package com.hulkhiretech.payments.services.implementation;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hulkhiretech.payments.constant.ErrorEnum;
import com.hulkhiretech.payments.constant.ValidatorEnum;
import com.hulkhiretech.payments.exception.ValidationException;
import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.services.interfaces.HMacSHA256Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HMacSHA256ServiceImpl implements HMacSHA256Service {

	private final Gson gson;
	
	@Value("${merchant.client.id}")
	private String clientId;
	
	@Value("${hmac.secret.key}")
	private String secretKey;

	@Override
	public String generateHMacSHA256Signeture(String jsonData) {

		
		String signature = null;

		try {
			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);

			byte[] signatureBytes = mac.doFinal(jsonData.getBytes(StandardCharsets.UTF_8));
			signature = Base64.getEncoder().encodeToString(signatureBytes);

		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		log.info("Generated HMAC-SHA256 signature: {}", signature);

		return signature;
	}

	@Override
	public void verifyHMacSHA256(
	        String incomingHmacSignature,
	        String rawBody) {

	    log.info("Verifying HMAC signature: {}",
	            incomingHmacSignature);

	    if (incomingHmacSignature == null
	            || incomingHmacSignature.isBlank()) {

	        throw new ValidationException(
	                ErrorEnum.MISSING_HMAC_SIGNATURE.getErrorCode(),
	                ErrorEnum.MISSING_HMAC_SIGNATURE.getErrorMessage());
	    }

	    String generatedHmacSignature =
	            generateHMacSHA256Signeture(rawBody);

	    log.info("Generated HMAC signature: {}",
	            generatedHmacSignature);

	    if (!incomingHmacSignature.equals(
	            generatedHmacSignature)) {

	        throw new ValidationException(
	                ErrorEnum.INVALID_HMAC_SIGNATURE.getErrorCode(),
	                ErrorEnum.INVALID_HMAC_SIGNATURE.getErrorMessage());
	    }

	    log.info("HMAC validation successful");
	}

	@Override
	public String getClientId() {
		return clientId;
	}

}
