package com.hulkhiretech.payments.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hulkhiretech.payments.services.interfaces.Validator;

@Component
public class ValidatorFactory {

    private final Map<String, Validator> validatorMap;

    public ValidatorFactory(List<Validator> validators) {

        validatorMap = new HashMap<>();

        validators.forEach(
                validator ->
                        validatorMap.put(
                                validator.getRuleName(),
                                validator));
    }

    public Validator getValidator(String ruleName) {

        return validatorMap.get(ruleName);
    }
}