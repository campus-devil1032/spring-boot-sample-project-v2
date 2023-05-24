package com.example.campusspringdemov2.domain.trade.dao;


import com.example.campusspringdemov2.domain.trade.domain.TradeHistory;
import com.example.campusspringdemov2.domain.trade.domain.TradeHistoryDateRange;

import java.util.List;

public interface TradeSearchRepository {

    List<TradeHistory> findByDate(TradeHistoryDateRange tradeHistory);
}
