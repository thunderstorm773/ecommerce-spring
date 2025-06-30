package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.util.Date;

@Data
public class SystemParameterView {

    private Long id;

    private String code;

    private String value;

    private String description;

    private Date dateCreated;

    private Date lastUpdated;
}
