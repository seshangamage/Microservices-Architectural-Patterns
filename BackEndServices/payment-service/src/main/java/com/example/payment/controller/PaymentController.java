package com.example.payment.controller;

import com.example.payment.model.Payment;
import com.example.payment.repository.PaymentRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping
    public List<Payment> list() {
        return paymentRepository.findAll();
    }

    @PostMapping
    public Payment create(@RequestBody Payment payment) {
        return paymentRepository.save(payment);
    }
}
