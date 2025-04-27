package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CouponRepository;
import com.tu.ecommerce.entity.Coupon;
import com.tu.ecommerce.model.viewModel.CouponView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CouponService(CouponRepository couponRepository,
                         ModelMapperUtil modelMapperUtil) {

        this.couponRepository = couponRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<CouponView> getAllActiveCoupons(Pageable pageable) {
        Page<Coupon> coupons = this.couponRepository.findAllActiveCoupons(pageable);
        return this.modelMapperUtil.convertToPage(pageable, coupons, CouponView.class);
    }

    public CouponView checkIsActiveCoupon(String couponCode) {
        Coupon coupon = this.couponRepository.checkIsActiveCoupon(couponCode);
        if (coupon != null) {
            return this.modelMapperUtil.getModelMapper().map(coupon, CouponView.class);
        }

        return null;
    }
}
