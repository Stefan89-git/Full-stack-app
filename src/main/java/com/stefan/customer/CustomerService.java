package com.stefan.customer;

import com.stefan.exception.DuplicateResourceException;
import com.stefan.exception.RequestValidationException;
import com.stefan.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao, CustomerMapper customerMapper) {
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
        customerDao.create(customer);
        return toDto(customer);
    }

    public CustomerDto updateCustomer(Integer customerId, CustomerDto customerDto) {
        CustomerDto dto = getCustomer(customerId);
        throwErrorIfEmailExists(customerDto.getEmail());
        if (dto.equals(customerDto)) {
            throw new RequestValidationException("There have been no changes!");
        }
        dto.setName(customerDto.getName());
        dto.setEmail(customerDto.getEmail());
        dto.setAge(customerDto.getAge());
        Customer customer = toEntity(dto);
        customerDao.update(customer);
        return dto;
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
