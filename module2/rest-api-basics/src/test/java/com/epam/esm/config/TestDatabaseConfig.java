package com.epam.esm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("com.epam.esm.repositories")
@PropertySource("classpath:test_db.properties")
@RequiredArgsConstructor
public class TestDatabaseConfig {

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
    public JdbcTemplate jdbcTemplateTest() {
        return new JdbcTemplate(dataSource());
    }
}
