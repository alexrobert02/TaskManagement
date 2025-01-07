package org.example.taskmanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DueDateValidator.class)  // Specify the validator to be used
public @interface ValidDueDate {
    String message() default "Due date must be after the current date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
