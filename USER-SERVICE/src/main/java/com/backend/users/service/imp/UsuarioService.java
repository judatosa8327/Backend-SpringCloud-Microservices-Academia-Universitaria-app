package com.backend.users.service.imp;

import com.backend.users.client.CourseRestClient;
import com.backend.users.dto.input.UsuarioDtoInput;
import com.backend.users.dto.output.UsuarioDtoOut;
import com.backend.users.entity.Rol;
import com.backend.users.entity.Usuario;
import com.backend.users.exception.NotFoundResourceException;
import com.backend.users.exception.UserDuplicateException;
import com.backend.users.repository.IRoleRepository;
import com.backend.users.repository.IUsuarioRepository;
import com.backend.users.service.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    private final IUsuarioRepository usuarioRepository;
    private final IRoleRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRestClient courseRestClient;
    private final ModelMapper modelMapper;

    public UsuarioService(IUsuarioRepository usuarioRepository, IRoleRepository rolesRepository, PasswordEncoder passwordEncoder, CourseRestClient courseRestClient, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseRestClient = courseRestClient;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDtoOut> listarUsuarios() {

        List<UsuarioDtoOut> usuariosDtoOut = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDtoOut.class))
                .toList();

        logger.info("Lista de usuarios: " + usuariosDtoOut);

        return usuariosDtoOut;
    }

    @Override
    @Transactional
    public UsuarioDtoOut guardarUsuario(UsuarioDtoInput usuario) {

        usuarioRepository.findByUsername(usuario.getUsername()).ifPresent(u -> {
            throw new UserDuplicateException("El nombre de usuario ya existe: " + usuario.getUsername());
        });

        Usuario usuarioAGuardar = modelMapper.map(usuario, Usuario.class);

        usuarioAGuardar.setRoles(getRoles(usuario));
        usuarioAGuardar.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioAGuardar.setCreatedAt(usuario.getCreatedAt() == null ? LocalDate.now() : usuario.getCreatedAt());
        Usuario usuarioGuardado = usuarioRepository.save(usuarioAGuardar);
        UsuarioDtoOut usuarioDtoOut = modelMapper.map(usuarioGuardado, UsuarioDtoOut.class);
        logger.info("Usuario guardado: " + usuarioDtoOut);

        return usuarioDtoOut;
    }

    public List<Rol> getRoles(UsuarioDtoInput usuario) {

        List<Rol> roles = new ArrayList<>();
        rolesRepository.findByName("ROLE_USER").ifPresent(roles::add);
        if (usuario.getIsAdmin()) rolesRepository.findByName("ROLE_ADMIN").ifPresent(roles::add);

        return roles;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDtoOut obtenerUsuarioPorId(Long id) {

        Usuario usuarioBuscado = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("No hay datos con id: " + id));
        UsuarioDtoOut usuarioDtoOut = modelMapper.map(usuarioBuscado, UsuarioDtoOut.class);
        logger.info("Usuario buscado: " + usuarioDtoOut);

        return usuarioDtoOut;
    }

    @Override
    @Transactional
    public UsuarioDtoOut actualizarUsuario(UsuarioDtoInput usuario, Long id) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("No hay datos con id: " + id));

        usuarioExistente.setRoles(getRoles(usuario));
        usuarioExistente.setName(usuario.getName());
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioExistente.setEnabled(usuario.getEnabled());
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        UsuarioDtoOut usuarioDtoOut = modelMapper.map(usuarioActualizado, UsuarioDtoOut.class);
        logger.info("Usuario actualizado: " + usuarioActualizado);

        return usuarioDtoOut;
    }


    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuarioAEliminar = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("No hay datos con id: " + id));
        logger.info("Usuario a eliminar: " + usuarioAEliminar);
        usuarioRepository.deleteById(id);
        logger.info("Usuario eliminado con id: " + id);
        courseRestClient.deleteCourseUserByUserId(id);

    }


}
