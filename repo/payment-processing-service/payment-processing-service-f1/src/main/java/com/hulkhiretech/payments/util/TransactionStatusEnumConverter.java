package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.TransactionStatusEnum;

public class TransactionStatusEnumConverter extends AbstractConverter<String, Integer> {

	@Override
	protected Integer convert(String source) {
		return TransactionStatusEnum.fromLabel(source).getCode();
	}

	public static TransactionStatusEnum fromCode(int code) {
		return TransactionStatusEnum.fromId(code);
	}

	public static TransactionStatusEnum fromLabel(String label) {
		return TransactionStatusEnum.fromLabel(label);
	}

}
