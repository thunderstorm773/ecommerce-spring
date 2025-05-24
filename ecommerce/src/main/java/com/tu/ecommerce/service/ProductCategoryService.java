package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.ProductCategoryRepository;
import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.model.bindingModel.CreateCategory;
import com.tu.ecommerce.model.bindingModel.EditCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryAdminView;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    public ProductCategoryView getProductCategoryByName(String categoryName) {
        ProductCategory productCategory = this.productCategoryRepository.getProductCategoryByCategoryName(categoryName);
        if (productCategory != null) {
            return this.modelMapperUtil.getModelMapper().map(productCategory, ProductCategoryView.class);
        }

        return null;
    }

    public ProductCategoryView createProductCategory(CreateCategory createCategory) {
        ProductCategory productCategory = this.modelMapperUtil.getModelMapper().map(createCategory, ProductCategory.class);
        productCategory.setIsActive(1);
        this.productCategoryRepository.save(productCategory);

        return this.modelMapperUtil.getModelMapper().map(productCategory, ProductCategoryView.class);
    }

    public ProductCategoryView editProductCategory(Long id, EditCategory editCategory) {
        ProductCategory productCategory = this.productCategoryRepository.findById(id).orElse(null);
        if (productCategory == null) {
            throw new RuntimeException("Category does not exists");
        }

        this.modelMapperUtil.getModelMapper().map(editCategory, productCategory);
        this.productCategoryRepository.save(productCategory);

        return this.modelMapperUtil.getModelMapper().map(productCategory, ProductCategoryView.class);
    }

    public ProductCategoryView deactivateProductCategory(Long id) {
        ProductCategory productCategory = this.productCategoryRepository.findById(id).orElse(null);
        if (productCategory == null) {
            throw new RuntimeException("Category does not exists");
        }

        if (productCategory.getIsActive() == 0) {
            throw new RuntimeException("Category is already deactivated");
        }

        productCategory.setIsActive(0);
        this.productCategoryRepository.save(productCategory);

        return this.modelMapperUtil.getModelMapper().map(productCategory, ProductCategoryView.class);
    }

    public ProductCategoryView activateProductCategory(Long id) {
        ProductCategory productCategory = this.productCategoryRepository.findById(id).orElse(null);
        if (productCategory == null) {
            throw new RuntimeException("Category does not exists");
        }

        if (productCategory.getIsActive() == 1) {
            throw new RuntimeException("Category is already activated");
        }

        productCategory.setIsActive(1);
        this.productCategoryRepository.save(productCategory);

        return this.modelMapperUtil.getModelMapper().map(productCategory, ProductCategoryView.class);
    }
}
