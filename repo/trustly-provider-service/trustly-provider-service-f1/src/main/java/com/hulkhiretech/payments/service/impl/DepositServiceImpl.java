package com.hulkhiretech.payments.service.impl;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hulkhiretech.payments.TrustlyProviderServiceApplication;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.pojo.DepositRequest;
import com.hulkhiretech.payments.pojo.DepositResponse;
import com.hulkhiretech.payments.service.DepositService;
import com.hulkhiretech.payments.service.DepositServiceHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {

	private final DepositServiceHelper depositServiceHelper;
	
	private final HttpServiceEngine httpServiceEngine;
	@Override
	public DepositResponse createDeposit(DepositRequest depositRequest) {
		log.info("Creating deposit in DepositServiceImpl");
		log.debug("DepositRequest: {}", depositRequest);
		
		
		HttpRequest request = depositServiceHelper.prepareDepositRequest(
				depositRequest);
		
		ResponseEntity<String> httpResponse = 
				httpServiceEngine.httpCall(request);
		
		DepositResponse responseObj = depositServiceHelper.processResponse(
				httpResponse);
		
		
//		String jsonReqData = null;
//		
//		try {
//			 jsonReqData = "{"
//				    + "\"method\": \"Deposit\","
//				    + "\"params\": {"
//				    + "    \"Signature\": \"f4ThjuMqbsdG6u ... S16VbzD4h==\","
//				    + "    \"UUID\": \"258a2184-2842-b485-25ca-293525152425\","
//				    + "    \"Data\": {"
//				    + "        \"Username\": \"merchant_username\","
//				    + "        \"Password\": \"merchant_password\","
//				    + "        \"NotificationURL\": \"https://URL_to_your_notification_service/dajskldjakls123\","
//				    + "        \"EndUserID\": \"12345\","
//				    + "        \"MessageID\": \"your_unique_deposit_id\","
//				    + "        \"Attributes\": {"
//				    + "            \"Country\": \"SE\","
//				    + "            \"Locale\": \"sv_SE\","
//				    + "            \"Currency\": \"SEK\","
//				    + "            \"Amount\": \"103.00\","
//				    + "            \"IP\": \"123.123.123.123\","
//				    + "            \"MobilePhone\": \"+46709876543\","
//				    + "            \"Firstname\": \"John\","
//				    + "            \"Lastname\": \"Doe\","
//				    + "            \"Email\": \"test@trustly.com\","
//				    + "            \"NationalIdentificationNumber\": \"790131-1234\","
//				    + "            \"SuccessURL\": \"https://yourpage.com/success\","
//				    + "            \"FailURL\": \"https://yourpage.com/fail\""
//				    + "        }"
//				    + "    }"
//				    + "},"
//				    + "\"version\": \"1.1\""
//				    + "};";
//
//		} catch (Exception e) {
//			log.error("Error validating deposit request: {}", e.getMessage(), e);
//			return ResponseEntity.status(500).body("Internal server error");
//		}
		
		
		
//		HttpRequest httpRequest = new HttpRequest();
//		
//		httpRequest.setUrl("http://localhost:8084/payment/initiate");
//		httpRequest.setMethod(HttpMethod.POST);
//		httpRequest.setBody(jsonReqData); 
//		
//		ResponseEntity<String> response=httpServiceEngine.httpCall(httpRequest);
		
		return responseObj;
	}

}
