package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.model.viewModel.ProductView;
import com.tu.ecommerce.util.ModelMapperUtil;
import com.tu.ecommerce.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
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

    public Page<ProductView> getAllProducts(String name, Jwt jwt, Pageable pageable) {
        Page<Product> products;
        boolean isAdmin = UserUtil.isUserAdmin(jwt);

        if (StringUtils.hasText(name)) {
            products = this.productRepository.findAllByNameContainingWithActiveCategory(name, isAdmin, pageable);
        } else {
            products = this.productRepository.findAllWithActiveCategory(isAdmin, pageable);
        }

        return this.modelMapperUtil.convertToPage(pageable, products, ProductView.class);
    }

    public Page<ProductView> getProductsByCategoryId(Long categoryId, Jwt jwt, Pageable pageable) {
        boolean isAdmin = UserUtil.isUserAdmin(jwt);

        Page<Product> products = this.productRepository.findAllByActiveCategoryId(categoryId, isAdmin, pageable);
        return this.modelMapperUtil.convertToPage(pageable, products, ProductView.class);
    }

    public ProductView getProduct(Long id, Jwt jwt) {
        boolean isAdmin = UserUtil.isUserAdmin(jwt);

        Optional<Product> product = this.productRepository.findIdWithActiveCategory(id, isAdmin);
        return product.map(value -> this.modelMapperUtil.getModelMapper().map(value, ProductView.class))
                .orElse(null);
    }

    public ProductView publishProduct(Long id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product does not exists");
        }

        Product product = optionalProduct.get();
        if(product.getIsActive()) {
            throw new RuntimeException("Product is already published");
        }

        product.setIsActive(true);
        this.productRepository.save(product);

        return this.modelMapperUtil.getModelMapper().map(product, ProductView.class);
    }

    public ProductView unpublishProduct(Long id) {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product does not exists");
        }

        Product product = optionalProduct.get();
        if(!product.getIsActive()) {
            throw new RuntimeException("Product is already unpublished");
        }

        product.setIsActive(false);
        this.productRepository.save(product);

        return this.modelMapperUtil.getModelMapper().map(product, ProductView.class);
    }
}
