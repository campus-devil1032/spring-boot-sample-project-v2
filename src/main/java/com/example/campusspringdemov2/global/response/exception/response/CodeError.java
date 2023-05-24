package com.example.campusspringdemov2.global.response.exception.response;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 요청자에게 리턴 할 Htts Status Code / 자체 관리 Error code / 메세지를 열거화
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CodeError {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " 입력 값이 잘못되었습니다."),
    METHOD_NOT_ALLOWED(405, "C002", " 해당 Method는 사용 할 수 없습니다."),
    ENTITY_NOT_FOUND(400, "C003", " Entity를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 에러"),
    INVALID_TYPE_VALUE(400, "C005", " 값 타입이 잘못되었습니다."),
    HANDLE_ACCESS_DENIED(403, "C006", " 접근이 거부되었습니다."),
    HANDLE_NOT_FOUND(404, "C007", " 요청을 찾을 수 없습니다."),

    // Header
    HEADER_VALUE_NULL(400, "H001", "요청 헤더에 'apiKey' 필드가 없습니다."),
    INVALID_HEADER_VALUE(403, "H001", "요청 헤더에 'apiKey' 인증이 실패했습니다."),

    // Date
    INVALID_DATE_RANGE(400, "D001", "시작날짜와 종료날짜가 올바르지 않습니다."),
    DATE_RANGE_TOO_LONG(400, "D001", "조회 기간은 1년을 넘을 수 없습니다."),

    // Bucket4j
    BUCKET4J_APIKEY_NULL(400, "BUCKET001", "요청 헤더에 'apiKey' 필드가 없습니다.")
    ;

    /* 계속 추가 가능 */

    private final String code;
    private final String message;
    private final int status;

    CodeError(final int status, final String code, final String message) {
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
