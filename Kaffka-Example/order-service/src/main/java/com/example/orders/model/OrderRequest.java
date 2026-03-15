/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.constraints.Min
 *  jakarta.validation.constraints.NotBlank
 */
package com.example.orders.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record OrderRequest(@NotBlank String orderId, @NotBlank String sku, @Min(value=1L) @Min(value=1L) int quantity) {
}
