package com.backend.users.controller;

import com.backend.users.dto.input.UsuarioDtoInput;
import com.backend.users.dto.output.UsuarioDtoOut;
import com.backend.users.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDtoOut>> listUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDtoOut> createUsuario(@RequestBody @Valid UsuarioDtoInput usuario) {
        return new ResponseEntity<>(usuarioService.guardarUsuario(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDtoOut> registerUsuario(@RequestBody @Valid UsuarioDtoInput usuario) {
        usuario.setIsAdmin(false);
        return createUsuario(usuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoOut> getUsuarioById(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.obtenerUsuarioPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoOut> updateUsuario(@RequestBody @Valid UsuarioDtoInput usuario, @PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuario, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }


}
