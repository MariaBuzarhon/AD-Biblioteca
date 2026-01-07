package com.maria.Tema3;

import Entidades.Ejemplar;
import Entidades.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EjemplarDAO {

    public List<Ejemplar> obtenerDisponibles() {
        EntityManager em = JPACode.getEntityManager();
        List<Ejemplar> lista;
        try {
            TypedQuery<Ejemplar> query = em.createQuery(
                    "SELECT e FROM Ejemplar e WHERE e.estado = 'Disponible'",
                    Ejemplar.class
            );
            lista = query.getResultList();
        } finally {
            em.close();
        }
        return lista;
    }

    public List<Ejemplar> obtenerTodos() {
        EntityManager em = JPACode.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Ejemplar e", Ejemplar.class).getResultList();
        } finally {
            em.close();
        }
    }
    public List<Ejemplar> obtenerPorLibro(Libro libro) {
        EntityManager em = JPACode.getEntityManager();
        return em.createQuery("SELECT e FROM Ejemplar e WHERE e.isbn = :libro", Ejemplar.class)
                .setParameter("libro", libro)
                .getResultList();
    }


    public void guardar(Ejemplar ejemplar) {
        EntityManager em = JPACode.getEntityManager();
        em.getTransaction().begin();
        em.persist(ejemplar);
        em.getTransaction().commit();
    }

}
