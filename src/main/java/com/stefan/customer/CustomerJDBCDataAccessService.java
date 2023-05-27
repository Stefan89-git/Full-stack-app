package com.stefan.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRawMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRawMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                LIMIT 1000
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> getCustomer(Integer id) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Customer create(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        return getAllCustomers().stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Customer update(Customer customer) {
        if (customer.getName() != null) {
            String sql = "UPDATE customer SET name = ? WHERE id = ?";
            jdbcTemplate.update(
                    sql,
                    customer.getName(),
                    customer.getId()
            );
        }
        if (customer.getAge() != null) {
            String sql = "UPDATE customer SET age = ? WHERE id = ?";
            jdbcTemplate.update(
                    sql,
                    customer.getAge(),
                    customer.getId()
            );
        }
        if (customer.getEmail() != null) {
            String sql = "UPDATE customer SET email = ? WHERE id = ?";
            jdbcTemplate.update(
                    sql,
                    customer.getEmail(),
                    customer.getId());
        }
        return customer;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public void delete(Integer customerId) {
        String sql = """
                DELETE
                FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, customerId);
    }

}
