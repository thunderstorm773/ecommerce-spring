package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductCategoryRepository;
import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.dao.SystemParameterRepository;
import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.entity.SystemParameter;
import com.tu.ecommerce.model.bindingModel.CreateProduct;
import com.tu.ecommerce.model.bindingModel.EditProduct;
import com.tu.ecommerce.model.viewModel.ProductView;
import com.tu.ecommerce.util.Constants;
import com.tu.ecommerce.util.CurrencyUtil;
import com.tu.ecommerce.util.ModelMapperUtil;
import com.tu.ecommerce.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    private final SystemParameterRepository systemParameterRepository;

    private final CurrencyUtil currencyUtil;

    private final ModelMapperUtil modelMapperUtil;

    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository productCategoryRepository,
                          SystemParameterRepository systemParameterRepository,
                          CurrencyUtil currencyUtil,
                          ModelMapperUtil modelMapperUtil) {

        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.systemParameterRepository = systemParameterRepository;
        this.currencyUtil = currencyUtil;
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

    public ProductView createProduct(CreateProduct createProduct) {
        SystemParameter showBgnCurrencyFirstParam = this.systemParameterRepository
                .findByCode(Constants.SHOW_BGN_CURRENCY_FIRST_CODE);
        Product product = this.modelMapperUtil.getModelMapper().map(createProduct, Product.class);

        ProductCategory productCategory = this.productCategoryRepository.findById(createProduct.getCategoryId()).orElse(null);
        product.setCategory(productCategory);
        this.setProductPrice(product, createProduct.getUnitPrice(), showBgnCurrencyFirstParam);

        this.productRepository.save(product);
        return this.modelMapperUtil.getModelMapper().map(product, ProductView.class);
    }

    public ProductView editProduct(Long id, EditProduct editProduct) {
        SystemParameter showBgnCurrencyFirstParam = this.systemParameterRepository
                .findByCode(Constants.SHOW_BGN_CURRENCY_FIRST_CODE);
        Product product = this.productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product does not exists");
        }

        ProductCategory productCategory = this.productCategoryRepository.findById(editProduct.getCategoryId()).orElse(null);
        product.setCategory(productCategory);
        this.setProductPrice(product, editProduct.getUnitPrice(), showBgnCurrencyFirstParam);

        this.modelMapperUtil.getModelMapper().map(editProduct, product);
        this.productRepository.save(product);
        return this.modelMapperUtil.getModelMapper().map(product, ProductView.class);
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

    private void setProductPrice(Product product,
                                 BigDecimal price,
                                 SystemParameter showBgnCurrencyFirstParam) {

        if ("1".equals(showBgnCurrencyFirstParam.getValue())) {
            BigDecimal unitPriceEur = this.currencyUtil.calculatePrice(price, showBgnCurrencyFirstParam);
            product.setUnitPriceEur(unitPriceEur);
        } else {
            product.setUnitPriceEur(price);
            BigDecimal unitPrice = this.currencyUtil.calculatePrice(price, showBgnCurrencyFirstParam);
            product.setUnitPrice(unitPrice);
        }
    }
}
