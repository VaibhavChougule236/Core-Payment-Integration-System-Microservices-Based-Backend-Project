package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.TransactionStatusEnum;

public class TransactionStatusIdToNameConverter extends AbstractConverter<Integer, String> {

	@Override
	protected String convert(Integer source) {
		return TransactionStatusEnum.fromId(source).getLabel();
	}


}
