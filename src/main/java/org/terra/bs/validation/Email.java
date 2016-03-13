package org.terra.bs.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Email.Validator.class)
public @interface Email {

    String message() default "{org.terra.bs.validation.constraints.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<Email, String> {

        @Override
        public void initialize(final Email hasId) {
        	// annotation has no type element(s)
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if(value.length() == 0) {
                // email field not mandatory, validate only when email address provided
                return true;
            }
            if (value.indexOf("@") == -1) {
                return false;
            }
            // more email validation / checks
            return true;
        }
    }

}