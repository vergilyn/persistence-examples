package com.vergilyn.examples.service.impl;


import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.repository.firstly.FirstlyCustomerRepository;
import com.vergilyn.examples.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@Service("firstlyCustomerService")
@Transactional("firstlyTransactionManager")
public class FirstlyCustomerServiceImpl extends CustomerService {
    @Autowired
    private FirstlyCustomerRepository customerRepository;

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
    @Transactional("firstTransactionManager")
    public Customer updateByJpa(Long customerId, String email) {
        Customer customer = getByJpa(customerId);
        customer.setEmail(email);

        return customerRepository.save(customer);
    }

    @Override
    @Transactional("firstTransactionManager")
    public int updateByJdbc(Long customerId, String email) {
        return customerRepository.updateByJdbc(customerId, email);
    }

    @Override
    @Transactional("firstTransactionManager")
    public int updateByNamed(Long customerId, String email) {
        return customerRepository.updateByNamed(customerId, email);
    }

    @Override
    @Transactional("firstTransactionManager")
    public void rollback() {
        super.rollback();

        throw new RuntimeException(ROLLBACK_MESSAGE);
    }

    @Override
    @Transactional("firstTransactionManager")
    public void commitSequence() {
        super.commitSequence();
    }
}
