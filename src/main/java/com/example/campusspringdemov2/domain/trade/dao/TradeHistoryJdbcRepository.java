package com.example.campusspringdemov2.domain.trade.dao;

import com.example.campusspringdemov2.domain.trade.domain.TradeHistory;
import com.example.campusspringdemov2.domain.trade.domain.TradeHistoryDateRange;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TradeHistoryJdbcRepository extends JdbcDaoSupport implements TradeSearchRepository {

    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public TradeHistoryJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<TradeHistory> findByDate(TradeHistoryDateRange tradeHistory) {
        String sql = "" +
                "select cp.company_name as companyName, sh.trade_date as tradeDate,\n" +
                "       if(sh.trade_price is null, (select sh.comp_prev_trade_price), sh.trade_price) as closingPrice,\n" +
                "       if(sh.trade_price is null, false, true) as hasClosingPrice\n" +
                "from company as cp left join stocks_history sh on cp.company_code = sh.company_code\n" +
                "where trade_date between (?) and (?) order by trade_date desc;";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TradeHistory> historyList = new ArrayList<>();

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, tradeHistory.getStartDate().toString());
            pstmt.setString(2, tradeHistory.getEndDate().toString());
            pstmt.executeQuery();

            rs = pstmt.getResultSet();
            while (rs.next()) {
                TradeHistory th = new TradeHistory();
                th.setCompanyName(rs.getString("companyName"));
                th.setTradeDate(rs.getString("tradeDate"));
                th.setClosingPrice(rs.getLong("closingPrice"));
                historyList.add(th);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return historyList;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            close(conn);
        }
    }

    private void close(Connection conn) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

}
