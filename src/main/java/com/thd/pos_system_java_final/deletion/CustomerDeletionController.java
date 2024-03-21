package com.thd.pos_system_java_final.deletion;

import com.thd.pos_system_java_final.models.Customer.CustomerRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerDeletionController extends DeletionController{
    private CustomerRepository customerRepository;
    @Override
    protected void deleteEntity(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    protected String redirectToLink() {
        return "redirect:/customer";
    }

    
}