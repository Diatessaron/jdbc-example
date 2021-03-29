package ru.fazlyev.jdbcexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fazlyev.jdbcexample.dao.AuthorDaoJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ApplicationConfig {
    private final Connection connection;

    public ApplicationConfig(AppProps appProps) throws SQLException {
        connection = DriverManager.getConnection(appProps.getDbUrl(), appProps.getUser(), appProps.getPassword());
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Bean
    public AuthorDaoJdbc authorDao() {
        return new AuthorDaoJdbc(connection);
    }
}
