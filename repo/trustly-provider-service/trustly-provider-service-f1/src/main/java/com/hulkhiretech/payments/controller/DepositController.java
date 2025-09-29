package com.hulkhiretech.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.constant.EndPoints;
import com.hulkhiretech.payments.pojo.DepositRequest;
import com.hulkhiretech.payments.pojo.DepositResponse;
import com.hulkhiretech.payments.service.DepositService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(EndPoints.V1_TRUSTLY_DEPOSITS)
@Slf4j
@RequiredArgsConstructor
public class DepositController {
	private final  DepositService depositService;

	@PostMapping
	public ResponseEntity<DepositResponse> createDeposit(@RequestBody DepositRequest depositRequest) {
		log.info("Creating deposit");
		//log.debug("DepositRequest: {}", depositRequest);
		DepositResponse response = depositService.createDeposit(depositRequest);
		log.info("Deposit created successfully with response: {}", response);
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}

}
