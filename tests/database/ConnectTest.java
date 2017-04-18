package database;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Henning on 18.04.2017.
 */
public class ConnectTest {

    @Test
    public void setupAndCloseConnectionTest() throws SQLException {
        Connect connect = new Connect();
        assertNotEquals(null, connect.conn);
        connect.close();
        assertEquals(true, connect.conn.isClosed());
    }

    @Test
    public void addingNewUsersTest() throws SQLException {
        Connect connect = new Connect();
        connect.addStudent("testStud@test.com", "test", "test", "test", 1, "test");
        connect.addTeacher("testTeach@test.com", "test", "test", "test", "test", "test");

        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM STUDENT WHERE email='testStud@test.com'");
        m_result_set.next();
        assertEquals("testStud@test.com", m_result_set.getString(1));
        m_result_set = connect.stmt.executeQuery("SELECT * FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        m_result_set.next();
        assertEquals("testTeach@test.com", m_result_set.getString(1));

        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
        connect.stmt.execute("DELETE FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");

    }

    @Test
    public void addAndRemoveSubjectsToStudent(){

    }

}