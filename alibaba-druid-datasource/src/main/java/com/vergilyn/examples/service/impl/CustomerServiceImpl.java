package com.vergilyn.examples.service.impl;


import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.repository.CustomerRepository;
import com.vergilyn.examples.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getByJpa(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    @Override
    public Customer getByJdbc(Long customerId) {
        return customerRepository.getByJdbc(customerId);
    }

    @Override
    public Customer getByNamed(Long customerId) {
        return customerRepository.getByNamed(customerId);
    }

    @Override
    @Transactional
    public Customer updateByJpa(Long customerId, String email) {
        Customer customer = getByJpa(customerId);
        customer.setEmail(email);

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public int updateByJdbc(Long customerId, String email) {
        return customerRepository.updateByJdbc(customerId, email);
    }

    @Override
    @Transactional
    public int updateByNamed(Long customerId, String email) {
        return customerRepository.updateByNamed(customerId, email);
    }

}
