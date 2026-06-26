package com.hulkhiretech.payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hulkhiretech.payments.pojo.PaymentNotificationRequest;
import com.hulkhiretech.payments.service.PaymentNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentNotificationController {

    private final PaymentNotificationService paymentNotificationService;

    @PostMapping("/notification")
    public ResponseEntity<String> receiveNotification(
            @RequestBody PaymentNotificationRequest notificationRequest) {

        log.info("Received payment notification: {}", notificationRequest);

        paymentNotificationService.processNotification(notificationRequest);

        log.info("Payment notification processed successfully.");

        return ResponseEntity.ok("Notification processed successfully.");
    }
}