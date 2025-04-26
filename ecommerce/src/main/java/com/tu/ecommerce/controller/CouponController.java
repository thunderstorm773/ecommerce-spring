package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.CouponView;
import com.tu.ecommerce.service.CouponService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("actives")
    public Page<CouponView> getAllActiveCoupons(Pageable pageable) {
        return couponService.getAllActiveCoupons(pageable);
    }
}
