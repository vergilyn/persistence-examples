package com.vergilyn.examples.config.dynamic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author VergiLyn
 * @date 2019-03-25
 */
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private final String[] QUERY_PREFIX = {"get", "query"};

    /**
     * service/dao aspect.
     */
    @Pointcut("execution( * com.vergilyn.examples.service.*.*(..)) && !@annotation(javax.transaction.Transactional)")
    public void daoAspect() {
    }

    /**
     * switch datasource.
     */
    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.useSlaveDataSource();
            logger.debug("Switch DataSource to [{}] in Method [{}]",
                    DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
        }
    }

    /**
     * restore datasource.
     */
    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.debug("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

    private Boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
