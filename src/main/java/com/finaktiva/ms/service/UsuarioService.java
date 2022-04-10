package com.finaktiva.ms.service;


import com.finaktiva.ms.entity.UsuarioEntity;
import com.finaktiva.ms.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

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

    public void guardar(UsuarioEntity usuario) {

        usuarioRepository.save(usuario);

    }

}
