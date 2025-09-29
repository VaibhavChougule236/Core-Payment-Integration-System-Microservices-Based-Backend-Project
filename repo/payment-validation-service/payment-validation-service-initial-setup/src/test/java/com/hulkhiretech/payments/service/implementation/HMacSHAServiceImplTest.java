package com.hulkhiretech.payments.service.implementation;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.services.implementation.HMacSHA256ServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HMacSHAServiceImplTest {
private Gson gson = new Gson();
	
	private HMacSHA256ServiceImpl hMacSHA256ServiceImpl = 
			new HMacSHA256ServiceImpl(gson);

	@Test
	public void testMethod() {
		log.info("This is a test method in HMacSHA256ServiceImplTest");
		
		PaymentRequest request = new PaymentRequest();
        request.setAmount("100.00");
        request.setCurrency("EUR");
        request.setPaymentMethod("CARD");
        request.setPaymentType("SALE");
        request.setProvider("TRUSTLY");
        request.setCustomerId("3232");
        request.setEmail("customer@example.com");
        request.setPhone("+491234567890");
        
		String jsonInput = gson.toJson(request);
		
		log.info("JSON Input: {}", jsonInput);
		
		String hmacSig = hMacSHA256ServiceImpl.generateHMacSHA256Signeture(jsonInput);
		
		log.info("Generated HMAC-SHA256 Signature: {}", hmacSig);
	}
}
