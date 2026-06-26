package com.hulkhiretech.payments.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.pojo.PaymentNotificationRequest;
import com.hulkhiretech.payments.service.PaymentStatusService;
import com.hulkhiretech.payments.service.PaymentNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentNotificationServiceImpl implements PaymentNotificationService {

    private final TransactionDao transactionDao;

    private final ModelMapper modelMapper;

    private final PaymentStatusService paymentStatusService;

    @Override
    @Transactional
    public void processNotification(PaymentNotificationRequest request) {

        log.info("Received notification : {}", request);

        TransactionEntity entity =
                transactionDao.getTransactionByReference(
                        request.getTxnReference());

        if (entity == null) {
            log.error("Transaction not found : {}", request.getTxnReference());
            throw new IllegalArgumentException(
                    "Transaction not found : " + request.getTxnReference());
        }

        TransactionDTO dto =
                modelMapper.map(entity, TransactionDTO.class);

        dto.setTxnStatus(
                TransactionStatusEnum
                        .fromLabel(request.getTxnStatus())
                        .getLabel());

        dto.setProviderReference(
                request.getProviderReference());

        dto.setErrorCode(
                request.getErrorCode());

        dto.setErrorMessage(
                request.getErrorMessage());

        paymentStatusService.updatePayment(dto);

        log.info("Transaction updated successfully : {}",
                dto.getTxnReference());

    }

}