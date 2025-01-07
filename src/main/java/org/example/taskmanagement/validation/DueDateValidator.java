package org.example.taskmanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DueDateValidator implements ConstraintValidator<ValidDueDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate dueDate, ConstraintValidatorContext context) {
        if (dueDate == null) {
            return true;
        }

        return dueDate.isAfter(LocalDate.now());
    }
}
