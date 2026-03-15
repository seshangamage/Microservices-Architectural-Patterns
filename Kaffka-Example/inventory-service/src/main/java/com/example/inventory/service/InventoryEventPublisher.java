/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.springframework.kafka.core.KafkaTemplate
 *  org.springframework.stereotype.Service
 */
package com.example.inventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.LinkedHashMap;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventPublisher {
    public static final String INVENTORY_RESERVED_TOPIC = "inventory.reserved";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public InventoryEventPublisher(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishInventoryReserved(String orderId, String sku, int quantity, boolean reserved) {
        LinkedHashMap<String, Object> payload = new LinkedHashMap<String, Object>();
        payload.put("orderId", orderId);
        payload.put("sku", sku);
        payload.put("quantity", quantity);
        payload.put("reserved", reserved);
        payload.put("eventType", "INVENTORY_RESERVED");
        payload.put("timestamp", Instant.now().toString());
        try {
            this.kafkaTemplate.send(INVENTORY_RESERVED_TOPIC, (Object)orderId, (Object)this.objectMapper.writeValueAsString(payload));
        }
        catch (JsonProcessingException ex) {
            throw new IllegalStateException("Failed to serialize inventory event", ex);
        }
    }
}
