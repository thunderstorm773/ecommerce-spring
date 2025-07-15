package com.tu.ecommerce.model.viewModel;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderWithItemsView {

    private String orderTrackingNumber;

    private int totalQuantity;

    private BigDecimal totalPrice;

    private BigDecimal totalPriceEur;

    private Date dateCreated;

    private List<OrderItemView> orderItems;
}
