package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.model.viewModel.ProductView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapperUtil modelMapperUtil;

    public ProductService(ProductRepository productRepository,
                          ModelMapperUtil modelMapperUtil) {

        this.productRepository = productRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public List<ProductView> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return this.modelMapperUtil.convertAll(products, ProductView.class);
    }
}
