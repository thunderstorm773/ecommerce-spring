package com.tu.ecommerce.dao;

import com.tu.ecommerce.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    List<State> findStateByCountry_Code(String countryCode);
}
