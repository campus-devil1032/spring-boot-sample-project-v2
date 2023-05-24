package com.example.campusspringdemov2.infra.bucket4j.dto;

import com.example.campusspringdemov2.global.response.normal.Response4xx;
import com.example.campusspringdemov2.global.response.normal.ResponseCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
public class Bucket4jResponse {

    public ResponseEntity<?> response4xx() {
        Response4xx resp = Response4xx.of(ResponseCode.BUCKET4J_APIKEY_NULL);
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
