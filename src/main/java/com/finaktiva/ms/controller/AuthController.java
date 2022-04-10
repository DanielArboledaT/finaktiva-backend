package com.finaktiva.ms.controller;


import com.finaktiva.ms.entity.RolEntity;
import com.finaktiva.ms.entity.UsuarioEntity;
import com.finaktiva.ms.security.dto.JwtDto;
import com.finaktiva.ms.security.dto.LoginUsuario;
import com.finaktiva.ms.security.dto.NuevoUsuario;
import com.finaktiva.ms.security.enums.RolNombre;
import com.finaktiva.ms.security.jwt.JwtProvider;
import com.finaktiva.ms.service.RolService;
import com.finaktiva.ms.service.UsuarioService;
import com.finaktiva.ms.util.Mensajes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/nuvoUsuario")
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

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return new ResponseEntity(new Mensajes("Campos incorrectos"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity(jwt, HttpStatus.OK);

    }

}
