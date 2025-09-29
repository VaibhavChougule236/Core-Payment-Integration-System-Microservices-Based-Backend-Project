package com.hulkhiretech.payments.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatusEnum {
	CREATED(1, "CREATED"), INITIATED(2, "INITIATED"), PENDING(3, "PENDING"), SUCCESS(4, "SUCCESS"), FAILED(5, "FAILED");

	private final int code;
	private final String label;
	
	public static TransactionStatusEnum fromId(int id) {
		for (TransactionStatusEnum status : values()) {
			if (status.getCode() == id) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid transaction status ID: " + id);
	}
	
	public static TransactionStatusEnum fromLabel(String label) {
		for (TransactionStatusEnum status : values()) {
			if (status.getLabel().equalsIgnoreCase(label)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid transaction status label: " + label);
	}
}