package com.example.campusspringdemov2.global.response.normal;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Http Status Code 2XX
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Response2xx implements Serializable {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private Object data;


    private Response2xx(final ResponseCode code, final Object data) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.data = data;
    }

    private Response2xx(final ResponseCode code) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static Response2xx of(final ResponseCode code) {
        return new Response2xx(code);
    }

    public static Response2xx of(final ResponseCode code, final Object successes) {
        return new Response2xx(code, successes);
    }


    /* 에러 내역을 항상 List 형으로 반환함 */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldReason {
        private String field;
        private String value;
        private String reason;

        private FieldReason(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldReason> of(final String field, final String value, final String reason) {
            List<FieldReason> fieldReasons = new ArrayList<>();
            fieldReasons.add(new FieldReason(field, value, reason));
            return fieldReasons;
        }
    }

}
