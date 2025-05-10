package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.util.Date;

@Data
public class ProductCategoryAdminView {

    private Long id;

    private String categoryName;

    private Date lastUpdate;

    private Integer isActive;
}
