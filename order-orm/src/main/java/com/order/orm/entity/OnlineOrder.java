package com.order.orm.entity;

import java.util.Date;
import java.util.List;

public class OnlineOrder {

    private Long                  onlineOrderId;
    private String                orderNumber;
    private Date                  orderPlacedDate;
    private Double                sellingPrice;
    private Double                salesTax;
    private Double                shippingPrice;
    private String                orderStatus;
    private Date                  expectedShipDate;
    private List<OnlineOrderLine> orderLines;
    private String                customerName;
    private String                shippingMethod;
    private Long                  shippingAddressId;
    private Address               shippingAddress;

    public Long getOnlineOrderId() {
        return onlineOrderId;
    }

    public void setOnlineOrderId(Long onlineOrderId) {
        this.onlineOrderId = onlineOrderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(Date orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Double salesTax) {
        this.salesTax = salesTax;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getExpectedShipDate() {
        return expectedShipDate;
    }

    public void setExpectedShipDate(Date expectedShipDate) {
        this.expectedShipDate = expectedShipDate;
    }

    public List<OnlineOrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OnlineOrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
