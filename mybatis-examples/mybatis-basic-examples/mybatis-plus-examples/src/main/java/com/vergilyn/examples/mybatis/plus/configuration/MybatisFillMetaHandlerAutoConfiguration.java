package com.vergilyn.examples.mybatis.plus.configuration;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MybatisFillMetaHandlerAutoConfiguration {

	@Bean
	public MetaObjectHandler customFillHandler(){
		return new MetaObjectHandler() {
			@Override
			public void insertFill(MetaObject metaObject) {
				LocalDateTime now = LocalDateTime.now();

				log.info("#insertFill() >>>> begin {}", now);

				this.setFieldValByName("createTime", now, metaObject);
				this.setFieldValByName("updateTime", now, metaObject);
			}

			@Override
			public void updateFill(MetaObject metaObject) {
				LocalDateTime now = LocalDateTime.now();

				log.info("#updateFill() >>>> begin {}", now);
				this.setFieldValByName("updateTime", now, metaObject);
			}
		};
	}
}
