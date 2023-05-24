package com.example.campusspringdemov2.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CustomProperties {

    @Value("${api-key.header}")
    private String headerName;

    @Value("${api-key.secret}")
    private String apiKeySecret;

    @Value("${api.version.v1}")
    private String apiVersionV1;

    @Value("${api.version.v2}")
    private String apiVersionV2;

    @Value("${api.version.v3}")
    private String apiVersionV3;

    @Value("${api.version.v4}")
    private String apiVersionV4;

    @Value("${api.version.v5}")
    private String apiVersionV5;

}
