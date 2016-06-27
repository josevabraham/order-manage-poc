package com.order.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.order.orm.entity.OnlineOrder;

public class OnlineOrderMapper implements RowMapper<OnlineOrder> {

    public static OnlineOrderMapper newInstance(){
      return  new OnlineOrderMapper();
    }
    
    @Override
    public OnlineOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        OnlineOrder order = new OnlineOrder();
        order.setOnlineOrderId(rs.getLong("ONLINE_ORDER_ID"));
        order.setOrderPlacedDate(rs.getDate("ORDER_PLACED_DATE"));
        order.setCustomerName(rs.getNString("CUSTOMER_NAME"));
        order.setSellingPrice(rs.getDouble("SELLING_PRICE"));
        order.setOrderNumber(rs.getString("ORDER_NUMBER"));
        if (isNotNull(rs.getObject("SHIPPING_ADDRESS_ID")))
            order.setShippingAddressId(rs.getLong("SHIPPING_ADDRESS_ID"));
        if (isNotNull(rs.getObject("SALES_TAX")))
            order.setSalesTax(rs.getDouble("SALES_TAX"));
        if (isNotNull(rs.getObject("SHIPPING_PRICE")))
            order.setShippingPrice(rs.getDouble("SHIPPING_PRICE"));
        order.setOrderStatus(rs.getString("ORDER_STATUS"));
        return order;
    }

    private static boolean isNotNull(Object obj) {
        if (null != obj)
            return true;
        return false;

    }

}
