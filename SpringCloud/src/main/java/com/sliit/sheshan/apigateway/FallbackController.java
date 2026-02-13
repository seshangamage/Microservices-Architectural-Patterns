package com.sliit.sheshan.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/order")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String orderFallback() {
        return "Order service is unavailable. Please try again later.";
    }

    @GetMapping("/fallback/payment")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String paymentFallback() {
        return "Payment service is unavailable. Please try again later.";
    }

    @GetMapping("/fallback/inventory")
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String inventoryFallback() {
        return "Inventory service is unavailable. Please try again later.";
    }
}
