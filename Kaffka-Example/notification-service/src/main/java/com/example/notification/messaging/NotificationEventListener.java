/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.kafka.annotation.KafkaListener
 *  org.springframework.stereotype.Component
 */
package com.example.notification.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEventListener.class);

    @KafkaListener(topics={"order.created"}, groupId="notification-service-group")
    public void onOrderCreated(String payload) {
        LOGGER.info("Notification -> order.created event received: {}", (Object)payload);
    }

    @KafkaListener(topics={"inventory.reserved"}, groupId="notification-service-group")
    public void onInventoryReserved(String payload) {
        LOGGER.info("Notification -> inventory.reserved event received: {}", (Object)payload);
    }
}
