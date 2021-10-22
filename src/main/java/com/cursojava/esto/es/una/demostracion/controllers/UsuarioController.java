package com.cursojava.esto.es.una.demostracion.controllers;

import com.cursojava.esto.es.una.demostracion.dao.UsuarioDao;
import com.cursojava.esto.es.una.demostracion.models.Usuario;
import com.cursojava.esto.es.una.demostracion.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * Obtiene usuarios por el id
     *
     * @param id id del usuario en la base de datos
     * @return Usuario
     */
    @GetMapping(value = "usuarios/{id}")
    public Usuario getUserById(@PathVariable Long id) {
        return usuarioDao.getUserById(id);
    }

    /**
     * Obtiene una lista de usuarios desde la BDD
     *
     * @return List<Usuario>
     */
    @GetMapping(value = "usuarios")
    public List<Usuario> getUsers(@RequestHeader(value = "Authorization") String token) {
        if (!verifyToken(token)) {
            return null;
        }
        return usuarioDao.getUsers();
    }

    public boolean verifyToken(String token) {
        return jwtUtil.getKey(token) != null;

    }

    @RequestMapping(value = "usuariosss")
    public Usuario editUserById() {
        Usuario user = new Usuario();
        user.setNombre("Santiago");
        user.setApellido("Gomez");
        user.setEmail("smg@gamil.com");
        user.setTelefono("0987539462");
        user.setPassword("12345566");

        return user;
    }

    /**
     * Elimina un usuario en la BDD por id
     *
     * @param id del usuario en la base de datos
     * @return Mensaje de eliminacion
     */
    @DeleteMapping(value = "usuarios/{id}")
    public String deleteUserById(@RequestHeader(value = "Authorization") String token, @PathVariable Long id) {
        if (!verifyToken(token)) {
            return "NO AUTORIZADO";
        }
        Usuario user = usuarioDao.getUserById(id);
        if (user != null) {
            usuarioDao.deleteUser(user);
            return "Eliminado el usuario con id " + id;
        } else {
            return "no se econtro el usuario con id " + id;
        }

    }

    /**
     * Agrega un usuario en la base de datos
     *
     * @return Usuario agregado
     */
    @PostMapping(value = "usuarios")
    public Usuario addUser(@RequestBody Usuario user) {
        String password = user.getPassword();
        //usando una libreria para realizar el hash de la contrasenia
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashPass = argon2.hash(1, 1024, 1, password);
        user.setPassword(hashPass);
        usuarioDao.addUser(user);
        return user;
    }
}
