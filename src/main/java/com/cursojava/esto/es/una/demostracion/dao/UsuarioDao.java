package com.cursojava.esto.es.una.demostracion.dao;

import com.cursojava.esto.es.una.demostracion.models.Usuario;

import java.util.List;

public interface UsuarioDao {
    List<Usuario> getUsers();
    Usuario getUserById(Long id);
    void deleteUser(Usuario user);
    void addUser(Usuario user);

    Usuario  verifyCredentialsByUser(Usuario usuario);
}
