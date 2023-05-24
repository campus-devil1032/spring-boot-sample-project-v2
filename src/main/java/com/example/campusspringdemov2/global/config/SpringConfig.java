package com.example.campusspringdemov2.global.config;

import com.example.campusspringdemov2.domain.trade.dao.TradeHistoryJdbcRepository;
import com.example.campusspringdemov2.domain.trade.dao.TradeSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public TradeSearchRepository tradeSearchRepository() {
        return new TradeHistoryJdbcRepository(dataSource);
    }

}
