package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CouponRepository;
import com.tu.ecommerce.entity.Coupon;
import com.tu.ecommerce.model.bindingModel.CreateCoupon;
import com.tu.ecommerce.model.bindingModel.EditCoupon;
import com.tu.ecommerce.model.viewModel.CouponAdminView;
import com.tu.ecommerce.model.viewModel.CouponView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    public Page<CouponAdminView> getAllCoupons(Pageable pageable) {
        Page<Coupon> coupons = this.couponRepository.findAll(pageable);
        return this.modelMapperUtil.convertToPage(pageable, coupons, CouponAdminView.class);
    }

    public CouponView getCouponByDiscountCode(String discountCode) {
        Coupon coupon = this.couponRepository.findByDiscountCode(discountCode);
        if (coupon != null) {
            return this.modelMapperUtil.getModelMapper().map(coupon, CouponView.class);
        }

        return null;
    }

    public CouponView checkIsActiveCoupon(String couponCode) {
        Coupon coupon = this.couponRepository.checkIsActiveCoupon(couponCode);
        if (coupon != null) {
            return this.modelMapperUtil.getModelMapper().map(coupon, CouponView.class);
        }

        return null;
    }

    public CouponView createCoupon(CreateCoupon createCoupon) {
        Coupon coupon = this.modelMapperUtil.getModelMapper().map(createCoupon, Coupon.class);

        if (!createCoupon.getValidFrom().before(coupon.getValidTo())) {
            throw new RuntimeException("Valid from must be before valid to");
        }

        this.couponRepository.save(coupon);
        return this.modelMapperUtil.getModelMapper().map(coupon, CouponView.class);
    }

    public CouponView editCoupon(Long id, EditCoupon editCoupon) {
        Coupon coupon = this.couponRepository.findById(id).orElse(null);
        if (coupon == null) {
            throw new RuntimeException("Coupon does not exists");
        }

        if(!canEditCouponDiscountCode(id, editCoupon.getDiscountCode())) {
            throw new RuntimeException("Coupon already exists");
        }

        this.modelMapperUtil.getModelMapper().map(editCoupon, coupon);
        this.couponRepository.save(coupon);

        return this.modelMapperUtil.getModelMapper().map(coupon, CouponView.class);
    }

    public CouponView deleteCoupon(Long id) {
        Coupon coupon = this.couponRepository.findById(id).orElse(null);
        if (coupon == null) {
            throw new RuntimeException("Coupon does not exists");
        }

        this.couponRepository.delete(coupon);
        return this.modelMapperUtil.getModelMapper().map(coupon, CouponView.class);
    }

    public boolean canEditCouponDiscountCode(Long id, String discountCode) {
        CouponView coupon = this.getCouponByDiscountCode(discountCode);
        if (id != null && coupon != null) {
            return coupon.getId().equals(id);
        }

        return coupon == null;
    }
}
