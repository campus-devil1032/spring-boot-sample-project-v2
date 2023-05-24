package com.example.campusspringdemov2.global.response.exception;

import com.example.campusspringdemov2.global.response.exception.business.BusinessException;
import com.example.campusspringdemov2.global.response.exception.response.CodeError;
import com.example.campusspringdemov2.global.response.exception.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ResponseError response = ResponseError.of(CodeError.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ResponseError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        final ResponseError response = ResponseError.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ResponseError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        final ResponseError response = ResponseError.of(CodeError.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 잘못 된 API Resource에 접근 할 경우 발생 (HTTP Status 404)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ResponseError> handleNoHandlerFoundException(NoHandlerFoundException e) {
        final ResponseError response = ResponseError.of(CodeError.HANDLE_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Custom Exception
     * 비즈니스 관련 익셉션을 정의하면 이 곳에서 발생한다.
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseError> handleBusinessException(final BusinessException e) {
        final CodeError codeError = e.getErrorCode();
        final ResponseError response = ResponseError.of(codeError);
        return new ResponseEntity<>(response, HttpStatus.valueOf(codeError.getStatus()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ResponseError> handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        final ResponseError response = ResponseError.of(
                CodeError.HEADER_VALUE_NULL,
                ResponseError.FieldError.of("header", e.getHeaderName(), "API KEY를 입력해 주세요"));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 최상위 Exception
     * 지정되지 않은 예외는 이 곳에서 발생 (HTTP Status 500 Error)
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseError> handleException(Exception e) {
        final ResponseError response = ResponseError.of(CodeError.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ResponseError> handleNullpointerException(NullPointerException e) {
        final ResponseError response = ResponseError.of(CodeError.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
