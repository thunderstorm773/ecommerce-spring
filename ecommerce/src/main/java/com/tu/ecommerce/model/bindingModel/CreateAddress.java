package com.tu.ecommerce.model.bindingModel;

import lombok.Data;

@Data
public class CreateAddress {

    private String city;

    private String street;

    private String country;

    private String state;

    private String zipCode;

}
