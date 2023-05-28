package com.linkkou.mybatislog.mybatisplus.boot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
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
import java.util.Properties;

/**
 * 数据源配置
 *
 * @author deppon
 * @version 1.0
 * @date 2020/9/10 19:05
 */
@MapperScan(basePackages = {"com.linkkou.mybatislog.orm.**.dao", "com.baomidou.mybatisplus.samples.quickstart.mapper"}, sqlSessionTemplateRef = EnableDataSourceImpl.MAPPER_SCANNER_CONFIGURER)
public class EnableDataSourceImpl {

    public static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/run/*.xml";
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

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean("mybatisPlusInterceptor")
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        final PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOptimizeJoin(true);
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Bean(SQLSESSIONFACTORY_NAME)
    public SqlSessionFactory buildSqlSessionFactory(@Qualifier(DATASOURCE_NAME) DataSource dataSource,@Qualifier("mybatisPlusInterceptor") MybatisPlusInterceptor mybatisPlusInterceptor) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPlugins(mybatisPlusInterceptor);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
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