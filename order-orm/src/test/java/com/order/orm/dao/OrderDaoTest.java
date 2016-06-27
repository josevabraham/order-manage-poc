package com.order.orm.dao;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.order.orm.entity.Address;
import com.order.orm.entity.OnlineOrder;
import com.order.orm.mapper.OnlineOrderLineMapper;
import com.order.orm.mapper.OnlineOrderMapper;

import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.*;

public class OrderDaoTest {
    
    private JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
    private TransactionTemplate mockTransactionTemplate = mock(TransactionTemplate.class);


    @Test
    public void testGetOrder() {
        OrderDao testOrderDao = new OrderDao(mockJdbcTemplate, mockTransactionTemplate);
        Long testOnlineOrderId = 1L;
        testOrderDao.getOrder(testOnlineOrderId);
        verify(mockJdbcTemplate,times(1)).queryForObject(eq(OrderDao.selectOrderByOrderId), any(Object[].class), any(OnlineOrderMapper.class));

        //fail("Not yet implemented");
    }
    
    @Test
    public void testGetOrderLines() {
        OrderDao testOrderDao = new OrderDao(mockJdbcTemplate, mockTransactionTemplate);
        Long testOnlineOrderId = 1L;
        testOrderDao.getOrderLines(testOnlineOrderId);
        verify(mockJdbcTemplate,times(1)).query(eq(OrderDao.selectOrderLinesByOrderId), any(Object[].class), any(OnlineOrderLineMapper.class));

        //fail("Not yet implemented");
    }
    
    @Test
    public void testGetAllOrders() {
        OrderDao testOrderDao = new OrderDao(mockJdbcTemplate, mockTransactionTemplate);
        testOrderDao.getAllOrders();
        verify(mockJdbcTemplate,times(1)).query(any(String.class), any(OnlineOrderMapper.class));

        //fail("Not yet implemented");
    }
    
    @Test
    public void testCreateOrder() {
        OrderDao testOrderDao = new OrderDao(mockJdbcTemplate, mockTransactionTemplate);
        OnlineOrder mockOnlineOrder = mock(OnlineOrder.class);
       // verify(mockTransactionTemplate,times(1)).execute(any(TransactionCallback.class));
        /*TransactionCallback<Long> mockTransactionCallback = mock(TransactionCallback.class);
       // when(mockTransactionTemplate.execute(mockTransactionCallback)).thenCallRealMethod();
        Address mockAddress = mock(Address.class);
        doReturn(mockAddress).when(mockOnlineOrder).getShippingAddress();*/
        testOrderDao.createOrder(mockOnlineOrder);
        //verify(mockJdbcTemplate,times(1)).update(any(PreparedStatementCreator.class));

       // doReturn(anyLong()).when(testOrderDao).insertAddress(mockAddress);
     //   verify(testOrderDao,only()).insertAddress(mockAddress);

        //fail("Not yet implemented");
    }

}
