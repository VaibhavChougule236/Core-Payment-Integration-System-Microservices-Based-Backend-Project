package com.hulkhiretech.payments.service;

import com.hulkhiretech.payments.pojo.NotificationRequest;

public interface NotificationService {

    void processNotification(NotificationRequest notificationRequest);

}