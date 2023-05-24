package com.example.campusspringdemov2.domain.trade.dto;

import com.example.campusspringdemov2.domain.trade.domain.TradeHistory;
import com.example.campusspringdemov2.global.response.normal.Response2xx;
import com.example.campusspringdemov2.global.response.normal.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class TradeHistoryResponse {
    List<TradeHistory> tradeHistoryList;

    public TradeHistoryResponse(final List<TradeHistory> tradeHistoryList) {
        this.tradeHistoryList = tradeHistoryList;
    }

    public ResponseEntity<?> response() {
        Response2xx resp = Response2xx.of(ResponseCode.RESPONSE_OK, this.tradeHistoryList);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}