/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.kafka.core.KafkaTemplate
 *  org.springframework.stereotype.Service
 */
package com.example.orders.service;

import com.example.orders.model.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.LinkedHashMap;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {
    public static final String ORDER_CREATED_TOPIC = "order.created";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishOrderCreated(OrderRequest request) {
        LinkedHashMap<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("orderId", request.orderId());
        payload.put("sku", request.sku());
        payload.put("quantity", request.quantity());
        payload.put("eventType", "ORDER_CREATED");
        payload.put("timestamp", Instant.now().toString());
        try {
            String message = this.objectMapper.writeValueAsString(payload);
            this.kafkaTemplate.send(ORDER_CREATED_TOPIC, request.orderId(), message);
        }
        catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize order event", ex);
        }
    }
}
