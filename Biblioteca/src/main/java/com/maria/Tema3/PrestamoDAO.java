package com.maria.Tema3;

import Entidades.Prestamo;
import Entidades.Usuario;
import Entidades.Ejemplar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PrestamoDAO {

    public List<Prestamo> obtenerPrestamosPorUsuario(Usuario usuario) {
        EntityManager em = JPACode.getEntityManager();
        List<Prestamo> lista;

        try {
            TypedQuery<Prestamo> query = em.createQuery(
                    "SELECT p FROM Prestamo p " +
                            "JOIN FETCH p.ejemplar e " +
                            "JOIN FETCH e.isbn " +
                            "WHERE p.usuario = :usuario",
                    Prestamo.class
            );
            query.setParameter("usuario", usuario);
            lista = query.getResultList();
        } finally {
            em.close();
        }

        return lista;
    }

    public List<Prestamo> findAll() {
        EntityManager em = JPACode.getEntityManager();
        List<Prestamo> lista =  em.createQuery("FROM Prestamo", Prestamo.class).getResultList();
        em.close();
        return lista;
    }

    public void devolverPrestamo(Prestamo prestamo) {
        EntityManager em = JPACode.getEntityManager();

        try {
            em.getTransaction().begin();

            Prestamo p = em.find(Prestamo.class, prestamo.getId());
            p.setFechaDevolucion(LocalDate.now());

            Ejemplar ej = p.getEjemplar();
            ej.setEstado("Disponible");

            Usuario usuario = p.getUsuario();

            LocalDate fechaLimite = p.getFechaInicio().plusDays(15);
            if (LocalDate.now().isAfter(fechaLimite)) {
                LocalDate nuevaPenalizacion = LocalDate.now().plusDays(15);

                if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
                    nuevaPenalizacion = usuario.getPenalizacionHasta().plusDays(15);
                }

                usuario.setPenalizacionHasta(nuevaPenalizacion);

                long diasTotales = ChronoUnit.DAYS.between(LocalDate.now(), nuevaPenalizacion);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Penalización activa");
                alert.setHeaderText("Este usuario ha devuelto el libro fuera de plazo");
                alert.setContentText("El usuario estará penalizado durante " + diasTotales + " días.");
                alert.showAndWait();
            }

            em.merge(p);
            em.merge(ej);
            em.merge(usuario);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public int contarPrestamosActivos(Usuario usuario) {
        EntityManager em = JPACode.getEntityManager();
        int total;
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(p) FROM Prestamo p WHERE p.usuario = :usuario AND p.fechaDevolucion IS NULL",
                    Long.class
            );
            query.setParameter("usuario", usuario);
            total = query.getSingleResult().intValue();
        } finally {
            em.close();
        }
        return total;
    }

    public void crearPrestamo(Usuario usuario, Ejemplar ejemplar) {
        EntityManager em = JPACode.getEntityManager();
        try {
            em.getTransaction().begin();

            ejemplar.setEstado("Prestado");
            em.merge(ejemplar);

            Prestamo prestamo = new Prestamo();
            prestamo.setUsuario(usuario);
            prestamo.setEjemplar(ejemplar);
            prestamo.setFechaInicio(LocalDate.now());
            prestamo.setFechaDevolucion(null);

            em.persist(prestamo);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
