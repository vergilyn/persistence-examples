package com.vergilyn.examples.service;

import com.vergilyn.examples.entity.Customer;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface CustomerService {

    Customer getByJpa(Long customerId);

    Customer getByJdbc(Long customerId);

    Customer getByNamed(Long customerId);

    String queryByInnerPolling();

    Customer getByTransaction();

}
