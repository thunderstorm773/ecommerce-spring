package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductCategoryRepository;
import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryAdminView;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<ProductCategoryView> getAllActiveProductCategories() {
        List<ProductCategory> productCategories = this.productCategoryRepository.findAllActive();
        return this.modelMapperUtil.convertAll(productCategories, ProductCategoryView.class);
    }

    public Page<ProductCategoryAdminView> getAllProductCategories(Pageable pageable) {
        Page<ProductCategory> productCategories = this.productCategoryRepository.findAll(pageable);
        return this.modelMapperUtil.convertToPage(pageable, productCategories, ProductCategoryAdminView.class);
    }

    public ProductCategoryView getProductCategoryById(Long id) {
        ProductCategory productCategory = this.productCategoryRepository.findById(id).orElse(null);
        if (productCategory != null) {
            return this.modelMapperUtil.getModelMapper().map(productCategory, ProductCategoryView.class);
        }

        return null;
    }
}
