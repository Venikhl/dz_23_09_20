package org.step.repository;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.step.entity.User;

import javax.validation.*;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationTest {

    private static Validator validator;

    // BeforeClass - вызывается 1 раз за весь цикл метода
    // в отличии от Before, который вызывается перед каждым методом теста
    @BeforeClass
    public static void setup() {
        System.out.println("Setup is happened");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        validator = validatorFactory.getValidator();
    }

    @AfterClass
    public static void clean() {
        System.out.println("Clean is happened");
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenValidateUser_ShouldThrowConstraintViolationException() {
        // session

        // transaction open
        User first = User.builder()
                .username("Ilon Mask")
                .age(16)
                .build();

        // session.persist(first)

        // transaction close
        Set<ConstraintViolation<User>> validate = validator.validate(first);

        if (validate.isEmpty()) {
            System.out.println("Everything working fine");
        } else {
            Map<String, String> violationMap = validate.stream()
                    .collect(Collectors.toMap(
                            cv -> cv.getPropertyPath().toString(),
                            ConstraintViolation::getMessage
                    ));
            System.out.println(violationMap);

            throw new ConstraintViolationException(validate);
        }
    }

    @Test
    public void whenValidateUser_ShouldPassValidation() {
        User user = User.builder()
                .username("valid username")
                .age(25)
                .build();

        Set<ConstraintViolation<User>> validate = validator.validate(user);

        if (validate.isEmpty()) {
            System.out.println("Everything fine");
        } else {
            throw new ConstraintViolationException(validate);
        }
    }
}
