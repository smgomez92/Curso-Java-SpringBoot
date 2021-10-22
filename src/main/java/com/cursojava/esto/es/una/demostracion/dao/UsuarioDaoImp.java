package com.cursojava.esto.es.una.demostracion.dao;

import com.cursojava.esto.es.una.demostracion.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Usuario> getUsers() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();

    }

    @Override
    public Usuario getUserById(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public void deleteUser(Usuario user) {
        entityManager.remove(user);
    }

    @Override
    public void addUser(Usuario user) {
        entityManager.merge(user);

    }

    @Override
    public Usuario verifyCredentialsByUser(Usuario usuario) {
        String query = "FROM Usuario u where u.email = :email"; //and u.password = :pass";

        List<Usuario> user = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                /* .setParameter("pass", usuario.getPassword())*/
                .getResultList();
        if (user.isEmpty()) {
            return null;
        }
        String passHashed = user.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passHashed, usuario.getPassword())) {
            return user.get(0);
        }
        return null;

    }
}
