package com.hulkhiretech.payments.services.implementation.validator;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.services.interfaces.Validator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Check2Validator implements Validator {

	@Override
	public void validate(PaymentRequest request) {
		log.info("Validating payment request: {}", request);
	}

}
