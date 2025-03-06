package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.OrderRepository;
import com.tu.ecommerce.entity.Order;
import com.tu.ecommerce.model.viewModel.OrderView;
import com.tu.ecommerce.util.ModelMapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ModelMapperUtil modelMapperUtil;

    public OrderService(OrderRepository orderRepository,
                        ModelMapperUtil modelMapperUtil) {

        this.orderRepository = orderRepository;
        this.modelMapperUtil = modelMapperUtil;
    }

    public Page<OrderView> getAllOrdersByCustomerEmail(String customerEmail, Pageable pageable) {
        Page<Order> orders = this.orderRepository.findAllByCustomer_Email(customerEmail, pageable);
        return this.modelMapperUtil.convertToPage(pageable, orders, OrderView.class);
    }
}
