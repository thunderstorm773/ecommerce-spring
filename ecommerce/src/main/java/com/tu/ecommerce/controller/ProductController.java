package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.ProductView;
import com.tu.ecommerce.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public Page<ProductView> getAllProducts(@RequestParam(value = "name", required = false) String name,
                                            Pageable pageable) {
        return this.productService.getAllProducts(name, pageable);
    }

    @GetMapping("category/{id}")
    public Page<ProductView> getProductsByCategoryId(@PathVariable("id") Long id, Pageable pageable) {
        return this.productService.getProductsByCategoryId(id, pageable);
    }
}
