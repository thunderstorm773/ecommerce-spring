package com.tu.ecommerce.dao;

import com.tu.ecommerce.entity.Product;
import com.tu.ecommerce.model.viewModel.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "product", path = "product",
        excerptProjection = ProductView.class)
public interface ProductRepository extends JpaRepository<Product, Long> {
}
