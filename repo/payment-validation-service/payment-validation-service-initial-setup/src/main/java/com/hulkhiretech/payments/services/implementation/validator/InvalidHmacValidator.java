package com.hulkhiretech.payments.services.implementation.validator;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.constant.ErrorEnum;
import com.hulkhiretech.payments.exception.ValidationException;
import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.services.interfaces.Validator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InvalidHmacValidator implements Validator {
	
	@Override
	public String getRuleName() {
	    return "INVALID_HMAC_SIGNATURE";
	}

    @Override
    public void validate(PaymentRequest paymentRequest) {

        log.info("Executing InvalidHmacValidator");

        if (paymentRequest == null) {
            log.error("Payment request is null");

            throw new ValidationException(
                    ErrorEnum.INVALID_HMAC_SIGNATURE.getErrorCode(),
                    ErrorEnum.INVALID_HMAC_SIGNATURE.getErrorMessage());
        }

        log.info("InvalidHmacValidator validation completed successfully");
    }
}