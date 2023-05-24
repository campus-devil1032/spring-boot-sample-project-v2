package com.example.campusspringdemov2.global.response.exception.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 일관성 있는 Error Response를 위해 dto 개념으로 사용되는 클래스
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseError {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private String timestamp;
    private String message;
    private int status;
    private String code;
    private List<FieldError> errors;


    private ResponseError(final CodeError code, final List<FieldError> errors) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
        this.code = code.getCode();
    }

    private ResponseError(final CodeError code) {
        this.timestamp = String.valueOf(LocalDateTime.now());
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }


    public static ResponseError of(final CodeError code, final BindingResult bindingResult) {
        return new ResponseError(code, FieldError.of(bindingResult));
    }

    public static ResponseError of(final CodeError code) {
        return new ResponseError(code);
    }

    public static ResponseError of(final CodeError code, final List<FieldError> errors) {
        return new ResponseError(code, errors);
    }

    public static ResponseError of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ResponseError(CodeError.INVALID_TYPE_VALUE, errors);
    }

    /* 에러 내역을 항상 List 형으로 반환함 */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
