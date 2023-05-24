package com.example.campusspringdemov2.domain.trade.api;

import com.example.campusspringdemov2.domain.trade.domain.TradeHistoryDateRange;
import com.example.campusspringdemov2.domain.trade.dto.TradeHistoryResponse;
import com.example.campusspringdemov2.domain.trade.service.TradeHistoryService;
import com.example.campusspringdemov2.global.config.CustomProperties;
import com.example.campusspringdemov2.global.response.exception.business.InvalidApiKeyException;
import com.example.campusspringdemov2.global.response.exception.business.NullApiKeyException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/${api.version.v1}/trade")
@RequiredArgsConstructor
@Validated
public class TradeApi {

    @Autowired
    CustomProperties customProperties;

    @Autowired
    private TradeHistoryService TradeHistoryService;

    @GetMapping(value = "/date")
    @ResponseBody
    public ResponseEntity<?> getTradeDataByDate(
            @RequestHeader(name = "apiKey", required = true) String apiKey,
            @RequestParam(value = "start_date", required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date", required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate endDate
    ) {

        if (StringUtils.isEmpty(apiKey)) throw new NullApiKeyException(); /* 'apiKey' 헤더 누락 됐을 경우 예외처리 */
        else if (!customProperties.getApiKeySecret().equals(apiKey))
            throw new InvalidApiKeyException(); /* 'apiKey' 안맞을 경우 예외처리 */
        return new TradeHistoryResponse(TradeHistoryService.getTradeHistoryFindDao(TradeHistoryDateRange.of(startDate, endDate))).response();

    }
}
