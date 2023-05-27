package com.stefan.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer dan = new Customer(
                1,
                "Dan",
                "dan@gmail.com",
                30
        );
        customers.add(dan);

        Customer eli = new Customer(
                2,
                "Eli",
                "eli@gmail.com",
                27
        );
        customers.add(eli);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getCustomer(Integer id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public Customer create(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        customers.add(customer);
        return customer;
    }

    @Override
    public boolean existsByEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public void delete(Integer id) {
        customers.stream()
                .filter(c -> !Objects.equals(c.getId(), id))
                .findFirst()
                .ifPresent(customers::remove);
    }
}
