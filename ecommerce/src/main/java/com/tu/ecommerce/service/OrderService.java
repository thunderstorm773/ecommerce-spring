package com.tu.ecommerce.service;

import com.tu.ecommerce.dao.OrderRepository;
import com.tu.ecommerce.entity.Customer;
import com.tu.ecommerce.entity.Order;
import com.tu.ecommerce.model.viewModel.OrderView;
import com.tu.ecommerce.model.viewModel.OrderWithItemsView;
import com.tu.ecommerce.util.ModelMapperUtil;
import com.tu.ecommerce.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Page<Order> orders = this.orderRepository.findAllByCustomer_EmailOrderByDateCreatedDesc(customerEmail, pageable);
        return this.modelMapperUtil.convertToPage(pageable, orders, OrderView.class);
    }

    public Page<OrderView> getAllOrders(Pageable pageable) {
        Page<Order> orders = this.orderRepository.findAllByOrderByDateCreatedDesc(pageable);
        return this.modelMapperUtil.convertToPage(pageable, orders, OrderView.class);
    }

    public OrderWithItemsView getOrderById(Long id, Jwt jwt) {
        Order order = this.orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }

        String loggedUsername = UserUtil.getUsername(jwt);
        List<String> loggedUserAuthorities = UserUtil.getUserAuthorities(jwt);

        Customer customer = order.getCustomer();
        if(!customer.getEmail().equals(loggedUsername) && !loggedUserAuthorities.contains("Admin")) {
            throw new RuntimeException("You do not have permission to view this order details");
        }

        return this.modelMapperUtil.getModelMapper().map(order, OrderWithItemsView.class);
    }
}
