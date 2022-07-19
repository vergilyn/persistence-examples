# sharding-sphere-mybatis-plus-examples

1. 只分表，不分库
2. 按`ShardingSphereMonthEntity#create_time`的**月份**分表保存，
例如 sharding_sphere_month_1、sharding_sphere_month_2...sharding_sphere_month_12