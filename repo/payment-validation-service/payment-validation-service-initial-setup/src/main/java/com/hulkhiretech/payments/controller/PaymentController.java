package com.hulkhiretech.payments.controller;

import com.hulkhiretech.payments.constant.Constant;
import com.hulkhiretech.payments.constant.ValidatorEnum;
import com.hulkhiretech.payments.exception.ValidationException;
import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.pojo.res.PaymentResponse;
import com.hulkhiretech.payments.services.implementation.PaymentServiceImpl;
import com.hulkhiretech.payments.services.interfaces.HMacSHA256Service;
import com.hulkhiretech.payments.services.interfaces.PaymentService;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(Constant.PAYMENT_ENDPOINT)
public class PaymentController {

	@Autowired
    private final PaymentService paymentService;
	
//	@Autowired
//	private HMacSHA256Service hMacSHA256Service;
	
	private final org.slf4j.Logger logger= LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest
//    		@RequestHeader(value="hmac-signeure", required=false) String hmacSigneure
    		) {
        log.info("Received paymentRequest: {}", paymentRequest);
        logger.info("Received paymentRequest: {}", paymentRequest);
        log.warn("Received paymentRequest: {}", paymentRequest);
        log.debug("Received paymentRequest: {}", paymentRequest);
        log.error("Received paymentRequest: {}", paymentRequest);
        log.trace("Received paymentRequest: {}", paymentRequest);
       // log.info("Received HMAC Signature: {}", hmacSigneure);
        
//		if (hmacSigneure == null || hmacSigneure.isEmpty()) {
//			log.error("HMAC Signature is required for verification");
//			
//		}

        PaymentResponse response = paymentService.createPayment(paymentRequest);
        
		

        log.info("Payment processed successfully: {}", response);
        return response;
    }
}
