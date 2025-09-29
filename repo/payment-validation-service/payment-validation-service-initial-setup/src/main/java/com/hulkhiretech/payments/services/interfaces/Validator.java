package com.hulkhiretech.payments.services.interfaces;

import com.hulkhiretech.payments.pojo.req.PaymentRequest;

public interface Validator {
    void validate(PaymentRequest request);
}
