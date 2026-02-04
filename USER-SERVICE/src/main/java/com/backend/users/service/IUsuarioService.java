package com.backend.users.service;

import com.backend.users.dto.input.UsuarioDtoInput;
import com.backend.users.dto.output.UsuarioDtoOut;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDtoOut> listarUsuarios();

    UsuarioDtoOut guardarUsuario(UsuarioDtoInput usuario);

    UsuarioDtoOut obtenerUsuarioPorId(Long id);

    UsuarioDtoOut actualizarUsuario(UsuarioDtoInput usuario, Long id);

    void eliminarUsuario(Long id);

}
