package com.linkkou.mybatislog.mybatisplus.boot.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源注解
 *
 * @author deppon
 * @version 1.0
 * @date 2020/9/10 19:05
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EnableDataSourceImpl.class})
public @interface EnableDataSource {

}
