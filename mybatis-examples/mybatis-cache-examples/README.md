# mybatis-cache-examples

+ [meituan - 聊聊MyBatis缓存机制](https://tech.meituan.com/2018/01/19/mybatis-cache.html)

> 一级缓存 总结
> 1. MyBatis一级缓存的生命周期和SqlSession一致。
> 2. MyBatis一级缓存内部设计简单，只是一个没有容量限定的HashMap，在缓存的功能性上有所欠缺。
> 3. MyBatis的一级缓存最大范围是SqlSession内部（且根据 mapper/namespace 隔离），有多个SqlSession或者分布式的环境下，数据库写操作会引起脏数据，建议设定缓存级别为Statement。
