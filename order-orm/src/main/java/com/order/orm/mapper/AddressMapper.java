package com.order.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.order.orm.entity.Address;

public class AddressMapper implements RowMapper<Address> {

     enum RsMap{
         ADDRESS_ID,
         FIRST_NAME,
         LAST_NAME,
         STREET_ADDRESS_1,
         STREET_ADDRESS_2,
         CITY,
         STATE,
         ZIP
         
     }
    
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setAddressId(rs.getLong(RsMap.ADDRESS_ID.name()));
        address.setFirstName(rs.getString(RsMap.FIRST_NAME.name()));
        address.setLastName(rs.getString(RsMap.LAST_NAME.name()));
        address.setStrretAddress1(rs.getString(RsMap.STREET_ADDRESS_1.name()));
        address.setStrretAddress2(rs.getString(RsMap.STREET_ADDRESS_2.name()));
        address.setCity(rs.getString(RsMap.CITY.name()));
        address.setState(rs.getString(RsMap.STATE.name()));
        address.setZipCode(rs.getString(RsMap.ZIP.name()));

        return address;
    }

}
