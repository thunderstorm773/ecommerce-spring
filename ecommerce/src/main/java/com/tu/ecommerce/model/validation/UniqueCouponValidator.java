package com.tu.ecommerce.model.validation;

import com.tu.ecommerce.model.viewModel.CouponView;
import com.tu.ecommerce.service.CouponService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueCouponValidator implements ConstraintValidator<UniqueCoupon, String> {

    private final CouponService couponService;

    public UniqueCouponValidator(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public boolean isValid(String discountCode, ConstraintValidatorContext constraintValidatorContext) {
        CouponView coupon = this.couponService.getCouponByDiscountCode(discountCode);
        return coupon == null;
    }
}
