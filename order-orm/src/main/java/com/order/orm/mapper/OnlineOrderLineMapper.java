package com.order.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.order.orm.entity.OnlineOrderLine;

public class OnlineOrderLineMapper implements RowMapper<OnlineOrderLine> {
    
   


    @Override
    public OnlineOrderLine mapRow(ResultSet rs, int rowNum) throws SQLException {
      
        OnlineOrderLine orderLine = new OnlineOrderLine();
        
        orderLine.setOrderLineId(rs.getLong("ONLINE_ORDER_LINE_ID"));
        orderLine.setOrderId(rs.getLong("ONLINE_ORDER_ID"));
        orderLine.setProductId(rs.getLong("PRODUCT_ID"));
        orderLine.setProductName(rs.getNString("PRODUCT_NAME"));
        orderLine.setQuantity(rs.getInt("QUANTITY"));
        orderLine.setLineStatus(rs.getString("LINE_STATUS"));
        return orderLine;
    }

}
