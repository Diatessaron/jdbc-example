package ru.fazlyev.jdbcexample.shell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import ru.fazlyev.jdbcexample.dao.AuthorDaoJdbc;
import ru.fazlyev.jdbcexample.domain.Author;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorCommandsTest {
    @MockBean
    private AuthorDaoJdbc authorDaoJdbc;

    @Autowired
    private Shell shell;
    public final Author jamesJoyce = new Author(1, "James Joyce");

    @Test
    void testInsertMethodByTimesOfDaoInvocation() throws SQLException {
        final Author foucault = new Author("Michel Foucault");

        doNothing().when(authorDaoJdbc).insert(foucault);

        shell.evaluate(() -> "aInsert Michel,Foucault");

        verify(authorDaoJdbc, times(1)).insert(foucault);
    }

    @Test
    void shouldReturnCorrectMessage() throws SQLException {
        doNothing().when(authorDaoJdbc).insert(new Author("Michel Foucault"));

        final String expected = "You successfully inserted a Michel Foucault to repository";
        final String actual = shell.evaluate(() -> "aInsert Michel,Foucault").toString();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAuthorByIdByMessageComparison() throws SQLException {
        when(authorDaoJdbc.getAuthorById(1)).thenReturn(jamesJoyce);

        final String expected = jamesJoyce.toString();
        final String actual = shell.evaluate(() -> "authorById 1").toString();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAuthorByNameByMessageComparison() throws SQLException {
        when(authorDaoJdbc.getAuthorByName(jamesJoyce.getName())).thenReturn(jamesJoyce);

        final String expected = jamesJoyce.toString();
        final String actual = shell.evaluate(() -> "authorByName James,Joyce").toString();

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllByMessageComparison() throws SQLException {
        when(authorDaoJdbc.getAll()).thenReturn(List.of(jamesJoyce));

        final String expected = List.of(jamesJoyce).toString();
        final String actual = shell.evaluate(() -> "aGetAll").toString();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnCorrectMessageAfterUpdateMethod() {
        final String expected = "Michel Foucault was updated";
        final String actual = shell.evaluate(() -> "aUpdate 1 Michel,Foucault").toString();

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnCorrectMessageAfterDeleteMethod() throws SQLException {
        when(authorDaoJdbc.getAuthorById(1)).thenReturn(jamesJoyce);

        final String expected = "James Joyce was deleted";
        final String actual = shell.evaluate(() -> "aDelete 1").toString();

        assertEquals(expected, actual);
    }
}
