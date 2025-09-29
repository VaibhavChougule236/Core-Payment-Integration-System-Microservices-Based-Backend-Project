package com.hulkhiretech.payments.service;

import org.springframework.http.ResponseEntity;

import com.hulkhiretech.payments.pojo.DepositRequest;
import com.hulkhiretech.payments.pojo.DepositResponse;

public interface DepositService {
	
	 DepositResponse createDeposit(DepositRequest depositRequest);

}
