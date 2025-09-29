package com.hulkhiretech.payments.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProviderEnum {
	TRUSTLY(1, "TRUSTLY");

	private final int code;
	private final String label;

	public static ProviderEnum fromLabel(String label) {
		for (ProviderEnum type : ProviderEnum.values()) {
			if (type.getLabel().equalsIgnoreCase(label)) {
				return type;
			}
		}
		throw new IllegalArgumentException("No ProviderEnum found for label: " + label);
	}

	public static ProviderEnum fromCode(Integer source) {
		for (ProviderEnum type : ProviderEnum.values()) {
			if (type.getCode() == source) {
				return type;
			}
		}
		throw new IllegalArgumentException("No ProviderEnum found for code: " + source);
	}

}