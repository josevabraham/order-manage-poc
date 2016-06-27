package com.order.orm.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.order.orm.entity.Address;
import com.order.orm.mapper.AddressMapper.RsMap;

public class AddressMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        AddressMapper testMapper = new AddressMapper();

        ResultSet mockRs = mock(ResultSet.class);
        when(mockRs.getLong(RsMap.ADDRESS_ID.name())).thenReturn(1L);
        when(mockRs.getString(RsMap.FIRST_NAME.name())).thenReturn("Test");
        when(mockRs.getString(RsMap.LAST_NAME.name())).thenReturn("User");
        when(mockRs.getString(RsMap.STREET_ADDRESS_1.name())).thenReturn("100 Test Rd");
        when(mockRs.getString(RsMap.STREET_ADDRESS_2.name())).thenReturn("Apt 10");
        when(mockRs.getString(RsMap.CITY.name())).thenReturn("Test City");
        when(mockRs.getString(RsMap.STATE.name())).thenReturn("CA");
        when(mockRs.getString(RsMap.ZIP.name())).thenReturn("90001");

        Address testAddress = testMapper.mapRow(mockRs, 1);
        assertEquals(new Long(1), testAddress.getAddressId());
        assertEquals("Test", testAddress.getFirstName());
        assertEquals("User", testAddress.getLastName());
        assertEquals("100 Test Rd", testAddress.getStrretAddress1());
        assertEquals("Apt 10", testAddress.getStrretAddress2());
        assertEquals("Test City", testAddress.getCity());
        assertEquals("CA", testAddress.getState());
        assertEquals("90001", testAddress.getZipCode());

    }

}
