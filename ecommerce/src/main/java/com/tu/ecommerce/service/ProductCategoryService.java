package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductCategoryRepository;
import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final ModelMapperUtil modelMapperUtil;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository,
                                  ModelMapperUtil modelMapperUtil) {

        this.productCategoryRepository = productCategoryRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public List<ProductCategoryView> getAllProductCategories() {
        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        return this.modelMapperUtil.convertAll(productCategories, ProductCategoryView.class);
    }
}
