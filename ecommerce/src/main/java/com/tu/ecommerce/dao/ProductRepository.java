package com.tu.ecommerce.dao;

import com.tu.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p " +
                   "JOIN p.category c " +
                   "WHERE c.isActive = 1 " +
                   "AND (:isAdmin = true OR p.isActive = true) " +
                   "ORDER BY p.dateCreated DESC, c.categoryName")
    Page<Product> findAllWithActiveCategory(@Param("isAdmin") boolean isAdmin,
                                            Pageable pageable);

    @Query(value = "SELECT p FROM Product p " +
                   "JOIN p.category c " +
                   "WHERE c.id = :categoryId " +
                   "AND c.isActive = 1 " +
                   "AND (:isAdmin = true OR p.isActive = true) " +
                   "ORDER BY p.dateCreated DESC, c.categoryName")
    Page<Product> findAllByActiveCategoryId(@Param("categoryId") Long categoryId,
                                            @Param("isAdmin") boolean isAdmin,
                                            Pageable pageable);

    @Query(value = "SELECT p FROM Product p " +
                   "JOIN p.category c " +
                   "WHERE p.name LIKE CONCAT('%', :name, '%') " +
                   "AND c.isActive = 1 " +
                   "AND (:isAdmin = true OR p.isActive = true) " +
                   "ORDER BY p.dateCreated DESC, c.categoryName")
    Page<Product> findAllByNameContainingWithActiveCategory(@Param("name") String name,
                                                            @Param("isAdmin") boolean isAdmin,
                                                            Pageable pageable);

    @Query(value = "SELECT p FROM Product p " +
                   "JOIN p.category c " +
                   "WHERE p.id = :id " +
                   "AND c.isActive = 1 " +
                   "AND (:isAdmin = true OR p.isActive = true)")
    Optional<Product> findIdWithActiveCategory(@Param("id") Long id,
                                               @Param("isAdmin") boolean isAdmin);
}
