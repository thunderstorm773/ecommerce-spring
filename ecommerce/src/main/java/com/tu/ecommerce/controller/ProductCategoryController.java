package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.service.ProductCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("")
    public List<ProductCategoryView> getAllActiveProductCategories() {
        return this.productCategoryService.getAllActiveProductCategories();
    }

    @GetMapping("name/{categoryName}")
    public ProductCategoryView getProductCategoryByName(@PathVariable("categoryName") String categoryName) {
        return this.productCategoryService.getProductCategoryByName(categoryName);
    }
}
