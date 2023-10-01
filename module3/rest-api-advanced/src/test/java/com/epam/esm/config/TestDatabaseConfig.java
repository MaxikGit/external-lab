package com.epam.esm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan({ "com.epam.esm.certificate.repository", "com.epam.esm.tag.repository",
        "com.epam.esm.order.repository", "com.epam.esm.user.repository"})
@PropertySource("classpath:test_datasource.properties")
@RequiredArgsConstructor
public class TestDatabaseConfig {

    private static final String[] ENTITIES_PACKAGES_TO_SCAN = { "com.epam.esm.certificate.model",
            "com.epam.esm.tag.model", "com.epam.esm.order.model", "com.epam.esm.user.model"};
    private final Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("test.driver")));
        dataSource.setUrl(environment.getProperty("test.jdbcUrl"));
        dataSource.setUsername(environment.getProperty("test.user_name"));
        dataSource.setPassword(environment.getProperty("test.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(ENTITIES_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private HibernateJpaVendorAdapter vendorAdaptor() {
        return new HibernateJpaVendorAdapter();
    }
}
