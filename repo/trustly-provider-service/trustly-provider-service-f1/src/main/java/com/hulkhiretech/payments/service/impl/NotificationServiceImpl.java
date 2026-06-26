package com.hulkhiretech.payments.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.pojo.NotificationRequest;
import com.hulkhiretech.payments.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final HttpServiceEngine httpServiceEngine;
    
    @Value("${processing.notification.url}")
    private String processingNotificationUrl;

    private final Gson gson;

    @Override
    public void processNotification(NotificationRequest notificationRequest) {

        log.info("Received Notification : {}", notificationRequest);

        HttpRequest request = new HttpRequest();

        request.setMethod(HttpMethod.POST);

        request.setUrl(processingNotificationUrl);

        request.setBody(gson.toJson(notificationRequest));

        log.info("Calling Processing Service...");

        ResponseEntity<String> response =
                httpServiceEngine.httpCall(request);

        log.info("Processing Service Response : {}",
                response.getBody());
    }
}