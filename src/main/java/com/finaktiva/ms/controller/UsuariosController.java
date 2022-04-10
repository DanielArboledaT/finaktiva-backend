package com.finaktiva.ms.controller;


import com.finaktiva.ms.entity.UsuarioEntity;
import com.finaktiva.ms.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/finaktiva")
public class UsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/consultaUsuarios", method = RequestMethod.GET)
    public List<UsuarioEntity> obtenerUsuarios() {

        System.out.println("Entre");
        return (List<UsuarioEntity>) usuarioService.obtenerUsuarios();

    }

}
