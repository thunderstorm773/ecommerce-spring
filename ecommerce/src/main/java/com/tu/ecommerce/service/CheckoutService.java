package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.CustomerRepository;
import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.entity.*;
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

    private final ProductRepository productRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CheckoutService(CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           ModelMapperUtil modelMapperUtil) {

        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
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

        for (OrderItem orderItem : orderItems) {
            Product product = this.productRepository.findById(orderItem.getProduct().getId()).orElse(null);
            orderItem.setProduct(product);
            order.addOrderItem(orderItem);
        }

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
