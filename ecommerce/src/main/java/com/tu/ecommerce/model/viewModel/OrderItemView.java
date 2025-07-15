package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemView {

    private Long id;

    private String imageURL;

    private BigDecimal unitPrice;

    private BigDecimal unitPriceEur;

    private int quantity;

    private ProductView product;
}
