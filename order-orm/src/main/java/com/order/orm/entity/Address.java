package com.order.orm.entity;

public class Address {

    private Long   addressId;
    private String firstName;
    private String lastName;
    private String strretAddress1;
    private String strretAddress2;
    private String city;
    private String state;
    private String zipCode;

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStrretAddress1() {
        return strretAddress1;
    }

    public void setStrretAddress1(String strretAddress1) {
        this.strretAddress1 = strretAddress1;
    }

    public String getStrretAddress2() {
        return strretAddress2;
    }

    public void setStrretAddress2(String strretAddress2) {
        this.strretAddress2 = strretAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
