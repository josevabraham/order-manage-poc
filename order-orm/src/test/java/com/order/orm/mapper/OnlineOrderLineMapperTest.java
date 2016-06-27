package com.order.orm.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.order.orm.entity.OnlineOrderLine;
import com.order.orm.mapper.OnlineOrderLineMapper.RsMap;



public class OnlineOrderLineMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        OnlineOrderLineMapper testMapper = new OnlineOrderLineMapper();
        ResultSet mockRs =  mock(ResultSet.class);
        
        when(mockRs.getLong(RsMap.ONLINE_ORDER_LINE_ID.name())).thenReturn(1L);
        when(mockRs.getLong(RsMap.ONLINE_ORDER_ID.name())).thenReturn(201L);
        when(mockRs.getLong(RsMap.PRODUCT_ID.name())).thenReturn(1234L);
        when(mockRs.getString(RsMap.PRODUCT_NAME.name())).thenReturn("Test Item");
        when(mockRs.getInt(RsMap.QUANTITY.name())).thenReturn(2);
        when(mockRs.getString(RsMap.LINE_STATUS.name())).thenReturn("New");
        OnlineOrderLine testOnlineOrderLine = testMapper.mapRow(mockRs, 1);
        
        assertEquals(new Long(1), testOnlineOrderLine.getOrderLineId());
        assertEquals(new Long(201), testOnlineOrderLine.getOrderId());
        assertEquals(new Long(1234), testOnlineOrderLine.getProductId());
        assertEquals("Test Item", testOnlineOrderLine.getProductName());
        assertEquals(2, testOnlineOrderLine.getQuantity());
        assertEquals("New", testOnlineOrderLine.getLineStatus());
                
    }
    
    

}
