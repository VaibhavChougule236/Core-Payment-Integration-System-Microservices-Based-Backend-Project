package com.hulkhiretech.payments.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.hulkhiretech.payments.constant.ErrorEnum;
import com.hulkhiretech.payments.exception.TrustlyProviderException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpServiceEngine {

	private final RestClient restClient;

	public ResponseEntity<String> httpCall(@RequestBody HttpRequest httpRequest) {
		log.info("Making HTTP call in HttpServiceEngine");

		try {
			ResponseEntity<String> response = restClient.method(httpRequest.getMethod()).uri(httpRequest.getUrl())
					.contentType(MediaType.APPLICATION_JSON).body(httpRequest.getBody()).retrieve()
					.toEntity(String.class);
			log.debug("HTTP response: {}", response);
			return response;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error("HTTP error occurred: {}", e.getMessage(), e);
			if(e.getStatusCode()==HttpStatus.GATEWAY_TIMEOUT) {
				throw new TrustlyProviderException(ErrorEnum.UNABLE_TO_CONNECT_TRUSTLY.getErrorCode(), ErrorEnum.UNABLE_TO_CONNECT_TRUSTLY.getErrorMessage(), HttpStatus.GATEWAY_TIMEOUT);
			}
			return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
		} catch (Exception e) {
			log.error("Error during HTTP call: {}", e.getMessage(), e);
			throw new TrustlyProviderException(ErrorEnum.UNABLE_TO_CONNECT_TRUSTLY.getErrorCode(),
					ErrorEnum.UNABLE_TO_CONNECT_TRUSTLY.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
