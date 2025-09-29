package com.hulkhiretech.payments.services.interfaces;

import com.hulkhiretech.payments.pojo.req.PaymentRequest;

public interface HMacSHA256Service  {
	
	String generateHMacSHA256Signeture(String paymentRequest);

	void verifyHMacSHA256(String hmacSigneure,PaymentRequest paymentRequest );

	String getClientId();

}
