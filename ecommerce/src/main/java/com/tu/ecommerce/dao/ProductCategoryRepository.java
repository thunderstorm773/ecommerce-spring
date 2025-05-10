package com.tu.ecommerce.dao;

import com.tu.ecommerce.entity.ProductCategory;
import com.tu.ecommerce.model.viewModel.ProductCategoryView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query(value = "SELECT c FROM ProductCategory c " +
                   "WHERE c.isActive = 1")
    List<ProductCategory> findAllActive();
}
