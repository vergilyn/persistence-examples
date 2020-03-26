package com.vergilyn.examples.service;

import javax.transaction.Transactional;

import com.vergilyn.examples.entity.Customer;

/**
 * @author VergiLyn
 * @date 2019-03-21
 */
public interface CustomerService {
    String ROLLBACK_MESSAGE = "transactional rollback!";

    static String email(String mark){
        return mark + "@" + System.currentTimeMillis();
    }

    Customer getByJpa(Long customerId);

    Customer getByJdbc(Long customerId);

    Customer getByNamed(Long customerId);

    Customer updateByJpa(Long customerId, String email);

    int updateByJdbc(Long customerId, String email);

    int updateByNamed(Long customerId, String email);

    void rollback();

    void rollback2();

    /**
     * catch中的 写操作 能否正常提交执行？
     * <pre>
     *   @Transactional
     *   public T save(T t){
     *       try {
     *           save(t);
     *           throw new Exception();
     *       }catch(Exception e) {
     *           t = new T();
     *           save(t);
     *       }
     *   }
     *
     * </pre>
     */
    void rollbackCatchScopeCommit(Long id);

    @Transactional
    void commitSequence();
}
