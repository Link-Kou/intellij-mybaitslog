package com.linkkou.mybatislog.mybatis.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源配置
 *
 * @author deppon
 * @version 1.0
 * @date 2020/9/10 19:05
 */
@MapperScan(basePackages = {"com.linkkou.mybatislog.orm.**.dao"}, sqlSessionTemplateRef = EnableDataSourceImpl.MAPPER_SCANNER_CONFIGURER)
public class EnableDataSourceImpl {

    public static final String MAPPER_LOCATION = "classpath*:/**/*Mapper.xml";
    public static final String DATASOURCE_NAME = "dataSourceAuth";
    public static final String TX_AUTHM_ANAGER = "txAuthManager";
    public static final String MAPPER_SCANNER_CONFIGURER = "mapperScannerConfigurerAuth";
    public static final String SQLSESSIONFACTORY_NAME = "sqlSessionFactoryAuth";

    @Bean(DATASOURCE_NAME)
    public DataSource buildDataSource(@Value("${datasource.url}") String url, @Value("${datasource.username}") String username, @Value("${datasource.password}") String password) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setMinIdle(20);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(6000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setFilters("stat");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(SQLSESSIONFACTORY_NAME)
    public SqlSessionFactory buildSqlSessionFactory(@Qualifier(DATASOURCE_NAME) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(TX_AUTHM_ANAGER)
    public DataSourceTransactionManager worldTransactionManager(@Qualifier(DATASOURCE_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(MAPPER_SCANNER_CONFIGURER)
    public SqlSessionTemplate worldSqlSessionTemplate(@Qualifier(SQLSESSIONFACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}