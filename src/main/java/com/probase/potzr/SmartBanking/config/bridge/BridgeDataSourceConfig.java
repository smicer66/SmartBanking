package com.probase.potzr.SmartBanking.config.bridge;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.probase.potzr.SmartBanking.models.bridge"}
)
public class BridgeDataSourceConfig {

    @Value("${spring.dummy-datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.dummy-datasource.username}")
    private String username;
    @Value("${spring.dummy-datasource.password}")
    private String password;
    @Value("${spring.dummy-datasource.url}")
    private String dataSourceUrl;




    @Bean(name="BridgeDataSourceConfig")
    public DataSource dataSource()
    {
        DataSourceBuilder dsb = DataSourceBuilder.create();
        dsb.driverClassName(driverClassName);
        dsb.username(username);;
        dsb.password(password);
        dsb.url(dataSourceUrl);



        return dsb.build();
    }
}
