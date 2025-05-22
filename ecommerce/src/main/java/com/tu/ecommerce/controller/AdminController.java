package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.bindingModel.CreateCategory;
import com.tu.ecommerce.model.bindingModel.EditCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryAdminView;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.service.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("product-categories/{id}")
    public ProductCategoryView getProductCategory(@PathVariable("id") Long id) {
        return this.productCategoryService.getProductCategoryById(id);
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

    @DeleteMapping("product-categories/delete/{id}")
    public ProductCategoryView deleteProductCategory(@PathVariable("id") Long id) {
        return this.productCategoryService.deleteProductCategory(id);
    }
}
