package com.tu.ecommerce.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = CategoryValidator.class)
public @interface Category {

    String message() default "Category not exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
