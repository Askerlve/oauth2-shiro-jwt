package com.askerlve.ums.web.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Askerlve
 * @Description: 数据源配置
 * @date 2018/4/19上午11:48
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "datasource.auth")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource datasource() {
        return DataSourceBuilder.create().build();
    }

}
