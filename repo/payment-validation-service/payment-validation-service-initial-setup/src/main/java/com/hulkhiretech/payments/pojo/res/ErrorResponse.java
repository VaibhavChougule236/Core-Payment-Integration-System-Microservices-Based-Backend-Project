package com.hulkhiretech.payments.pojo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private String statusCode;
    private String message;
    
}