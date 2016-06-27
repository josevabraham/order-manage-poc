package com.order.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.order.orm.entity.OnlineOrderLine;

public class OnlineOrderLineMapper implements RowMapper<OnlineOrderLine> {
    
   enum RsMap{
       ONLINE_ORDER_LINE_ID,
       ONLINE_ORDER_ID,
       PRODUCT_ID,
       PRODUCT_NAME,
       QUANTITY,
       LINE_STATUS
   }


    @Override
    public OnlineOrderLine mapRow(ResultSet rs, int rowNum) throws SQLException {
      
        OnlineOrderLine orderLine = new OnlineOrderLine();
        
        orderLine.setOrderLineId(rs.getLong(RsMap.ONLINE_ORDER_LINE_ID.name()));
        orderLine.setOrderId(rs.getLong(RsMap.ONLINE_ORDER_ID.name()));
        orderLine.setProductId(rs.getLong(RsMap.PRODUCT_ID.name()));
        orderLine.setProductName(rs.getString(RsMap.PRODUCT_NAME.name()));
        orderLine.setQuantity(rs.getInt(RsMap.QUANTITY.name()));
        orderLine.setLineStatus(rs.getString(RsMap.LINE_STATUS.name()));
        return orderLine;
    }

}
