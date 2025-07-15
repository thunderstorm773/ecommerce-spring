package com.tu.ecommerce.controller;

import com.tu.ecommerce.model.viewModel.OrderView;
import com.tu.ecommerce.model.viewModel.OrderWithItemsView;
import com.tu.ecommerce.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public OrderWithItemsView getOrderDetails(@PathVariable("id") Long id,
                                              @AuthenticationPrincipal Jwt jwt) {
        return this.orderService.getOrderById(id, jwt);
    }
}
