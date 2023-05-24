package com.example.campusspringdemov2.domain.trade.exception;


import com.example.campusspringdemov2.global.response.exception.business.BusinessException;
import com.example.campusspringdemov2.global.response.exception.response.CodeError;

public class DateRangeTooLongException extends BusinessException {

    public DateRangeTooLongException() {
        super(CodeError.DATE_RANGE_TOO_LONG);
    }
}
