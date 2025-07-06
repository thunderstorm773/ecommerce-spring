package com.tu.ecommerce.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.tu.ecommerce.dao.CustomerRepository;
import com.tu.ecommerce.dao.ProductRepository;
import com.tu.ecommerce.dao.SystemParameterRepository;
import com.tu.ecommerce.entity.*;
import com.tu.ecommerce.model.bindingModel.CreateOrderItem;
import com.tu.ecommerce.model.bindingModel.CreatePurchase;
import com.tu.ecommerce.model.bindingModel.CreatePurchaseResponse;
import com.tu.ecommerce.model.bindingModel.PaymentInfo;
import com.tu.ecommerce.util.ModelMapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CheckoutService {

    private static final String SHOW_BGN_CURRENCY_FIRST_CODE = "SHOW_BGN_CURRENCY_FIRST";
    private static final String BGN_EUR_EXCHANGE_RATE_CODE = "BGN_EUR_EXCHANGE_RATE";

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final SystemParameterRepository systemParameterRepository;

    private final ModelMapperUtil modelMapperUtil;

    public CheckoutService(CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           SystemParameterRepository systemParameterRepository,
                           ModelMapperUtil modelMapperUtil,
                           @Value("${stripe.key.secret}") String stripeSecretKey) {

        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.systemParameterRepository = systemParameterRepository;
        this.modelMapperUtil = modelMapperUtil;
        Stripe.apiKey = stripeSecretKey;
    }

    @Transactional
    public CreatePurchaseResponse placeOrder(CreatePurchase purchase) {
        Order order = this.modelMapperUtil.getModelMapper().map(purchase.getOrder(), Order.class);

        String orderTrackingNumber = this.generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
        this.fillOrderItems(purchase, order);

        Address shippingAddress = this.modelMapperUtil.getModelMapper().map(purchase.getShippingAddress(), Address.class);
        order.setShippingAddress(shippingAddress);

        Address billingAddress = this.modelMapperUtil.getModelMapper().map(purchase.getBillingAddress(), Address.class);
        order.setBillingAddress(billingAddress);

        // check for existing user
        Customer customer = this.customerRepository.findByEmail(purchase.getCustomer().getEmail());
        if(customer == null) {
            customer = this.modelMapperUtil.getModelMapper().map(purchase.getCustomer(), Customer.class);
        }

        customer.addOrder(order);

        this.customerRepository.save(customer);
        return new CreatePurchaseResponse(orderTrackingNumber);
    }

    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "ECommerce");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    private void fillOrderItems(CreatePurchase purchase, Order order) {
        SystemParameter showBgnCurrencyFirstParam = this.systemParameterRepository.findByCode(SHOW_BGN_CURRENCY_FIRST_CODE);

        if ("1".equals(showBgnCurrencyFirstParam.getValue())) {
            BigDecimal totalPriceEur = this.calculateOtherPrice(order.getTotalPrice(), showBgnCurrencyFirstParam);
            order.setTotalPriceEur(totalPriceEur);
        } else {
            order.setTotalPriceEur(order.getTotalPrice());
            BigDecimal totalPrice = this.calculateOtherPrice(order.getTotalPrice(), showBgnCurrencyFirstParam);
            order.setTotalPrice(totalPrice);
        }

        List<CreateOrderItem> createOrderItems = purchase.getOrderItems().stream().toList();
        List<OrderItem> mappedOrderItems = this.modelMapperUtil.convertAll(createOrderItems, OrderItem.class);
        Set<OrderItem> orderItems = new HashSet<>(mappedOrderItems);

        for (OrderItem orderItem : orderItems) {
            Product product = this.productRepository.findById(orderItem.getProduct().getId()).orElse(null);
            if (product != null) {
                int newUnitsInStock = Math.max(product.getUnitsInStock() - orderItem.getQuantity(), 0);
                product.setUnitsInStock(newUnitsInStock);
            }

            if ("1".equals(showBgnCurrencyFirstParam.getValue())) {
                BigDecimal unitPriceEur = this.calculateOtherPrice(orderItem.getUnitPrice(), showBgnCurrencyFirstParam);
                orderItem.setUnitPriceEur(unitPriceEur);
            } else {
                orderItem.setUnitPriceEur(orderItem.getUnitPrice());
                BigDecimal unitPrice = this.calculateOtherPrice(orderItem.getUnitPrice(), showBgnCurrencyFirstParam);
                orderItem.setUnitPrice(unitPrice);
            }

            orderItem.setProduct(product);
            order.addOrderItem(orderItem);
        }
    }

    private BigDecimal calculateOtherPrice(BigDecimal price, SystemParameter showBgnCurrencyFirstParam) {
        SystemParameter bgnEurExchangeRateParam = this.systemParameterRepository.findByCode(BGN_EUR_EXCHANGE_RATE_CODE);

        BigDecimal exchangeRate = new BigDecimal(bgnEurExchangeRateParam.getValue());
        if ("1".equals(showBgnCurrencyFirstParam.getValue())) {
            return price.divide(exchangeRate, 2, RoundingMode.HALF_UP);
        } else {
            return price.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        }
    }
}
