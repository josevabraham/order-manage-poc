package com.order.orm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.google.common.collect.Lists;
import com.order.orm.entity.Address;
import com.order.orm.entity.OnlineOrder;
import com.order.orm.entity.OnlineOrderLine;
import com.order.orm.mapper.AddressMapper;
import com.order.orm.mapper.OnlineOrderLineMapper;
import com.order.orm.mapper.OnlineOrderMapper;

@Component
public class OrderDao {

    private static final Logger logger                    = LoggerFactory.getLogger(OrderDao.class);
    private JdbcTemplate        jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public static final String  selectOrderByOrderId      = "SELECT ONLINE_ORDER_ID ,ORDER_NUMBER ,ORDER_PLACED_DATE ,CUSTOMER_NAME ,SHIPPING_ADDRESS_ID ,SELLING_PRICE ,SALES_TAX ,SHIPPING_PRICE ,ORDER_STATUS ,MODIFIED_DTM ,CREATED_DTM ,MODIFIER_ID FROM ONLINE_ORDER WHERE ONLINE_ORDER_ID = ?";
    public static final String  selectOrderLinesByOrderId = "SELECT ONLINE_ORDER_LINE_ID ,ONLINE_ORDER_ID ,PRODUCT_ID ,PRODUCT_NAME ,QUANTITY ,LINE_STATUS FROM ONLINE_ORDER_LINE WHERE ONLINE_ORDER_ID = ?";
    public static final String  selectAddressByAddressId  = "SELECT ADDRESS_ID ,FIRST_NAME ,LAST_NAME ,STREET_ADDRESS_1,STREET_ADDRESS_2 ,CITY,STATE,ZIP,MODIFIED_DTM,MODIFIER_ID FROM ADDRESS WHERE ADDRESS_ID = ?";
    public static final String  selectAllOrders           = "SELECT ONLINE_ORDER_ID ,ORDER_NUMBER ,ORDER_PLACED_DATE ,CUSTOMER_NAME ,SHIPPING_ADDRESS_ID ,SELLING_PRICE ,SALES_TAX ,SHIPPING_PRICE ,ORDER_STATUS ,MODIFIED_DTM ,CREATED_DTM ,MODIFIER_ID FROM ONLINE_ORDER";
    public static final String  insertOnlineOrder         = "INSERT INTO ONLINE_ORDER " + "            (ORDER_NUMBER, " + "             ORDER_PLACED_DATE, " + "             CUSTOMER_NAME, "
            + "             SHIPPING_ADDRESS_ID, " + "             SELLING_PRICE, " + "             SALES_TAX, " + "             SHIPPING_PRICE, " + "             ORDER_STATUS, "
            + "             MODIFIED_DTM, " + "             MODIFIER_ID) " + "VALUES      (?, " + "             ?, " + "             ?, " + "             ?, " + "             ?, " + "             ?, "
            + "             ?, " + "             ?, " + "             ?, " + "             ?)";
    public static final String  insertOnlineOrderLine     = "INSERT INTO ONLINE_ORDER_LINE(ONLINE_ORDER_ID,PRODUCT_ID ,PRODUCT_NAME ,QUANTITY ,LINE_STATUS ,MODIFIED_DTM,MODIFIER_ID) values(?,?,?,?,?,?,?)";
    public static final String  insertAddress             = "INSERT INTO ADDRESS(FIRST_NAME ,LAST_NAME ,STREET_ADDRESS_1,STREET_ADDRESS_2 ,CITY,STATE,ZIP,MODIFIED_DTM,MODIFIER_ID) values (?,?,?,?,?,?,?,?,?)";

