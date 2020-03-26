package com.vergilyn.examples.service.impl;


import java.util.Date;

import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.repository.CustomerRepository;
import com.vergilyn.examples.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
@Service
@Slf4j
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

    @Override
    @Transactional
    public void rollback() {
        Long customerId = 1L;
        Customer before = null; //getByJpa(customerId);

        updateByJpa(customerId, CustomerService.email("jpa"));

        Customer after = getByJpa(customerId);

        Customer jdbc = getByJdbc(customerId);

        Customer named = getByNamed(customerId);

        System.out.printf("before: %s, after >>>> jpa: %s, jdbc: %s, named: %s",
                before == null ? null : before.getEmail(),
                after.getEmail(),
                jdbc.getEmail(),
                named.getEmail());

        throw new RuntimeException(ROLLBACK_MESSAGE);
    }

    @Override
    @Transactional
    public void rollback2() {
        Long customerId = 1L;
        Customer before = null; //getByJpa(customerId);

        updateByJdbc(customerId, CustomerService.email("jpa"));

        Customer after = getByJpa(customerId);

        Customer jdbc = getByJdbc(customerId);

        Customer named = getByNamed(customerId);

        System.out.printf("before: %s, after >>>> jpa: %s, jdbc: %s, named: %s",
                before == null ? null : before.getEmail(),
                after.getEmail(),
                jdbc.getEmail(),
                named.getEmail());

        throw new RuntimeException(ROLLBACK_MESSAGE);
    }

    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void rollbackCatchScopeCommit(Long id) {
        Customer customer = customerRepository.getByJdbc(id);
        customer.setLastUpdate(new Date());
        try {
            customerRepository.save(customer);
            throw new RuntimeException(ROLLBACK_MESSAGE);
        } catch (Exception e){
            log.error("rollbackCatchScopeCommit() error, and execute save/update. error-message: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void commitSequence() {
        Long customerId = 1L;

        updateByJpa(customerId, "before");
        updateByJdbc(customerId, "after");

    }
}
