package com.example.campusspringdemov2.domain.trade.service;

import com.example.campusspringdemov2.domain.trade.domain.TradeHistory;
import com.example.campusspringdemov2.domain.trade.domain.TradeHistoryDateRange;

import java.util.List;

public interface TradeHistoryService {
    List<TradeHistory> getTradeHistoryFindDao(TradeHistoryDateRange tradeHistory);
}
