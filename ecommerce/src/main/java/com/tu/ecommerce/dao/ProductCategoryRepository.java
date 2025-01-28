package com.tu.ecommerce.dao;

import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category",
        excerptProjection = ProductCategoryView.class)
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
