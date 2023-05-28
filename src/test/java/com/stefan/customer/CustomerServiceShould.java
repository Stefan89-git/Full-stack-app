package com.stefan.customer;

import com.github.javafaker.Faker;
import com.stefan.AbstractTestcontainers;
import com.stefan.exception.DuplicateResourceException;
import com.stefan.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceShould extends AbstractTestcontainers {
    private static final Faker FAKER = new Faker();

    @Mock
    private CustomerDao customerDao;
    @Mock
    private CustomerMapper customerMapper;

    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao, customerMapper);
    }

    @Test
    void getAllCustomers() {
        Customer customer1 = createRandomCustomer();
        Customer customer2 = createRandomCustomer();
        List<Customer> customers = List.of(customer1, customer2);
        when(customerDao.getAllCustomers()).thenReturn(customers);
        customers.forEach(customer -> {
            when(customerMapper.toDto(customer)).thenReturn(toDto(customer));
        });
        List<CustomerDto> allCustomers = underTest.getAllCustomers();
        List<CustomerDto> customerDtos = customers.stream()
                .map(this::toDto)
                .toList();

        assertTrue(allCustomers.containsAll(customerDtos));
        assertEquals(2, allCustomers.size());
    }

    @Test
    void getCustomer() {
        Customer customer = createRandomCustomer();
        when(customerDao.getCustomer(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(toDto(customer));

        CustomerDto actual = underTest.getCustomer(1);
        assertEquals(toDto(customer), actual);
    }

    @Test
    void throwErrorIfCustomerNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> underTest.getCustomer(-1));
    }

    @Test
    void createCustomer() {
        CustomerDto customerDto = toDto(createRandomCustomer());
        Customer customer = createRandomCustomer();
        when(customerMapper.toEntity(customerDto)).thenReturn(customer);
        when(customerDao.create(customer)).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(customerDto);

        CustomerDto actual = underTest.createCustomer(customerDto);
        assertEquals(customerDto, actual);

        when(customerDao.existsByEmail(customerDto.getEmail())).thenReturn(true);
        assertThrows(DuplicateResourceException.class, () -> underTest.createCustomer(customerDto));
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto = toDto(createRandomCustomer());
        Customer customer = createRandomCustomer();
        when(customerDao.getCustomer(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(toDto(customer));

        CustomerDto dto = underTest.getCustomer(1);

        when(customerMapper.toEntity(dto)).thenReturn(toEntity(dto));
        when(customerDao.update(toEntity(dto))).thenReturn(toEntity(dto));
        when(customerMapper.toDto(toEntity(dto))).thenReturn(dto);

        CustomerDto updated = underTest.updateCustomer(1, customerDto);
        assertEquals(customerDto, updated);
    }

    @Test
    void deleteCustomer() {
        Customer customer = createRandomCustomer();
        customer.setId(1);
        when(customerDao.getCustomer(1)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(toDto(customer));

        underTest.deleteCustomer(1);
        verify(customerDao, times(1)).delete(1);
    }

    private Customer createRandomCustomer() {
        return Customer.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "_" + UUID.randomUUID())
                .age(FAKER.number().numberBetween(15, 85))
                .build();
    }

    private CustomerDto toDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .age(customer.getAge())
                .build();
    }

    private Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .age(customerDto.getAge())
                .build();
    }
}