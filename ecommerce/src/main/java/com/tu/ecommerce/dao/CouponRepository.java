package com.tu.ecommerce.dao;

import com.tu.ecommerce.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(value = "SELECT c FROM Coupon c " +
                   "WHERE CURRENT_TIMESTAMP >= c.validFrom " +
                   "AND CURRENT_TIMESTAMP <= c.validTo " +
                   "AND c.status = true")
    Page<Coupon> findAllActiveCoupons(Pageable pageable);
}
