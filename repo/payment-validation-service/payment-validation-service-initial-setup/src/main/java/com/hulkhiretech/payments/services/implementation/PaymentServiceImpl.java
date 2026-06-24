package com.hulkhiretech.payments.services.implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.factory.ValidatorFactory;
import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.pojo.res.PaymentResponse;
import com.hulkhiretech.payments.services.interfaces.PaymentService;
import com.hulkhiretech.payments.services.interfaces.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${validator.rules}")
    private String validationRules;

    private final ValidatorFactory validatorFactory;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentDetails) {

        log.info("Received payment request");

        String[] rules = validationRules.split(",");

        for (String rule : rules) {

            rule = rule.trim();

            log.info("Applying validation rule: {}", rule);

            Validator validator =
                    validatorFactory.getValidator(rule);

            if (validator == null) {

                log.warn(
                        "Validator not found for rule: {}",
                        rule);

                continue;
            }

            log.info(
                    "Executing validator: {}",
                    validator.getClass().getSimpleName());

            validator.validate(paymentDetails);
        }

        PaymentResponse paymentResponse =
                new PaymentResponse();

        String paymentId =
                UUID.randomUUID().toString();

        paymentResponse.setId(paymentId);

        paymentResponse.setStatus("VALIDATED");

        paymentResponse.setProvider(
                paymentDetails.getProvider());

        paymentResponse.setAmount(
                paymentDetails.getAmount());

        paymentResponse.setCurrency(
                paymentDetails.getCurrency());

        paymentResponse.setRedirectUrl(
                "https://example.com/redirect?paymentId="
                        + paymentId);

        log.info(
                "Validation completed successfully. PaymentId={}",
                paymentId);

        return paymentResponse;
    }
}