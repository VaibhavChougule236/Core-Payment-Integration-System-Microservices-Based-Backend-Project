package com.hulkhiretech.payments.service.impl.statushandler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.service.interfaces.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SuccessStatusHandler implements TransactionStatusHandler {

    private final TransactionDao transactionDao;

    private final ModelMapper modelMapper;

    @Override
    public TransactionDTO handleTransactionStatus(TransactionDTO transactionDTO) {

        log.info("Handling SUCCESS status for transaction: {}",
                transactionDTO.getTxnReference());

        TransactionEntity entity =
                modelMapper.map(transactionDTO,
                        TransactionEntity.class);

        boolean updated =
                transactionDao.updateTransaction(entity);

        if (!updated) {
            log.error("Failed to update transaction {}",
                    transactionDTO.getTxnReference());

            throw new RuntimeException(
                    "Unable to update transaction status to SUCCESS");
        }

        log.info("Transaction updated successfully.");

        return transactionDTO;
    }
}