package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.model.viewModel.ProductView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapperUtil modelMapperUtil;

    public ProductService(ProductRepository productRepository,
                          ModelMapperUtil modelMapperUtil) {

        this.productRepository = productRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<ProductView> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return this.modelMapperUtil.convertToPage(pageable, products, ProductView.class);
    }

    public Page<ProductView> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findProductsByCategoryId(categoryId, pageable);
        return this.modelMapperUtil.convertToPage(pageable, products, ProductView.class);
    }
}
