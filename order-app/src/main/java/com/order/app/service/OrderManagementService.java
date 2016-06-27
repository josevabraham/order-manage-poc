package com.order.app.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.order.common.entity.Order;
import com.order.common.entity.OrderLine;
import com.order.common.entity.ShippingAddress;
import com.order.orm.dao.OrderDao;
import com.order.orm.entity.Address;
import com.order.orm.entity.OnlineOrder;
import com.order.orm.entity.OnlineOrderLine;

@Service
public class OrderManagementService {

    private static final Logger logger = LoggerFactory.getLogger(OrderManagementService.class);
    private OrderDao            orderDao;

    @Autowired
    public OrderManagementService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Long createOrder(Order order) {
        return orderDao.createOrder(getOnlineOrderFromOrderImport(order));
    }

    public Order getOrder(Long orderId) {
        OnlineOrder onlineOrder = orderDao.getOrder(orderId);
        if(null == onlineOrder)
            return null;
        
        List<OnlineOrderLine> onlineOrderLines = orderDao.getOrderLines(orderId);
        onlineOrder.setOrderLines(onlineOrderLines);
        if (null != onlineOrder.getShippingAddressId()) {
            Address address = orderDao.getAddress(onlineOrder.getShippingAddressId());
            onlineOrder.setShippingAddress(address);
        }
        Order orderForExport = getOrderFromOnlineOrder(onlineOrder);
        return orderForExport;

    }

    public List<Order> getAllOrder() {
        List<OnlineOrder> onlineOrders = orderDao.getAllOrders();
        List<Order> ordersForExport = new ArrayList<Order>();
        for (OnlineOrder onlineOrder : onlineOrders) {
            Order orderForExport = getOrderFromOnlineOrder(onlineOrder);
            if (null != orderForExport)
                ordersForExport.add(orderForExport);
        }
        return ordersForExport;

    }

    private static Order getOrderFromOnlineOrder(OnlineOrder onlineOrder) {
        if (null == onlineOrder)
            return null;
        Order exportOrder = new Order();
        exportOrder.setOrderId(onlineOrder.getOnlineOrderId());
        exportOrder.setOrderNumber(onlineOrder.getOrderNumber());
        exportOrder.setOrderPlacedDate(onlineOrder.getOrderPlacedDate());
        exportOrder.setCustomerName(onlineOrder.getCustomerName());
        exportOrder.setSellingPrice(onlineOrder.getSellingPrice());
        exportOrder.setSalesTax(onlineOrder.getSalesTax());
        exportOrder.setShippingPrice(onlineOrder.getShippingPrice());
        exportOrder.setExpectedShipDate(onlineOrder.getExpectedShipDate());
        exportOrder.setOrderStatus(onlineOrder.getOrderStatus());
        exportOrder.setShippingMethod(onlineOrder.getShippingMethod());
        if (!CollectionUtils.isEmpty(onlineOrder.getOrderLines())) {
            List<OrderLine> exportOrderLines = new ArrayList<OrderLine>();
            for (OnlineOrderLine onlineOrderLine : onlineOrder.getOrderLines()) {
                OrderLine exportOrderLine = getOrderLineFromOnlineOrderLine(onlineOrderLine);
                if (null != exportOrderLine)
                    exportOrderLines.add(exportOrderLine);
            }
            exportOrder.setOrderLines(exportOrderLines);
        }
        if (null != onlineOrder.getShippingAddress()) {
            exportOrder.setShippingAddress(getShippingAddressFromAddress(onlineOrder.getShippingAddress()));
        }
        return exportOrder;

    }

    private static ShippingAddress getShippingAddressFromAddress(Address address) {
        if (null == address)
            return null;
        ShippingAddress exportAddress = new ShippingAddress();
        exportAddress.setFirstName(address.getFirstName());
        exportAddress.setLastName(address.getLastName());
        exportAddress.setStrretAddress1(address.getStrretAddress1());
        exportAddress.setStrretAddress2(address.getStrretAddress2());
        exportAddress.setCity(address.getCity());
        exportAddress.setState(address.getState());
        exportAddress.setZipCode(address.getZipCode());
        return exportAddress;
    }

    private static OrderLine getOrderLineFromOnlineOrderLine(OnlineOrderLine orderLine) {
        if (null == orderLine) {
            return null;
        }
        OrderLine exportOrderLine = new OrderLine();
        exportOrderLine.setOrderLineId(orderLine.getOrderLineId());
        exportOrderLine.setProductId(orderLine.getProductId());
        exportOrderLine.setProductName(orderLine.getProductName());
        exportOrderLine.setQuantity(orderLine.getQuantity());
        return exportOrderLine;
    }

    private static OnlineOrder getOnlineOrderFromOrderImport(Order order) {
        if (null == order)
            return null;
        OnlineOrder onlineOrder = new OnlineOrder();
        logger.debug("OrderPlacedDate: " + order.getOrderPlacedDate());
        logger.debug("OrderNumber: " + order.getOrderNumber());

        onlineOrder.setOrderPlacedDate(order.getOrderPlacedDate());
        onlineOrder.setCustomerName(order.getCustomerName());
        onlineOrder.setSellingPrice(order.getSellingPrice());
        onlineOrder.setExpectedShipDate(order.getExpectedShipDate());
        onlineOrder.setOrderNumber(order.getOrderNumber());
        onlineOrder.setSalesTax(order.getSalesTax());
        onlineOrder.setOrderStatus(order.getOrderStatus());
        onlineOrder.setShippingPrice(order.getShippingPrice());
        onlineOrder.setShippingMethod(order.getShippingMethod());
        List<OnlineOrderLine> onlineOrderLines = new ArrayList<OnlineOrderLine>();
        if (!CollectionUtils.isEmpty(order.getOrderLines())) {
            for (OrderLine orderLine : order.getOrderLines()) {
                onlineOrderLines.add(getOnlineOrderLineFromOrderLine(orderLine));
            }

        }
        onlineOrder.setOrderLines(onlineOrderLines);
        onlineOrder.setShippingAddress(getAddressFromShippingAddress(order.getShippingAddress()));
        return onlineOrder;

    }

    private static OnlineOrderLine getOnlineOrderLineFromOrderLine(OrderLine orderLine) {
        if (null == orderLine)
            return null;
        OnlineOrderLine onlineOrderLine = new OnlineOrderLine();
        onlineOrderLine.setProductId(orderLine.getProductId());
        onlineOrderLine.setProductName(orderLine.getProductName());
        onlineOrderLine.setQuantity(orderLine.getQuantity());
        onlineOrderLine.setLineStatus("New");
        return onlineOrderLine;

    }

    private static Address getAddressFromShippingAddress(ShippingAddress shippingAddress) {
        if (null == shippingAddress)
            return null;
        Address address = new Address();
        address.setFirstName(shippingAddress.getFirstName());
        address.setLastName(shippingAddress.getLastName());
        address.setStrretAddress1(shippingAddress.getStrretAddress1());
        address.setStrretAddress2(shippingAddress.getStrretAddress2());
        address.setCity(shippingAddress.getCity());
        address.setState(shippingAddress.getState());
        address.setZipCode(shippingAddress.getZipCode());
        return address;

    }
}
