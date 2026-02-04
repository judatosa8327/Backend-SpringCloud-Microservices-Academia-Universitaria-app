package com.backend.users.service;

import com.backend.users.entity.Usuario;
import com.backend.users.repository.IUsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;

    public JpaUserDetailService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);

        if (usuarioOptional.isEmpty())
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);

        Usuario usuario = usuarioOptional.get();
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getName()))
                .collect(Collectors.toList());

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
