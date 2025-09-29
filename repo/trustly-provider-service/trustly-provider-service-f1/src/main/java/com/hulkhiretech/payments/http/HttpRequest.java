package com.hulkhiretech.payments.http;

import org.springframework.http.HttpMethod;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HttpRequest {
	private String url;
	private HttpMethod method;
	private String body;

}
