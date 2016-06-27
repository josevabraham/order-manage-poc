package com.order.orm.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.order.orm.entity.Address;

public class AddressMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setAddressId(rs.getLong("ADDRESS_ID"));
        address.setFirstName(rs.getString("FIRST_NAME"));
        address.setLastName(rs.getString("LAST_NAME"));
        address.setStrretAddress1(rs.getString("STREET_ADDRESS_1"));
        address.setStrretAddress2(rs.getNString("STREET_ADDRESS_2"));
        address.setCity(rs.getString("CITY"));
        address.setState(rs.getString("STATE"));
        address.setZipCode(rs.getString("ZIP"));

        return address;
    }

}
