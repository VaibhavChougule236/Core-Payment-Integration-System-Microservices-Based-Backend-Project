package com.hulkhiretech.payments.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {
	SALE(1, "SALE");

	private final int code;
	private final String label;
	
	public static PaymentTypeEnum fromLabel(String label) {
		for (PaymentTypeEnum type : PaymentTypeEnum.values()) {
			if (type.getLabel().equalsIgnoreCase(label)) {
				return type;
			}
		}
		throw new IllegalArgumentException("No PaymentTypeEnum found for label: " + label);
	}
	
	public static PaymentTypeEnum fromCode(int code) {
		for (PaymentTypeEnum type : PaymentTypeEnum.values()) {
			if (type.getCode() == code) {
				return type;
			}
		}
		throw new IllegalArgumentException("No PaymentTypeEnum found for code: " + code);
	}
}