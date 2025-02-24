package com.tu.ecommerce.model.bindingModel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrder {

    private int totalQuantity;

    private BigDecimal totalPrice;
}
