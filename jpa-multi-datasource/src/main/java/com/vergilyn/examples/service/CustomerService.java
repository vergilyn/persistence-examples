package com.vergilyn.examples.service;

import com.vergilyn.examples.entity.Customer;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public abstract class CustomerService {
    public static final String ROLLBACK_MESSAGE = "transactional rollback!";

    public static String email(String mark){
        return mark + "@" + System.currentTimeMillis();
    }

    public abstract Customer getByJpa(Long customerId);

    public abstract Customer getByJdbc(Long customerId);

    public abstract Customer getByNamed(Long customerId);

    public abstract Customer updateByJpa(Long customerId, String email);

    public abstract int updateByJdbc(Long customerId, String email);

    public abstract int updateByNamed(Long customerId, String email);

    public void rollback(){
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

    public void commitSequence(){
        Long customerId = 1L;

        updateByJpa(customerId, "before");
        updateByJdbc(customerId, "after");
    }
}
