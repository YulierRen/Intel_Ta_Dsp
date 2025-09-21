package com.lty.www.intel_ta_dsp.config;

import com.lty.www.intel_ta_dsp.datasource.DataSourceType;
import com.lty.www.intel_ta_dsp.datasource.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@MapperScan(sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Autowired
    DataSourceApi dataSourceApi;

    @Bean(name = "masterDataSource")
    @Primary
    public HikariDataSource masterDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(dataSourceApi.getMasterUrl());
        ds.setUsername(dataSourceApi.getMasterUsername());
        ds.setPassword(dataSourceApi.getMasterPassword());
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }

    @Bean(name = "slaveDataSource")
    public HikariDataSource slaveDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(dataSourceApi.getSlaveUrl());
        ds.setUsername(dataSourceApi.getSlaveUsername());
        ds.setPassword(dataSourceApi.getSlavePassword());
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return ds;
    }


    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("slaveDataSource") DataSource slave) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER, master);
        targetDataSources.put(DataSourceType.SLAVE, slave);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(master); // 默认主库
        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*.xml"));
        factoryBean.setTypeAliasesPackage("com.lty.www.intel_ta_dsp.entity");
        return factoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}

