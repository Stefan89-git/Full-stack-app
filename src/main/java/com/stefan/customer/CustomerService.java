package com.stefan.customer;

import com.stefan.exception.DuplicateResourceException;
import com.stefan.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stefan.util.CustomValidation.setIfNotNull;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao, CustomerMapper customerMapper) {
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerDao.getAllCustomers().stream()
                .map(this::toDto)
                .toList();
    }

    public CustomerDto getCustomer(Integer id) {
        Customer customer = customerDao.getCustomer(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(id)
                ));
        return toDto(customer);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        throwErrorIfEmailExists(customerDto.getEmail());
        Customer customer = toEntity(customerDto);
        customer = customerDao.create(customer);
        return toDto(customer);
    }

    public CustomerDto updateCustomer(Integer customerId, CustomerDto customerDto) {
        CustomerDto dto = getCustomer(customerId);
        if (customerDto.getEmail() != null && !dto.getEmail().equals(customerDto.getEmail())) {
            throwErrorIfEmailExists(customerDto.getEmail());
            dto.setEmail(customerDto.getEmail());
        }
        dto.setName(setIfNotNull(dto.getName(), customerDto.getName()));
        dto.setAge(setIfNotNull(dto.getAge(), customerDto.getAge()));
        Customer customer = toEntity(dto);
        Customer updated = customerDao.update(customer);
        return toDto(updated);
    }

    public void deleteCustomer(Integer id) {
        CustomerDto customerDto = getCustomer(id);
        customerDao.delete(customerDto.getId());
    }

    private void throwErrorIfEmailExists(String email) {
        if (customerDao.existsByEmail(email)) {
            throw new DuplicateResourceException("Email is taken. Choose another one");
        }
    }

    private Customer toEntity(CustomerDto dto) {
        return customerMapper.toEntity(dto);
    }

    private CustomerDto toDto(Customer customer) {
        return customerMapper.toDto(customer);
    }
}
