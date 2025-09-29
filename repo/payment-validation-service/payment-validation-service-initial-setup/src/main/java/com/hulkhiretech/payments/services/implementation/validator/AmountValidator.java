//package com.hulkhiretech.payments.services.implementation.validator;
//
//import com.hulkhiretech.payments.pojo.req.PaymentRequest;
//import com.hulkhiretech.payments.services.interfaces.Validator;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AmountValidator implements Validator {
//    @Override
//    public boolean validate(PaymentRequest request) {
//        try {
//            double amount = Double.parseDouble(request.getAmount());
//            return amount > 0;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
