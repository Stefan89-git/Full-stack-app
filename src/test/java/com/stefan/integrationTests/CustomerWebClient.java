package com.stefan.integrationTests;

import com.stefan.customer.CustomerDto;
import com.stefan.integrationTests.util.CustomWebTestClient;
import org.springframework.stereotype.Component;

@Component
public class CustomerWebClient extends AbstractWebClient<CustomerDto> {

    public CustomerWebClient(CustomWebTestClient customWebTestClient) {
        super(customWebTestClient, "customers", CustomerDto.class);
    }
}
