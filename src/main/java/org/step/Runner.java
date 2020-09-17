package org.step;

import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import java.util.Map;

public class Runner {

    public static void main(String[] args) {

        System.out.println("Hello world");

        EntityManager entityManager = SessionFactoryCreator.getEntityManager();

        Map<String, Object> properties = entityManager.getProperties();

        System.out.println(properties);
    }
}
