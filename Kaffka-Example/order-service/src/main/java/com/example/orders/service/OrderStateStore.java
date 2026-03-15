/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Component
 */
package com.example.orders.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class OrderStateStore {
    private final Map<String, String> orderStatuses = new ConcurrentHashMap<String, String>();

    public void setStatus(String orderId, String status) {
        this.orderStatuses.put(orderId, status);
    }

    public String getStatus(String orderId) {
        return this.orderStatuses.getOrDefault(orderId, "UNKNOWN");
    }
}
