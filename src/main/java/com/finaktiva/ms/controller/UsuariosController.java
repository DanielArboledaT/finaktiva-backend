package com.finaktiva.ms.controller;


import com.finaktiva.ms.entity.RolEntity;
import com.finaktiva.ms.entity.UsuarioEntity;
import com.finaktiva.ms.security.dto.NuevoUsuario;
import com.finaktiva.ms.security.enums.RolNombre;
import com.finaktiva.ms.service.RolService;
import com.finaktiva.ms.service.UsuarioService;
import com.finaktiva.ms.util.Mensajes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/finaktiva")
@CrossOrigin(origins = "*")
public class UsuariosController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolService rolService;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping(value = "/consultaUsuarios")
    public List<UsuarioEntity> obtenerUsuarios() {

        return (List<UsuarioEntity>) usuarioService.obtenerUsuarios();

    }

    @PreAuthorize("hasAuthority('ROL_ADMIN')")
    @PostMapping("/nuevoUsuario")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensajes("Campos incorrectos"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getUsername())) {
            return new ResponseEntity(new Mensajes("Nombre usuario ya existe"), HttpStatus.BAD_REQUEST);
        }
        if(usuarioService.existsByNombreEmail(nuevoUsuario.getEmail())) {
            return new ResponseEntity(new Mensajes("Email ya existe"), HttpStatus.BAD_REQUEST);
        }

        UsuarioEntity usuario = new UsuarioEntity(nuevoUsuario.getNombre(), nuevoUsuario.getEmail(),
                passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.getUsername());

        Set<RolEntity> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROL_OPERATIVO).get());

        if (nuevoUsuario.getRoles().contains("Admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROL_ADMIN).get());
        }

        usuario.setRoles(roles);
        usuarioService.guardar(usuario);

        return new ResponseEntity<>(new Mensajes("Usuario guardado"), HttpStatus.CREATED);

    }

}
