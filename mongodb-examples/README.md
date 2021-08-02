# mongodb-examples

## spring-data-mongodb
- `SimpleMongoRepository`
- `SimpleJpaRepository`

1. `SimpleJpaRepository` 中的 `saveAll` 并未使用 insert-many，而是 **for-each save**。
`SimpleMongoRepository#savelAll` 最终是使用的 insert-many。


## Q&A
### Q1. `insert/insertAll` VS `save/saveAll`
**mongodb:**
1. 如果不存在`_id`，那么`insert & save`都表示新增。
2. 如果存在`_id`。`insert`抛出异常，`save`等价于 update。

**spring-data-mongodb:**  
不同的实现类 `SimpleMongoRepository / SimpleJpaRepository`。

如果能明确是 **新增（`_id == null`）** 时，建议使用 insert/insertAll，
性能上有差别（包括java实现上也有区别，类似与会先`findById`，再insert/update）。