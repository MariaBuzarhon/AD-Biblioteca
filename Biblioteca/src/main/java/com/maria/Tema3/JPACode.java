package com.maria.Tema3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPACode {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("BibliotecaPU");


    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}