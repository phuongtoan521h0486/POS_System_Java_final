package com.thd.pos_system_java_final.models.Customer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findAll();

    Customer findByPhoneNumber(String phone);

    Customer findByCustomerId(int id);
}
