package ru.fazlyev.jdbcexample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private final String dbUrl;
    private final String user;
    private final String password;

    public AppProps(String url, String user, String password) {
        this.dbUrl = url;
        this.user = user;
        this.password = password;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
