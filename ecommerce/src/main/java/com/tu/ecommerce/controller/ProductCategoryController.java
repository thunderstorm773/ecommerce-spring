package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.service.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("")
    public Page<ProductCategoryView> getAllProductCategories(Pageable pageable) {
        return this.productCategoryService.getAllProductCategories(pageable);
    }
}
