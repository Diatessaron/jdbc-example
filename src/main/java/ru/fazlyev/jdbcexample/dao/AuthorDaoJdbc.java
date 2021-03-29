package ru.fazlyev.jdbcexample.dao;

import ru.fazlyev.jdbcexample.domain.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJdbc implements AuthorDao {
    private final Connection connection;

    public AuthorDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int count() throws SQLException {
        final ResultSet resultSet = connection.createStatement().executeQuery("select count(*) row_count from author");
        resultSet.next();

        return resultSet.getInt("row_count");
    }

    @Override
    public void insert(Author author) throws SQLException {
        final String query = String.format("insert into author (name) values ('%s')", author.getName());

        connection.createStatement().executeUpdate(query);
    }

    @Override
    public Author getAuthorById(long id) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement("select id, name from author where id = ?");
        preparedStatement.setLong(1, id);
        final ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return mapAuthor(resultSet);
    }

    @Override
    public Author getAuthorByName(String name) throws SQLException {
        final PreparedStatement preparedStatement = connection.prepareStatement("select id, name from author where name = ?");
        preparedStatement.setString(1, name);
        final ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return mapAuthor(resultSet);
    }

    @Override
    public List<Author> getAll() throws SQLException {
        List<Author> result = new ArrayList<>();

        final PreparedStatement preparedStatement = connection.prepareStatement("select id, name from author");
        final ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            result.add(mapAuthor(resultSet));
        }

        return result;
    }

    @Override
    public void update(Author author) throws SQLException {
        final String query = String.format("update author set name = '%s' where id = %d", author.getName(), author.getId());

        connection.createStatement().executeUpdate(query);
    }

    @Override
    public void deleteById(long id) throws SQLException {
        final String query = String.format("delete from author where id = %d", id);

        connection.createStatement().executeUpdate(query);
    }

    private Author mapAuthor(ResultSet resultSet) throws SQLException {
        final long id = resultSet.getLong("id");
        final String name = resultSet.getString("name");

        return new Author(id, name);
    }
}
