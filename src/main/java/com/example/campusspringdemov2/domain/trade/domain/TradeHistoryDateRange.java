package com.example.campusspringdemov2.domain.trade.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradeHistoryDateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    private TradeHistoryDateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static TradeHistoryDateRange of(final LocalDate startDate, final LocalDate endDate) {
        return new TradeHistoryDateRange(startDate, endDate);
    }
}
