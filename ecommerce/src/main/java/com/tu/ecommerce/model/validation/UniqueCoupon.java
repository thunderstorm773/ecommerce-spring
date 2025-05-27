package com.tu.ecommerce.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = UniqueCouponValidator.class)
public @interface UniqueCoupon {

    String message() default "Coupon already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
