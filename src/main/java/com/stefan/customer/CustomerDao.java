package com.stefan.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomer(Integer id);

    Customer create(Customer customer);

    Customer update(Customer customer);

    boolean existsByEmail(String email);

    void delete(Integer id);
}
