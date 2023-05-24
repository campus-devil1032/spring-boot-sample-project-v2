package com.example.campusspringdemov2.global.response.normal;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Http Status Code 4XX
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response4xx {

    private String message;
    private int status;
    private String code;
    private Object data;


    private Response4xx(final ResponseCode code, final Object data) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = data;
    }

    private Response4xx(final ResponseCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static Response4xx of(final ResponseCode code) {
        return new Response4xx(code);
    }

    public static Response4xx of(final ResponseCode code, final Object successes) {
        return new Response4xx(code, successes);
    }

}
