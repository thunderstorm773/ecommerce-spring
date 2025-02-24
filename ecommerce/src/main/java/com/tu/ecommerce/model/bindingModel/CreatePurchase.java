package com.tu.ecommerce.model.bindingModel;

import lombok.Data;

import java.util.Set;

@Data
public class CreatePurchase {

    private CreateCustomer customer;

    private CreateAddress shippingAddress;

    private CreateAddress billingAddress;

    private CreateOrder order;

    private Set<CreateOrderItem> orderItems;
}
