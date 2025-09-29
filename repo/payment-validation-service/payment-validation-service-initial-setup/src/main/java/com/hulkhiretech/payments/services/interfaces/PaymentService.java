package com.hulkhiretech.payments.services.interfaces;

import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.pojo.res.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
}