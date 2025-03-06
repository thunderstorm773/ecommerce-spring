package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderView {

    private String orderTrackingNumber;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private Date dateCreated;
}
