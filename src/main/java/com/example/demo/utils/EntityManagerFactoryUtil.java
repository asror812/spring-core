package com.example.demo.utils;

import org.springframework.context.annotation.Configuration;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Configuration
public class EntityManagerFactoryUtil {

    private static EntityManagerFactory emf;

    // Method to return the singleton instance
    public EntityManagerFactory get() {
        if (emf == null) {
            synchronized (EntityManagerFactoryUtil.class) { 
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("default");
                }
            }
        }
        return emf;
    }
}
