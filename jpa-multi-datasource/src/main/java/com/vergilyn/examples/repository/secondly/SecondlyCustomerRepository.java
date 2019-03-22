package com.vergilyn.examples.repository.secondly;

import com.vergilyn.examples.entity.Customer;

import org.springframework.data.repository.CrudRepository;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface SecondlyCustomerRepository extends CrudRepository<Customer, Long>, CustomSecondlyCustomerRepository {
}
