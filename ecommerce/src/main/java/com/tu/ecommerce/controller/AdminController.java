package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.bindingModel.CreateCategory;
import com.tu.ecommerce.model.bindingModel.CreateCoupon;
import com.tu.ecommerce.model.bindingModel.EditCategory;
import com.tu.ecommerce.model.bindingModel.EditCoupon;
import com.tu.ecommerce.model.viewModel.CouponAdminView;
import com.tu.ecommerce.model.viewModel.CouponView;
import com.tu.ecommerce.model.viewModel.ProductCategoryAdminView;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.service.CouponService;
import com.tu.ecommerce.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductCategoryService productCategoryService;

    private final CouponService couponService;

    public AdminController(ProductCategoryService productCategoryService,
                           CouponService couponService) {
        this.productCategoryService = productCategoryService;
        this.couponService = couponService;
    }

    @GetMapping("product-categories")
    public Page<ProductCategoryAdminView> getAllProductCategories(Pageable pageable) {
        return this.productCategoryService.getAllProductCategories(pageable);
    }

    @GetMapping("product-categories/{id}")
    public ProductCategoryView getProductCategory(@PathVariable("id") Long id) {
        return this.productCategoryService.getProductCategoryById(id);
    }

    @GetMapping("product-categories/can-edit-name")
    public boolean canEditProductCategoryName(@RequestParam(value = "id", required = false) Long id,
                                              @RequestParam(value = "name") String categoryName) {
        return this.productCategoryService.canEditProductCategoryName(id, categoryName);
    }

    @PostMapping("product-categories/add")
    public ProductCategoryView createProductCategory(@Valid @RequestBody CreateCategory createCategory) {
        return this.productCategoryService.createProductCategory(createCategory);
    }

    @PutMapping("product-categories/edit/{id}")
    public ProductCategoryView editProductCategory(@PathVariable("id") Long id,
                                                   @Valid @RequestBody EditCategory editCategory) {
        return this.productCategoryService.editProductCategory(id, editCategory);
    }

    @PostMapping("product-categories/deactivate/{id}")
    public ProductCategoryView deactivateProductCategory(@PathVariable("id") Long id) {
        return this.productCategoryService.deactivateProductCategory(id);
    }

    @PostMapping("product-categories/activate/{id}")
    public ProductCategoryView activateProductCategory(@PathVariable("id") Long id) {
        return this.productCategoryService.activateProductCategory(id);
    }

    @GetMapping("coupons")
    public Page<CouponAdminView> getAllCoupons(Pageable pageable) {
        return this.couponService.getAllCoupons(pageable);
    }

    @GetMapping("coupons/can-edit-discount-code")
    public boolean canEditCouponDiscountCode(@RequestParam(value = "id", required = false) Long id,
                                             @RequestParam(value = "discountCode") String discountCode) {
        return this.couponService.canEditCouponDiscountCode(id, discountCode);
    }

    @PostMapping("coupons/add")
    public CouponView createCoupon(@Valid @RequestBody CreateCoupon createCoupon) {
        return this.couponService.createCoupon(createCoupon);
    }

    @PutMapping("coupons/edit/{id}")
    public CouponView editCoupon(@PathVariable("id") Long id,
                                 @Valid @RequestBody EditCoupon editCoupon) {
        return this.couponService.editCoupon(id, editCoupon);
    }

    @DeleteMapping("coupons/delete/{id}")
    public CouponView deleteCoupon(@PathVariable("id") Long id) {
        return this.couponService.deleteCoupon(id);
    }
}
