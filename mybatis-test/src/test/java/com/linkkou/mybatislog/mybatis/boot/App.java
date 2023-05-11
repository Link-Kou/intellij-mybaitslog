package com.linkkou.mybatislog.mybatis.boot;

import com.linkkou.mybatislog.mybatis.boot.config.EnableDataSource;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 引导
 *
 * @author deppon
 * @version 1.0
 * @date 2020/9/10 19:05
 */
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class})
@ComponentScan({"com.linkkou.mybatislog"})
@EnableDataSource
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
