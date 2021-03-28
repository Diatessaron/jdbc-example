package ru.fazlyev.jdbcexample.dao;

import ru.fazlyev.jdbcexample.domain.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDao {
    int count() throws SQLException;

    void insert(Author author) throws SQLException;

    Author getAuthorById(long id) throws SQLException;

    Author getAuthorByName(String name) throws SQLException;

    List<Author> getAll() throws SQLException;

    void update(Author author) throws SQLException;

    void deleteById(long id) throws SQLException;
}
