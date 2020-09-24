package org.step;

import org.step.repository.SessionFactoryCreator;

import javax.persistence.EntityManager;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;

public class Runner {

    public static void main(String[] args) {
//
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//
//        Validator validator = validatorFactory.getValidator();
//
//        validator.validate()

        System.out.println("Hello world");

        EntityManager entityManager = SessionFactoryCreator.getEntityManager();

        Map<String, Object> properties = entityManager.getProperties();

        System.out.println(properties);
    }


}