    @Autowired
    public OrderDao(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    public OnlineOrder getOrder(Long onlineOrderId) {
        try {
            OnlineOrder order = jdbcTemplate.queryForObject(selectOrderByOrderId, new Object[] { onlineOrderId }, OnlineOrderMapper.newInstance());
            return order;
        } catch (EmptyResultDataAccessException e) {
            logger.error("No OnlineOrder results for onlineOrderId : {}", onlineOrderId);
            return null;
        } catch (Exception e) {
            logger.error("Error fetching the Lines for onlineOrderId : {} ", onlineOrderId, e);
        }
        return null;
    }

    public List<OnlineOrderLine> getOrderLines(Long onlineOrderId) {
        try {
            List<OnlineOrderLine> orderLines = jdbcTemplate.query(selectOrderLinesByOrderId, new Object[] { onlineOrderId }, new OnlineOrderLineMapper());
            return orderLines;
        } catch (Exception e) {
            logger.error("Error fetching the Lines for order_id : {} ", onlineOrderId, e);
        }
        return Lists.newArrayList();
    }

    public Address getAddress(Long addressId) {
        try {
            Address address = jdbcTemplate.queryForObject(selectAddressByAddressId, new Object[] { addressId }, new AddressMapper());
            return address;
        } catch (EmptyResultDataAccessException e) {
            logger.error("No Address results for addressId : {}", addressId);
            return null;
        } catch (Exception e) {
            logger.error("Error fetching the address for addressId : {} ", addressId, e);
        }
        return null;
    }

    public List<OnlineOrder> getAllOrders() {
        try {
            List<OnlineOrder> orders = jdbcTemplate.query(selectAllOrders, new OnlineOrderMapper());
            return orders;
        } catch (Exception e) {
            logger.error("Error fetching all orders ", e);
        }
        return Lists.newArrayList();

    }

    public Long createOrder(OnlineOrder onlineOrder) {
        return transactionTemplate.execute(new TransactionCallback<Long>() {

            @Override
            public Long doInTransaction(TransactionStatus status) {
                try {
                    logger.info("Inserting order to DB");
                    if (null != onlineOrder.getShippingAddress()) {
                        Long addressId = insertAddress(onlineOrder.getShippingAddress());
                        onlineOrder.setShippingAddressId(addressId);
                    }
                    Long onlineOrderId = insertOnlineOrder(onlineOrder);
                    for (OnlineOrderLine onlineOrderLine : onlineOrder.getOrderLines()) {
                        onlineOrderLine.setOrderId(onlineOrderId);
                        insertOnlineOrderLine(onlineOrderLine);

                    }
                    return onlineOrderId;
                } catch (Exception e) {
                    logger.error("Insert order failed :", e);
                    status.setRollbackOnly();
                    throw e;
                }
            }
        });
    }

    public Long insertOnlineOrder(final OnlineOrder onlineOrder) {
        KeyHolder holder = new GeneratedKeyHolder();
        int row = jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertOnlineOrder, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, onlineOrder.getOrderNumber());
                ps.setDate(2, new Date(onlineOrder.getOrderPlacedDate().getTime()));
                ps.setString(3, onlineOrder.getCustomerName());
                ps.setLong(4, onlineOrder.getShippingAddressId());
                ps.setDouble(5, onlineOrder.getSellingPrice());
                ps.setDouble(6, onlineOrder.getSalesTax());
                ps.setDouble(7, onlineOrder.getShippingPrice());
                ps.setString(8, onlineOrder.getOrderStatus());
                ps.setDate(9, new Date(new java.util.Date().getTime()));
                ps.setLong(10, 0L);
                return ps;
            }

        }, holder);

        if (row > 0) {
            return holder.getKey().longValue();
        }
        return null;
    }

    public Long insertOnlineOrderLine(final OnlineOrderLine onlineOrderLine) {
        KeyHolder holder = new GeneratedKeyHolder();
        int row = jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertOnlineOrderLine, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, onlineOrderLine.getOrderId());
                ps.setLong(2, onlineOrderLine.getProductId());
                ps.setString(3, onlineOrderLine.getProductName());
                ps.setInt(4, onlineOrderLine.getQuantity());
                ps.setString(5, onlineOrderLine.getLineStatus());
                ps.setDate(6, new Date(new java.util.Date().getTime()));
                ps.setLong(7, 0L);
                return ps;
            }

        }, holder);

        if (row > 0) {
            return holder.getKey().longValue();
        }
        return null;
    }

    public Long insertAddress(final Address address) {
        KeyHolder holder = new GeneratedKeyHolder();
        int row = jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertAddress, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, address.getFirstName());
                ps.setString(2, address.getLastName());
                ps.setString(3, address.getStrretAddress1());
                ps.setString(4, address.getStrretAddress2());
                ps.setString(5, address.getCity());
                ps.setString(6, address.getState());
                ps.setString(7, address.getZipCode());
                ps.setDate(8, new Date(new java.util.Date().getTime()));
                ps.setLong(9, 0L);
                return ps;
            }

        }, holder);

        if (row > 0) {
            return holder.getKey().longValue();
        }
        return null;
    }

}
