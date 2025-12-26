package com.probase.potzr.SmartBanking.config.core;


import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import com.probase.potzr.SmartBanking.models.core.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackageClasses = {Client.class, ClientSetting.class, Transaction.class},
        transactionManagerRef = "transactionManager"
)
@Primary
public class DataSourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String dataSourceUrl;







    @Bean(name="DataSourceConfig")
    @Primary
    public DataSource dataSource()
    {
        DataSourceBuilder dsb = DataSourceBuilder.create();
        dsb.driverClassName(driverClassName);
        dsb.username(username);;
        dsb.password(password);
        dsb.url(dataSourceUrl);



        return dsb.build();
    }


    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("com.probase.potzr.SmartBanking.models.core");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);

        Map<String,String> props = new HashMap<>();
        props.put("hibernate.dialect","org.hibernate.dialect.SQLServerDialect");
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","update");
        bean.setJpaPropertyMap(props);

        return bean;
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }
}
