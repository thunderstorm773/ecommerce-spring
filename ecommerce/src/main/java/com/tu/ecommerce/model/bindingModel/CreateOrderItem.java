package com.tu.ecommerce.model.bindingModel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderItem {

    private Long id;

    private String imageURL;

    private BigDecimal unitPrice;

    private int quantity;

    private Long productId;
}
