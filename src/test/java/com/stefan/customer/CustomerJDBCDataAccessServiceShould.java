package com.stefan.customer;

import com.stefan.AbstractTestcontainers;

import com.stefan.AssertUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerJDBCDataAccessServiceShould extends AbstractTestcontainers {
    private static final String[] EXCLUDED_FIELDS = {"id"};

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRawMapper customerMapper = new CustomerRawMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerMapper
        );
    }

    @Test
    void getAllCustomers() {
        Customer customer = createRandomCustomer();
        customer = underTest.create(customer);

        List<Customer> customers = underTest.getAllCustomers();
        assertTrue(customers.contains(customer));
    }

    @Test
    void getCustomer() {
        Customer customer = createRandomCustomer();
        Customer created = underTest.create(customer);

        AssertUtil.assertEquals(customer, created, EXCLUDED_FIELDS);
        assertTrue(underTest.getCustomer(-1).isEmpty());
    }

    @Test
    void create() {
        Customer actual = createRandomCustomer();
        Customer created = underTest.create(actual);

        AssertUtil.assertEquals(actual, created, EXCLUDED_FIELDS);
    }

    @Test
    void update() {
        Customer customer = createRandomCustomer();
        Customer created = underTest.create(customer);
        assertEquals(customer.getName(), created.getName());

        created.setName(FAKER.name().name());
        Customer updated = underTest.update(created);

        assertEquals(created.getId(), updated.getId());
        assertNotEquals(customer.getName(), updated.getName());
    }

    @Test
    void existsByEmail() {
        Customer customer = createRandomCustomer();
        customer = underTest.create(customer);

        boolean exists = underTest.existsByEmail(customer.getEmail());
        String fakeEmail = FAKER.internet().emailAddress();

        assertTrue(exists);
        assertFalse(underTest.existsByEmail(fakeEmail));
    }

    @Test
    void delete() {
        Customer customer = createRandomCustomer();
        customer = underTest.create(customer);
        assertTrue(underTest.getAllCustomers().contains(customer));
        List<Customer> allCustomers = underTest.getAllCustomers();

        underTest.delete(customer.getId());
        assertFalse(underTest.getAllCustomers().contains(customer));
    }

    private Customer createRandomCustomer() {
        return Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() +  "_" + UUID.randomUUID())
                .age(FAKER.number().numberBetween(15, 85))
                .build();
    }
}