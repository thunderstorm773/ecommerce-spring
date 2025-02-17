package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductCategoryRepository;
import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    private final ModelMapperUtil modelMapperUtil;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository,
                                  ModelMapperUtil modelMapperUtil) {

        this.productCategoryRepository = productCategoryRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<ProductCategoryView> getAllProductCategories(Pageable pageable) {
        Page<ProductCategory> productCategories = this.productCategoryRepository.findAll(pageable);
        return this.modelMapperUtil.convertToPage(pageable, productCategories, ProductCategoryView.class);
    }
}
