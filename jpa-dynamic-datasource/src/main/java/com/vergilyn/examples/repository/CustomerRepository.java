package com.vergilyn.examples.repository;

import com.vergilyn.examples.entity.Customer;

import org.springframework.data.repository.CrudRepository;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>, CustomCustomerRepository {
}
