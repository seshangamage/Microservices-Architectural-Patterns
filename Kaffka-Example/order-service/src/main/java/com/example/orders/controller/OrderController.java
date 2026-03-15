/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.Valid
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseStatus
 *  org.springframework.web.bind.annotation.RestController
 */
package com.example.orders.controller;

import com.example.orders.model.OrderRequest;
import com.example.orders.service.OrderEventPublisher;
import com.example.orders.service.OrderStateStore;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/orders"})
public class OrderController {
    private final OrderEventPublisher orderEventPublisher;
    private final OrderStateStore orderStateStore;

    public OrderController(OrderEventPublisher orderEventPublisher, OrderStateStore orderStateStore) {
        this.orderEventPublisher = orderEventPublisher;
        this.orderStateStore = orderStateStore;
    }

    @PostMapping
    @ResponseStatus(value=HttpStatus.ACCEPTED)
    public Map<String, String> createOrder(@Valid @RequestBody OrderRequest request) {
        this.orderStateStore.setStatus(request.orderId(), "CREATED");
        this.orderEventPublisher.publishOrderCreated(request);
        return Map.of("orderId", request.orderId(), "status", "CREATED", "message", "Order accepted and event published");
    }

    @GetMapping(value={"/{orderId}"})
    public Map<String, String> getOrderStatus(@PathVariable String orderId) {
        return Map.of("orderId", orderId, "status", this.orderStateStore.getStatus(orderId));
    }
}
