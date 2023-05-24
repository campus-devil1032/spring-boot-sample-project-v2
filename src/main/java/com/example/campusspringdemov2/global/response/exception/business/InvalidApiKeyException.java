package com.example.campusspringdemov2.global.response.exception.business;


import com.example.campusspringdemov2.global.response.exception.response.CodeError;

public class InvalidApiKeyException extends BusinessException {

    public InvalidApiKeyException() {
        super(CodeError.INVALID_HEADER_VALUE);
    }
}
