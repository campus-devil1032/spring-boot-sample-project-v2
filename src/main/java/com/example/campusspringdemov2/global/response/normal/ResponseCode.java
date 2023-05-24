package com.example.campusspringdemov2.global.response.normal;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {

    // Common
    RESPONSE_OK(200, "COMP001", "Success"),
    CREATE_COMPLETE(201, "COMP002", "Create Completed"),

    // Bucket4j
    BUCKET4J_APIKEY_NULL(400, "BUCKET001", "요청 헤더에 'apiKey' 필드가 없습니다."),
    BUCKET4J_APIKEY_QUOTA_OVER(429, "BUCKET002", "API 요청 할당량을 모두 사용했습니다."),

    ;

    /* 계속 추가 가능 */

    private final String code;
    private final String message;
    private final int status;

    ResponseCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}

/**
 * 요청자에게 리턴 할 Htts Status Code / 자체 관리 Error code / 메세지를 열거화
 */

