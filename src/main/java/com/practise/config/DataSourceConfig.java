package com.practise.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Base64;


/**
 * @author: realz
 * @package: com.practise.config
 * @date: 2019-09-23
 * @email: zlp951116@hotmail.com
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(new String(Base64.getDecoder().decode(dataSourceProperties.getPassword())));
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setMaxActive(500);
        return dataSource;
    }
}
