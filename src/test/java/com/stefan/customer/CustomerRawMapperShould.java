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
        when(resultSet.getInt("age")).thenReturn(25);
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getString("email")).thenReturn("john@example.com");

        Customer actual = customerRawMapper.mapRow(resultSet, 1);
        Customer expected = Customer.builder()
                .id(1)
                .name("John Doe")
                .email("john@example.com")
                .age(25)
                .build();

        assertEquals(actual, expected);
    }
}
