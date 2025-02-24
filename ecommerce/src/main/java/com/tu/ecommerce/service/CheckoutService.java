package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CustomerRepository;
import com.tu.ecommerce.entity.Address;
import com.tu.ecommerce.entity.Customer;
import com.tu.ecommerce.entity.Order;
import com.tu.ecommerce.entity.OrderItem;
import com.tu.ecommerce.model.bindingModel.CreateOrderItem;
import com.tu.ecommerce.model.bindingModel.CreatePurchase;
import com.tu.ecommerce.model.bindingModel.CreatePurchaseResponse;
import com.tu.ecommerce.util.ModelMapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutService {

    private final CustomerRepository customerRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CheckoutService(CustomerRepository customerRepository,
                           ModelMapperUtil modelMapperUtil) {

        this.customerRepository = customerRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    @Transactional
    public CreatePurchaseResponse placeOrder(CreatePurchase purchase) {
        Order order = this.modelMapperUtil.getModelMapper().map(purchase.getOrder(), Order.class);

        String orderTrackingNumber = this.generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        List<CreateOrderItem> createOrderItems = purchase.getOrderItems().stream().toList();
        List<OrderItem> mappedOrderItems = this.modelMapperUtil.convertAll(createOrderItems, OrderItem.class);
        Set<OrderItem> orderItems = new HashSet<>(mappedOrderItems);
        orderItems.forEach(item -> order.addOrderItem(item));

        Address shippingAddress = this.modelMapperUtil.getModelMapper().map(purchase.getShippingAddress(), Address.class);
        order.setShippingAddress(shippingAddress);

        Address billingAddress = this.modelMapperUtil.getModelMapper().map(purchase.getBillingAddress(), Address.class);
        order.setBillingAddress(billingAddress);

        Customer customer = this.modelMapperUtil.getModelMapper().map(purchase.getCustomer(), Customer.class);
        customer.addOrder(order);

        this.customerRepository.save(customer);

        return new CreatePurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
