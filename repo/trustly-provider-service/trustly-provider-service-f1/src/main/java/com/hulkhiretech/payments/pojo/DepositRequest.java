package com.hulkhiretech.payments.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class DepositRequest {
	private String txnReference;
	private String  endUserId;
	
	private final String amount;
	private final String currency;
	
	
	private String firstName;
	private String lastName;
	private String email;
	
	private String Country;
	private final String locale;
	
	private String successUrl;
	private String failUrl;
}
