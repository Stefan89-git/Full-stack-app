package com.stefan.integrationTests;

import com.github.javafaker.Faker;
import com.stefan.customer.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static com.stefan.AssertUtil.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CustomerIntegrationTest {
    private static final String[] EXCLUDED_FIELDS = {"id"};
    private static final Faker FAKER = new Faker();

    @Autowired
    private CustomerWebClient customerWebClient;

    @Test
    void happyFlow() {
        CustomerDto customerDto = createRandomCustomerDto();
        CustomerDto created = customerWebClient.create(customerDto);

        List<CustomerDto> allCustomers = customerWebClient.getAll();
        assertTrue(allCustomers.contains(created));

        CustomerDto getOne = customerWebClient.getOne(created.getId());
        assertEquals(getOne, created, EXCLUDED_FIELDS);

        CustomerDto customerForUpdate = createRandomCustomerDto();
        CustomerDto updated = customerWebClient.update(created.getId(), customerForUpdate);
        assertEquals(updated, customerForUpdate, EXCLUDED_FIELDS);

        customerWebClient.delete(updated.getId());
        allCustomers = customerWebClient.getAll();
        assertFalse(allCustomers.contains(updated));
    }

    private CustomerDto createRandomCustomerDto() {
        return CustomerDto.builder()
                .name(FAKER.name().fullName())
                .email(FAKER.internet().safeEmailAddress() + "_" + UUID.randomUUID())
                .age(FAKER.number().numberBetween(15, 85))
                .build();
    }
}
