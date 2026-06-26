package com.hulkhiretech.payments.service;

import com.hulkhiretech.payments.pojo.PaymentNotificationRequest;

public interface PaymentNotificationService {

    void processNotification(PaymentNotificationRequest notificationRequest);

}