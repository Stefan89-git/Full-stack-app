package com.stefan.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.stefan.AssertUtil.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerMapperShould {

    private CustomerMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerMapper();
    }

    @Test
    void toEntity() {
        CustomerDto dto = CustomerDto.builder()
                .id(1)
                .name("John Doe")
                .email("john@example.com")
                .age(25)
                .build();

        Customer customer = underTest.toEntity(dto);

        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getName(), dto.getName());
        assertEquals(customer.getEmail(), dto.getEmail());
        assertEquals(customer.getAge(), dto.getAge());
    }

    @Test
    void toDto() {
        Customer customer = Customer.builder()
                .id(1)
                .name("John Doe")
                .email("john@example.com")
                .age(25)
                .build();

        CustomerDto dto = underTest.toDto(customer);

        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getName(), dto.getName());
        assertEquals(customer.getEmail(), dto.getEmail());
        assertEquals(customer.getAge(), dto.getAge());
    }
}