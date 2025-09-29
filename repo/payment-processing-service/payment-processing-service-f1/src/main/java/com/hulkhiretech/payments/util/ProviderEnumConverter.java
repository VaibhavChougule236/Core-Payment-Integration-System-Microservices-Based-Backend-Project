package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.ProviderEnum;

public class ProviderEnumConverter extends AbstractConverter<String, Integer> {

	@Override
	protected Integer convert(String source) {
		return ProviderEnum.fromLabel(source).getCode();
	}

	

}
