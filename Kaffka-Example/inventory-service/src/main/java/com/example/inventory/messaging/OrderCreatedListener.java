/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.kafka.annotation.KafkaListener
 *  org.springframework.stereotype.Component
 */
package com.example.inventory.messaging;

import com.example.inventory.service.InventoryEventPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedListener.class);
    private final ObjectMapper objectMapper;
    private final InventoryEventPublisher inventoryEventPublisher;

    public OrderCreatedListener(ObjectMapper objectMapper, InventoryEventPublisher inventoryEventPublisher) {
        this.objectMapper = objectMapper;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    @KafkaListener(topics={"order.created"}, groupId="inventory-service-group")
    public void handleOrderCreated(String payload) {
        try {
            JsonNode root = this.objectMapper.readTree(payload);
            String orderId = root.path("orderId").asText();
            String sku = root.path("sku").asText();
            int quantity = root.path("quantity").asInt(0);
            boolean reserved = quantity > 0;
            LOGGER.info("Inventory processed orderId={}, sku={}, quantity={}, reserved={}", new Object[]{orderId, sku, quantity, reserved});
            this.inventoryEventPublisher.publishInventoryReserved(orderId, sku, quantity, reserved);
        }
        catch (Exception ex) {
            LOGGER.error("Failed to process order.created event: {}", (Object)payload, (Object)ex);
        }
    }
}
