package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer getCustomerByCustomerId (Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    public Customer getCustomerByPetId (Long petId) {
        return customerRepository.findCustomerByPetId(petId);
    }

    public Customer saveCustomer (Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers () {
        Iterable<Customer> iterableResult = customerRepository.findAll();
        return iterableToList(iterableResult);
    }

    /**
     * helper method to transform Iterable into List
     * @param customers
     * @return
     */
    public List<Customer> iterableToList (Iterable<Customer> customers) {
        List<Customer> res = new ArrayList<>();
        customers.forEach(customer -> {
            res.add(customer);
        });
        return res;
    }
}
