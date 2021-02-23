package com.vergilyn.examples.mybatis.cache.mapper;

import com.vergilyn.examples.mybatis.cache.entity.MybatisCacheEntity;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.impl.PerpetualCache;

import static com.vergilyn.examples.mybatis.cache.entity.MybatisCacheEntity.TABLE_NAME;

/**
 *
 * @author vergilyn
 * @since 2021-02-02
 */
@Mapper
// 声明这个namespace使用二级缓存，并且可以自定义配置。
@CacheNamespace(implementation = PerpetualCache.class, eviction = LruCache.class,
		flushInterval = 30_000)
//@CacheNamespaceRef  // 代表引用别的命名空间的Cache配置，两个命名空间的操作使用的是同一个Cache。
public interface MybatisCacheMapper {

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.DEFAULT)
	MybatisCacheEntity findWithCache(Integer id);

	@Select("SELECT * FROM " + TABLE_NAME + " WHERE id = #{id}")
	@Options(useCache = false)
	MybatisCacheEntity findNoCache(Integer id);

	@Update("UPDATE " + TABLE_NAME + " SET `name` = #{name} WHERE id = #{id}")
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.DEFAULT)
	void update(@Param("id") Integer id, @Param("name") String name);

	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
	@Insert("INSERT INTO " + TABLE_NAME + " (create_time, is_deleted, name, enum_field) "
			+ " VALUES(#{createTime}, #{isDeleted}, #{name}, #{enumField})")
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.DEFAULT)
	int insert(MybatisCacheEntity entity);

	@Delete("DELETE FROM " + TABLE_NAME + " WHERE id = #{id}")
	@Options(useCache = true, flushCache = Options.FlushCachePolicy.DEFAULT)
	int delete(Integer id);
}
