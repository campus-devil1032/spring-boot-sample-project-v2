package com.example.campusspringdemov2.global.response.exception.business;


import com.example.campusspringdemov2.global.response.exception.response.CodeError;

public class BusinessException extends RuntimeException {

    private final CodeError codeError;

    public BusinessException(String message, CodeError codeError) {
        super(message);
        this.codeError = codeError;
    }

    public BusinessException(CodeError codeError) {
        super(codeError.getMessage());
        this.codeError = codeError;
    }

    public CodeError getErrorCode() {
        return codeError;
    }

}
