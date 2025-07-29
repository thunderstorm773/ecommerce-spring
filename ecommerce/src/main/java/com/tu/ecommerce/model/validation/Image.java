package com.tu.ecommerce.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface Image {

    String message() default "Image file is not png/jpeg or exceeded max allowed size(10MB)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
