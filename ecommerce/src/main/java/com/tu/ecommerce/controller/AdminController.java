package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.ProductCategoryAdminView;
import com.tu.ecommerce.service.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProductCategoryService productCategoryService;

    public AdminController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("product-categories")
    public Page<ProductCategoryAdminView> getAllProductCategories(Pageable pageable) {
        return this.productCategoryService.getAllProductCategories(pageable);
    }
}
