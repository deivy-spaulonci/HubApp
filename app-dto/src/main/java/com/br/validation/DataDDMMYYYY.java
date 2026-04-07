package com.br.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataDDMMYYYYValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataDDMMYYYY {

    String message() default "Data inválida (formato esperado: ddMMyyyy)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
