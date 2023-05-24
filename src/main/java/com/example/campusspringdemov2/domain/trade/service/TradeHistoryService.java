package com.example.campusspringdemov2.domain.trade.service;

import com.example.campusspringdemov2.domain.trade.dao.TradeHistoryJdbcRepository;
import com.example.campusspringdemov2.domain.trade.domain.TradeHistory;
import com.example.campusspringdemov2.domain.trade.domain.TradeHistoryDateRange;
import com.example.campusspringdemov2.domain.trade.exception.DateRangeException;
import com.example.campusspringdemov2.domain.trade.exception.DateRangeTooLongException;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TradeHistoryService {
    private final TradeHistoryJdbcRepository tradeHistoryJdbcRepository;

    private TradeHistoryService(TradeHistoryJdbcRepository tradeHistoryJdbcRepository) {
        this.tradeHistoryJdbcRepository = tradeHistoryJdbcRepository;
    }

    public List<TradeHistory> getTradeHistoryFindDao(TradeHistoryDateRange tradeHistory) {
        validateDateRange(tradeHistory);
        return tradeHistoryJdbcRepository.findByDate(tradeHistory);
    }

    private void validateDateRange(TradeHistoryDateRange tradeHistory) {
        /* 시작날짜와 종료날짜를 비교해서 0포함 양수가 나와야 올바른 범위이다. */
        long days = ChronoUnit.DAYS.between(tradeHistory.getStartDate(), tradeHistory.getEndDate());
        if (days < 0) {
            /* 지정 날짜 범위가 올바르지 않은경우 예외처리 */
            throw new DateRangeException();
        } else if (days > 365) {
            throw new DateRangeTooLongException();
        }
    }


}
