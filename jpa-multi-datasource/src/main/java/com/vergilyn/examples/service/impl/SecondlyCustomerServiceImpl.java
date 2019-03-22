package com.vergilyn.examples.service.impl;


import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.repository.secondly.SecondlyCustomerRepository;
import com.vergilyn.examples.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@Service("secondlyCustomerService")
@Transactional("secondlyTransactionManager")
public class SecondlyCustomerServiceImpl extends CustomerService {
    @Autowired
    private SecondlyCustomerRepository repository;

    @Override
    public Customer getByJpa(Long customerId) {
        return repository.findById(customerId).get();
    }

    @Override
    public Customer getByJdbc(Long customerId) {
        return repository.getByJdbc(customerId);
    }

    @Override
    public Customer getByNamed(Long customerId) {
        return repository.getByNamed(customerId);
    }

    @Override
    public Customer updateByJpa(Long customerId, String email) {
        Customer customer = getByJpa(customerId);
        customer.setEmail(email);

        return repository.save(customer);
    }

    @Override
    public int updateByJdbc(Long customerId, String email) {
        return repository.updateByJdbc(customerId, email);
    }

    @Override
    public int updateByNamed(Long customerId, String email) {
        return repository.updateByNamed(customerId, email);
    }

    @Override
    public void rollback() {

        super.rollback();

    }

    @Override
    public void commitSequence() {

    }
}
