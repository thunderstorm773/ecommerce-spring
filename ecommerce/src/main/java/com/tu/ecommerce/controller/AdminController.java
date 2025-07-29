package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.bindingModel.*;
import com.tu.ecommerce.model.viewModel.*;
import com.tu.ecommerce.service.CouponService;
import com.tu.ecommerce.service.ProductCategoryService;
import com.tu.ecommerce.service.ProductService;
import com.tu.ecommerce.service.SystemParameterService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductCategoryService productCategoryService;

    private final CouponService couponService;

    private final SystemParameterService systemParameterService;

    private final ProductService productService;

    public AdminController(ProductCategoryService productCategoryService,
                           CouponService couponService,
                           SystemParameterService systemParameterService,
                           ProductService productService) {

        this.productCategoryService = productCategoryService;
        this.couponService = couponService;
        this.systemParameterService = systemParameterService;
        this.productService = productService;
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

    @GetMapping("coupons/{id}")
    public CouponView getCoupon(@PathVariable("id") Long id) {
        return this.couponService.getCouponById(id);
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

    @GetMapping("system-parameters")
    public Page<SystemParameterView> getSystemParameters(Pageable pageable) {
        return this.systemParameterService.getAllSystemParameters(pageable);
    }

    @GetMapping("system-parameters/{id}")
    public SystemParameterView getSystemParameter(@PathVariable("id") Long id) {
        return this.systemParameterService.getSystemParameter(id);
    }

    @PostMapping("system-parameters/add")
    public SystemParameterView createSystemParameter(@Valid @RequestBody CreateSystemParameter createSystemParameter) {
        return this.systemParameterService.createSystemParameter(createSystemParameter);
    }

    @PutMapping("system-parameters/edit/{id}")
    public SystemParameterView editSystemParameter(@PathVariable("id") Long id,
                                                   @Valid @RequestBody EditSystemParameter editSystemParameter) {
        return this.systemParameterService.editSystemParameter(id, editSystemParameter);
    }

    @PostMapping("products/publish/{id}")
    public ProductView publishProduct(@PathVariable("id") Long id) {
        return this.productService.publishProduct(id);
    }

    @PostMapping("products/unpublish/{id}")
    public ProductView unpublishProduct(@PathVariable("id") Long id) {
        return this.productService.unpublishProduct(id);
    }

    @GetMapping("products/{id}")
    public ProductAdminView getProduct(@PathVariable("id") Long id) {
        return this.productService.getProduct(id);
    }

    @PostMapping("products/add")
    public ProductAdminView createProduct(@Valid @ModelAttribute CreateProduct createProduct) throws IOException {
        return this.productService.createProduct(createProduct);
    }

    @PutMapping("products/edit/{id}")
    public ProductAdminView editProduct(@PathVariable("id") Long id,
                                        @Valid @RequestBody EditProduct editProduct) {
        return this.productService.editProduct(id, editProduct);
    }
}
