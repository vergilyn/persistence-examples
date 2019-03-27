package com.vergilyn.examples.config.dynamic;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;
import com.vergilyn.examples.config.DynamicDataConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author VergiLyn
 * @date 2019-03-25
 */
public class DynamicDataSourceContextHolder {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final AtomicInteger COUNTER = new AtomicInteger();

    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DynamicDataConfiguration.DataSourceKey.master::name);

    public static final List<Object> DATASOURCE_KEYS = Lists.newArrayList();

    public static final List<Object> SLAVER_DATASOURCE_KEYS = Lists.newArrayList();

    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * Use master data source.
     */
    public static void useMasterDataSource() {
        CONTEXT_HOLDER.set(DynamicDataConfiguration.DataSourceKey.master.name());
    }

    public static void useSlaveDataSource() {
        try {
            int datasourceKeyIndex = COUNTER.getAndIncrement() % SLAVER_DATASOURCE_KEYS.size();
            CONTEXT_HOLDER.set(String.valueOf(SLAVER_DATASOURCE_KEYS.get(datasourceKeyIndex)));
        } catch (Exception e) {
            logger.error("Switch slave datasource failed, error message is {}", e.getMessage());
            useMasterDataSource();
            e.printStackTrace();
        }
    }

    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    public static boolean containDataSourceKey(String key) {
        return DATASOURCE_KEYS.contains(key);
    }
}
