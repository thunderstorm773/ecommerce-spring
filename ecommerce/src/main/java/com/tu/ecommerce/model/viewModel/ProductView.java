package com.tu.ecommerce.model.viewModel;

import com.tu.ecommerce.entity.Product;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(name = "productView", types = {Product.class})
public interface ProductView {

    Long getId();

    String getSku();

    String getName();

    String getDescription();

    BigDecimal getUnitPrice();

    String getImageUrl();

    Boolean getIsActive();

    Integer getUnitsInStock();

    Date getDateCreated();

    Date getLastUpdated();
}
