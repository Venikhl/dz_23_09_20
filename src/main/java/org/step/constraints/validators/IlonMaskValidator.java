package org.step.constraints.validators;

import org.step.constraints.IlonMaskWillNotPass;
import org.step.constraints.Pass;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IlonMaskValidator implements ConstraintValidator<IlonMaskWillNotPass, String> {

    private Pass pass;

    @Override
    public void initialize(IlonMaskWillNotPass constraintAnnotation) {
        this.pass = constraintAnnotation.pass();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (!name.equalsIgnoreCase("Ilon Mask")) {
            return true;
        } else {
            switch (pass) {
                case DENIED:
                    return false;
                case ALLOW:
                    return true;
                default:
                    System.out.println("Default message");
            }
        }
        return false;
    }
}
