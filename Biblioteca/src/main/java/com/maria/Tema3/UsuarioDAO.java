package com.maria.Tema3;

import Entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class UsuarioDAO {
    public List<Usuario> findAll() {
        EntityManager em = JPACode.getEntityManager();

        List<Usuario> usuarios = em.createQuery("from Usuario", Usuario.class).getResultList();
        em.close();
        return usuarios;
    }

    public Usuario login(String usuario, String contrasenia) {

        EntityManager em = JPACode.getEntityManager();
        Usuario usu;

        try {
            usu = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.email = :nombre AND u.password = :cont",
                            Usuario.class
                    )
                    .setParameter("nombre", usuario)
                    .setParameter("cont", contrasenia)
                    .getSingleResult();

        } catch (NoResultException e) {
            usu = null;
        } finally {
            em.close();
        }

        return usu;
    }

    public Usuario informacion(String email) {
        EntityManager em = JPACode.getEntityManager();
        Usuario usu;

        try {
            usu = em.createQuery("SELECT u FROM Usuario u WHERE u.email =: nombre", Usuario.class)
                    .setParameter("nombre", email)
                    .getSingleResult();

        } catch (NoResultException e) {
        usu = null;

        } return usu;

    }
    public boolean guardarUsuario(Usuario u) {
        EntityManager em = JPACode.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
