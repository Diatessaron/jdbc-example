package ru.fazlyev.jdbcexample.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.fazlyev.jdbcexample.config.ApplicationConfig;
import ru.fazlyev.jdbcexample.dao.AuthorDao;
import ru.fazlyev.jdbcexample.domain.Author;

import java.sql.SQLException;

@ShellComponent
public class AuthorCommands {
    private final ApplicationConfig applicationConfig;
    private final AuthorDao authorDao;

    public AuthorCommands(ApplicationConfig applicationConfig, AuthorDao authorDao) throws SQLException {
        this.applicationConfig = applicationConfig;
        this.authorDao = authorDao;
    }

    @ShellMethod(key = {"ac", "aCount", "authorCount"}, value = "Count of authors")
    public String count() throws SQLException {
        return authorDao.count() + " of authors in the table";
    }

    @ShellMethod(key = {"ai", "aInsert"}, value = "Insert author. Arguments: authors name. " +
            "Please, put comma instead of space in each argument")
    public String insert(@ShellOption("Author") String name) throws SQLException {
        final Author author = new Author(reformatString(name));
        authorDao.insert(author);

        return String.format("You successfully inserted a %s to repository", author.getName());
    }

    @ShellMethod(key = {"abi", "authorById"}, value = "Get author by id")
    public String getAuthorById(@ShellOption("Id") long id) throws SQLException {
        return authorDao.getAuthorById(id).toString();
    }

    @ShellMethod(key = {"abn", "authorByName"}, value = "Get author by name. " +
            "Please, put comma instead of space in each argument")
    public String getAuthorByName(@ShellOption("Name") String name) throws SQLException {
        return authorDao.getAuthorByName(reformatString(name)).toString();
    }

    @ShellMethod(key = {"aga", "aGetAll"}, value = "Get all authors")
    public String getAll() throws SQLException {
        return authorDao.getAll().toString();
    }

    @ShellMethod(key = {"au", "aUpdate"}, value = "Update author in repository. Arguments: id, author. " +
            "Please, put comma instead of space in each argument")
    public String update(@ShellOption("Id") long id,
                         @ShellOption("Name") String name) throws SQLException {
        final Author author = new Author(id, reformatString(name));
        authorDao.update(author);

        return String.format("%s was updated", author.getName());
    }

    @ShellMethod(key = {"ad", "aDelete"}, value = "Delete author by id")
    public String deleteById(@ShellOption("Id") long id) throws SQLException {
        final Author author = authorDao.getAuthorById(id);
        authorDao.deleteById(id);

        return String.format("%s was deleted", author.getName());
    }

    @ShellMethod(key = {"cc", "closeConnection"}, value = "Close connection")
    public String closeConnection() throws SQLException {
        applicationConfig.closeConnection();

        return "Connection closed";
    }

    private String reformatString(String str) {
        return String.join(" ", str.split(","));
    }
}
