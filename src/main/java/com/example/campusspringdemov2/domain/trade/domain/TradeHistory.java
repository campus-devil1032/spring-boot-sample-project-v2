package com.example.campusspringdemov2.domain.trade.domain;

public class TradeHistory {
    private String companyName;
    private String tradeDate;
    private long closingPrice;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public long getClosingPrice() {
        return closingPrice;
    }

    public void setClosingPrice(long closingPrice) {
        this.closingPrice = closingPrice;
    }
}
