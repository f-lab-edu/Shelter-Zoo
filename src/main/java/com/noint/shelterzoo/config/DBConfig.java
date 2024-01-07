package com.noint.shelterzoo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.noint.shelterzoo.domain.*.repository")
@Profile(value = {"local", "dev"})
public class DBConfig {
    @Value("${spring.datasource.master.url}")
    private String masterUrl;
    @Value("${spring.datasource.master.username}")
    private String masterUsername;
    @Value("${spring.datasource.master.password}")
    private String masterPassword;
    @Value("${spring.datasource.master.maximum-pool-size}")
    private int masterMaxPoolSize;
    @Value("${spring.datasource.master.minimum-idle}")
    private int masterMinIdle;
    @Value("${spring.datasource.slave.url}")
    private String slaveUrl;
    @Value("${spring.datasource.slave.username}")
    private String slaveUsername;
    @Value("${spring.datasource.slave.password}")
    private String slavePassword;
    @Value("${spring.datasource.slave.maximum-pool-size}")
    private int slaveMaxPoolSize;
    @Value("${spring.datasource.slave.minimum-idle}")
    private int slaveMinIdle;

    @Bean
    public DataSource masterDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(masterUrl);
        hikariConfig.setUsername(masterUsername);
        hikariConfig.setPassword(masterPassword);
        hikariConfig.setMaximumPoolSize(masterMaxPoolSize);
        hikariConfig.setMinimumIdle(masterMinIdle);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        log.info("Master-Max-Pool-Size : {}", hikariDataSource.getMaximumPoolSize());
        log.info("Master-Min-Idle : {}", hikariDataSource.getMinimumIdle());
        return hikariDataSource;
    }

    @Bean
    public DataSource slaveDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(slaveUrl);
        hikariConfig.setUsername(slaveUsername);
        hikariConfig.setPassword(slavePassword);
        hikariConfig.setMaximumPoolSize(slaveMaxPoolSize);
        hikariConfig.setMinimumIdle(slaveMinIdle);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        log.info("Slave-Max-Pool-Size : {}", hikariDataSource.getMaximumPoolSize());
        log.info("Slave-Min-Idle : {}", hikariDataSource.getMinimumIdle());
        return hikariDataSource;
    }

    @Bean
    public DataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                String targetDB = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                        ? "SLAVE" : "MASTER";
                log.info("targetDB : {}", targetDB);
                return targetDB;
            }
        };

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("MASTER", masterDataSource);
        targetDataSources.put("SLAVE", slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    @Bean
    public DataSource proxyDataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource proxyDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(proxyDataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource proxyDataSource) throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(proxyDataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/domain/**/*.xml"));
        return sessionFactoryBean;
    }
}