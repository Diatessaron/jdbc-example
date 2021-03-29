package ru.fazlyev.jdbcexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.fazlyev.jdbcexample.config.AppProps;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class JdbcExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcExampleApplication.class, args);
    }
}
