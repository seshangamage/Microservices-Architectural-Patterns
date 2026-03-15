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
package com.example.orders.messaging;

import com.example.orders.service.OrderStateStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryReservedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryReservedListener.class);
    private final ObjectMapper objectMapper;
    private final OrderStateStore orderStateStore;

    public InventoryReservedListener(ObjectMapper objectMapper, OrderStateStore orderStateStore) {
        this.objectMapper = objectMapper;
        this.orderStateStore = orderStateStore;
    }

    @KafkaListener(topics={"inventory.reserved"}, groupId="order-service-group")
    public void handleInventoryReserved(String payload) {
        try {
            JsonNode root = this.objectMapper.readTree(payload);
            String orderId = root.path("orderId").asText();
            boolean reserved = root.path("reserved").asBoolean(false);
            String nextState = reserved ? "INVENTORY_RESERVED" : "INVENTORY_FAILED";
            this.orderStateStore.setStatus(orderId, nextState);
            LOGGER.info("Order {} updated by inventory event -> {}", (Object)orderId, (Object)nextState);
        }
        catch (Exception ex) {
            LOGGER.error("Failed to process inventory.reserved event: {}", (Object)payload, (Object)ex);
        }
    }
}
