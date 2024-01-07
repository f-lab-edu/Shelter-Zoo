package com.noint.shelterzoo.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@Slf4j
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.noint.shelterzoo.domain.*.repository")
@Profile("dev1DB")
public class DBConfigByOne {
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(HikariDataSource dataSource) throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/domain/**/*.xml"));
        log.info("Max-Pool-Size : {}", dataSource.getMaximumPoolSize());
        log.info("Min-Idle : {}", dataSource.getMinimumIdle());
        return sessionFactoryBean;
    }
}