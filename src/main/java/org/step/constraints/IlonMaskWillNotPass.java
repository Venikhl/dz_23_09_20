package org.step.constraints;

import org.step.constraints.validators.IlonMaskValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IlonMaskValidator.class})
@Documented
public @interface IlonMaskWillNotPass {

    String message() default "Ilon Mask go away";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Pass pass() default Pass.DENIED;
}
