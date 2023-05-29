package com.stefan.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRawMapperShould {

    @Test
    void mapRow() throws SQLException {
        CustomerRawMapper customerRawMapper = new CustomerRawMapper();
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(20);
        when(resultSet.getString("name")).thenReturn("Pera");
        when(resultSet.getString("email")).thenReturn("pera@gmail.com");

        Customer actual = customerRawMapper.mapRow(resultSet, 1);
        Customer expected = Customer.builder()
                .id(1)
                .name("Pera")
                .email("pera@gmail.com")
                .age(20)
                .build();

        assertEquals(actual, expected);
    }
}