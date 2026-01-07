package com.maria.Tema3;

import Entidades.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class LibroDAO {

    public void guardar(Libro libro) {
        EntityManager em = JPACode.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Libro> obtenerTodos() {
        EntityManager em = JPACode.getEntityManager();
        try {
            TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Libro buscarPorISBN(String isbn) {
        EntityManager em = JPACode.getEntityManager();
        return em.find(Libro.class, isbn);
    }

}
