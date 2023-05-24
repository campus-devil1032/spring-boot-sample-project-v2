package com.example.campusspringdemov2.domain.trade.exception;


import com.example.campusspringdemov2.global.response.exception.business.BusinessException;
import com.example.campusspringdemov2.global.response.exception.response.CodeError;

public class DateRangeException extends BusinessException {

    public DateRangeException() {
        super(CodeError.INVALID_DATE_RANGE);
    }
}
