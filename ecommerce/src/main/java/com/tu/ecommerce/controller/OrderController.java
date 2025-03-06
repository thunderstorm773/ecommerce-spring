package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.OrderView;
import com.tu.ecommerce.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public Page<OrderView> getAllOrdersByCustomerEmail(@RequestParam(value = "customerEmail") String customerEmail,
                                                       Pageable pageable) {
        return this.orderService.getAllOrdersByCustomerEmail(customerEmail, pageable);
    }
}
