package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductAdminView {

    private Long id;

    private String name;

    private String description;

    private String image;

    private ProductCategoryView category;

    private BigDecimal unitPrice;

    private BigDecimal unitPriceEur;

    private String imageUrl;

    private Boolean isActive;

    private Integer unitsInStock;

    private Date dateCreated;

    private Date lastUpdated;
}
