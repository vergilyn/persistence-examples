package com.vergilyn.examples.service.impl;


import javax.transaction.Transactional;

import com.vergilyn.examples.entity.Customer;
import com.vergilyn.examples.repository.CustomerRepository;
import com.vergilyn.examples.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String queryByInnerPolling(){
        Long id = 1L;
        Customer first = getByJpa(id);
        Customer second = getByJdbc(id);
        Customer third = getByNamed(id);
        Customer fourth = getByJpa(id);
        Customer fifth = getByJdbc(id);

        return String.format("first: %s, second: %s, third: %s, fourth: %s, fifth: %s \n",
                first.getEmail(), second.getEmail(), third.getEmail(), fourth.getEmail(), fifth.getEmail());
    }

    @Override
    @Transactional
    public Customer getByTransaction() {
        return getByJpa(1L);
    }
}
