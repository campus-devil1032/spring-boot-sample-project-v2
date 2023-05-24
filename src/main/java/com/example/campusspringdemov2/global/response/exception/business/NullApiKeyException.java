package com.example.campusspringdemov2.global.response.exception.business;


import com.example.campusspringdemov2.global.response.exception.response.CodeError;

public class NullApiKeyException extends BusinessException {
    public NullApiKeyException() {
        super(CodeError.HEADER_VALUE_NULL);
    }
}
