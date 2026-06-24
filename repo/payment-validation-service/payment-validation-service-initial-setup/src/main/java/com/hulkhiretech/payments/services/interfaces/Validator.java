package com.hulkhiretech.payments.services.interfaces;

import com.hulkhiretech.payments.pojo.req.PaymentRequest;

public interface Validator {
	String getRuleName();
    void validate(PaymentRequest request);
}
