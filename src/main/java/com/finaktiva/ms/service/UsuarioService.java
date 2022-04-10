package com.finaktiva.ms.service;


import com.finaktiva.ms.entity.RolEntity;
import com.finaktiva.ms.entity.UsuarioEntity;
import com.finaktiva.ms.entity.UsuarioRolEntity;
import com.finaktiva.ms.repository.IUsuarioRepository;
import com.finaktiva.ms.repository.IUsuarioRolRepository;
import com.finaktiva.ms.security.dto.NuevoUsuario;
import com.finaktiva.ms.security.enums.RolNombre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IUsuarioRolRepository usuarioRolRepository;

    @Autowired
    RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UsuarioEntity> obtenerUsuarios() {

        return (List<UsuarioEntity>) usuarioRepository.findAll();

    }

    public Optional<UsuarioEntity> getByUsername(String username) {

        return usuarioRepository.findByUsername(username);

    }

    public boolean existsByNombreUsuario(String username) {

        return usuarioRepository.existsByUsername(username);

    }

    public boolean existsByNombreEmail(String email) {

        return usuarioRepository.existsByEmail(email);

    }

    public void guardar(NuevoUsuario usuario) {

        UsuarioEntity usr = new UsuarioEntity(usuario.getNombre(), usuario.getEmail(),
                passwordEncoder.encode(usuario.getPassword()), usuario.getUsername());

        Set<RolEntity> roles = new HashSet<>();

        if (usuario.getRoles().contains("admin")) {
            roles.add(rolService.getByRolNombre(RolNombre.ROL_ADMIN).get());
        } else {
            roles.add(rolService.getByRolNombre(RolNombre.ROL_OPERATIVO).get());
        }

        usr.setRoles(roles);

        usuarioRepository.save(usr);

    }

    public void eliminarUsuario(UsuarioEntity usuario) {

        usuarioRepository.deleteById(usuario.getIdusuario());

    }

    public void actualizarUsuario(NuevoUsuario usuario) {

        usuarioRepository.actualizarUsuario(usuario.getNombre(), usuario.getEmail(),
                passwordEncoder.encode(usuario.getPassword()), usuario.getUsername(), usuario.getIdusuario());

        Integer rol;

        if (usuario.getRoles().contains("admin")) {
            rol = 1;
        } else {
            rol = 2;
        }


        usuarioRolRepository.actualizarUsuarioRol(rol, usuario.getIdusuario());

    }

}
