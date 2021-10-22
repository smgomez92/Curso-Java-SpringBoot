package com.cursojava.esto.es.una.demostracion.controllers;

import com.cursojava.esto.es.una.demostracion.dao.UsuarioDao;
import com.cursojava.esto.es.una.demostracion.models.Usuario;
import com.cursojava.esto.es.una.demostracion.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {


    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping(value = "/login")
    public String login(@RequestBody Usuario usuario) {
        Usuario userLoged = usuarioDao.verifyCredentialsByUser(usuario);
        if (userLoged != null) {
            String token = jwtUtil.create(String.valueOf(userLoged.getId()), userLoged.getEmail());
            log.info("Usuario logueado exitosamente");
            return token;
        } else {
            log.info("No existe el usuario");
            return "FAIL";
        }
    }
}
