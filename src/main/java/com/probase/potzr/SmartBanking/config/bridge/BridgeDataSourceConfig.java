package com.probase.potzr.SmartBanking.config.bridge;


import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.orm.jpa.persistenceunit.ManagedClassNameFilter;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import javax.sql.DataSource;
import javax.sql.DataSource;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "bridgeFundsTransferEntityManagerFactory",
        basePackageClasses = {BridgeFundsTransfer.class},
        transactionManagerRef = "bridgeFundsTransferTransactionManager"
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



    @Bean("bridgeFundsTransferDataSource")
    @ConfigurationProperties(prefix = "spring.dummy-datasource")
    public DataSource bridgeFundsTransferDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("bridgeFundsTransferJdbcTemplate")
    public JdbcTemplate bridgeFundsTransferJdbcTemplate(@Qualifier("bridgeFundsTransferDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("bridgeFundsTransferEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean bridgeFundsTransferEntityManagerFactory(
            @Qualifier("bridgeFundsTransferDataSource") DataSource dataSource,
            JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.probase.potzr.SmartBanking.models.bridge");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("bridgeFundsTransferPersistenceUnit");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        Map prop = jpaProperties.getProperties();
        prop.put("hibernate.dialect","org.hibernate.dialect.SQLServerDialect");
        jpaProperties.setProperties(prop);
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties.getProperties());


        return localContainerEntityManagerFactoryBean;
    }

    @Bean("bridgeFundsTransferTransactionManager")
    public PlatformTransactionManager bridgeFundsTransferTransactionManager(@Qualifier("bridgeFundsTransferEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
