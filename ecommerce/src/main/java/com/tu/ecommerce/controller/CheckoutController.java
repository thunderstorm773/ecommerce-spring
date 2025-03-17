package com.tu.ecommerce.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.tu.ecommerce.model.bindingModel.CreatePurchase;
import com.tu.ecommerce.model.bindingModel.CreatePurchaseResponse;
import com.tu.ecommerce.model.bindingModel.PaymentInfo;
import com.tu.ecommerce.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public CreatePurchaseResponse placeOrder(@RequestBody CreatePurchase createPurchase) {
        return this.checkoutService.placeOrder(createPurchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = this.checkoutService.createPaymentIntent(paymentInfo);
        String paymentIntentStr = paymentIntent.toJson();

        return ResponseEntity.ok(paymentIntentStr);
    }
}
