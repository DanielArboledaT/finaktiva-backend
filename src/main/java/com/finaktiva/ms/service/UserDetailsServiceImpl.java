package com.finaktiva.ms.service;

import com.finaktiva.ms.entity.UsuarioEntity;
import com.finaktiva.ms.entity.UsuarioPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioService.getByUsername(username).get();
        return UsuarioPrincipal.build(usuario);

    }

}
