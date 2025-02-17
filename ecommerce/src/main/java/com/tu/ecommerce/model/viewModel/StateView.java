package com.tu.ecommerce.model.viewModel;

import lombok.Data;

@Data
public class StateView {

    private Integer id;

    private String name;

    private CountryView country;
}
