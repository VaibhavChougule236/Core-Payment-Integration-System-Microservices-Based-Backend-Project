package com.hulkhiretech.payments.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TrustlyProviderException extends RuntimeException {
	private static final long serialVersionUID = -6560387861714534572L;

	private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;
    
    @Override
    public String getMessage() {
        return errorMessage;
    }

}
