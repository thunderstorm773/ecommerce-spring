package com.tu.ecommerce.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = UniqueCategoryValidator.class)
public @interface UniqueCategory {

    String message() default "Category Name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
