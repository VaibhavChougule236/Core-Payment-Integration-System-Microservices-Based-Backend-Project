package com.hulkhiretech.payments.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hulkhiretech.payments.constant.Constant;
import com.hulkhiretech.payments.pojo.req.PaymentRequest;
import com.hulkhiretech.payments.pojo.res.PaymentResponse;
import com.hulkhiretech.payments.services.interfaces.PaymentService;
import com.hulkhiretech.payments.util.LogUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constant.PAYMENT_ENDPOINT)
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest paymentRequest) {

        log.info(
                "Payment request received for customer {}",
                LogUtil.maskCustomer(
                        paymentRequest.getCustomerId()));

        PaymentResponse response =
                paymentService.createPayment(paymentRequest);

        log.info(
                "Payment validated successfully. PaymentId={}",
                response.getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}