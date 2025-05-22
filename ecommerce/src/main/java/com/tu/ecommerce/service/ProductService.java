package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.model.viewModel.ProductView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapperUtil modelMapperUtil;

    public ProductService(ProductRepository productRepository,
                          ModelMapperUtil modelMapperUtil) {

        this.productRepository = productRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<ProductView> getAllProducts(String name, Pageable pageable) {
        Page<Product> products;

        if (StringUtils.hasText(name)) {
            products = this.productRepository.findAllByNameContainingWithActiveCategory(name, pageable);
        } else {
            products = this.productRepository.findAllWithActiveCategory(pageable);
        }

        return this.modelMapperUtil.convertToPage(pageable, products, ProductView.class);
    }

    public Page<ProductView> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> products = this.productRepository.findAllByActiveCategoryId(categoryId, pageable);
        return this.modelMapperUtil.convertToPage(pageable, products, ProductView.class);
    }

    public ProductView getProduct(Long id) {
        Optional<Product> product = this.productRepository.findIdWithActiveCategory(id);
        return product.map(value -> this.modelMapperUtil.getModelMapper().map(value, ProductView.class))
                .orElse(null);
    }
}
