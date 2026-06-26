package com.hulkhiretech.payments.pojo;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTxnRequest {

	@NotNull
	@Positive
	private Integer userId;

	@NotBlank
	private String provider;

	@NotBlank
	private String paymentMethod;

	@NotBlank
	private String paymentType;

	@NotNull
	@DecimalMin("1.00")
	private BigDecimal amount;

	@NotBlank
	@Size(min=3,max=3)
	private String currency;

	@NotBlank
	private String merchantTransactionReference;
    
}
