package com.hulkhiretech.payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hulkhiretech.payments.constant.EndPoints;
import com.hulkhiretech.payments.pojo.NotificationRequest;
import com.hulkhiretech.payments.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EndPoints.PAYMENT_BASE_URI)
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<String> receiveNotification(
            @RequestBody NotificationRequest notificationRequest) {

        log.info("Received notification from Mock Trustly Gateway");

        log.info("NotificationRequest : {}", notificationRequest);

        notificationService.processNotification(notificationRequest);

        log.info("Notification processed successfully");

        return ResponseEntity.ok("Notification processed successfully");
    }
}