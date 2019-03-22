package com.vergilyn.examples.repository.firstly;

import com.vergilyn.examples.entity.Customer;

import org.springframework.data.repository.CrudRepository;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface FirstlyCustomerRepository extends CrudRepository<Customer, Long>, CustomFirstlyCustomerRepository {
}
