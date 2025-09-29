package com.hulkhiretech.payments.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.factory.TransactionStatusFactory;
import com.hulkhiretech.payments.service.interfaces.TransactionStatusHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class PaymentStatusService {
//	
//	private final TransactionStatusFactory transactionStatusFactory;
//	private final Map<TransactionStatusEnum, TransactionStatusHandler> handlers;
//	
//	public TransactionDTO updatePayment(TransactionDTO txnDTO) {
//		String txnStatus = txnDTO.getTxnStatus();
//		log.info("Passnig DTO to TransactionStatusHandler txnDTO: {}", txnDTO);
//		
//		TransactionStatusEnum statusEnum= TransactionStatusEnum.fromLabel(txnStatus);
////		TransactionStatusHandler statusHandler=transactionStatusFactory.getTransactionStatusHandler(statusEnum);
//		TransactionStatusHandler statusHandler=handlers.get(statusEnum);
//		log.info("Retrieved TransactionStatusHandler for statusEnum: {}", statusEnum);
//		
//		if (statusHandler == null) {
//			log.error("No TransactionStatusHandler found for statusEnum: {}", statusEnum);
//			
//			throw new RuntimeException("Invalid transaction status statusEnum: " + statusEnum);
//		}
//		log.info("Retrieved TransactionStatusHandler: {}", statusHandler);
//		
//		txnDTO = statusHandler.handleTransactionStatus(txnDTO);
//		return txnDTO;
//	}
//
//
//}

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentStatusService {

    //private final Map<String, TransactionStatusHandler> handlers;
	
	private final TransactionStatusFactory transactionStatusFactory;

    public TransactionDTO updatePayment(TransactionDTO txnDTO) {
//        String statusEnumName = txnDTO.getTxnStatus().toLowerCase();
//        String handlerBeanName = statusEnumName + "StatusHandler";
        
        TransactionStatusEnum statusEnum = TransactionStatusEnum.fromLabel(txnDTO.getTxnStatus());
        
        //TransactionStatusHandler handler = handlers.get(statusEnum);
        
        TransactionStatusHandler handler= transactionStatusFactory.getTransactionStatusHandler(statusEnum);

        log.info("Looking up handler bean: {}", handler);

        //TransactionStatusHandler handler = handlers.get(handlerBeanName);
        if (handler == null) {
            log.error("No TransactionStatusHandler found for bean name: {}", handler);
            throw new RuntimeException("Invalid transaction status: " + handler);
        }

        return handler.handleTransactionStatus(txnDTO);
    }
}

