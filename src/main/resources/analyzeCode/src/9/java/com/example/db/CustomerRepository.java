package com.example.db;

import com.example.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    long count();

    Customer save(Customer customer);

    Customer findOne(long id);

    Customer findByName(String name);

    List<Customer> findAll();
}