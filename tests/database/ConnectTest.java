package database;

import algorithm.*;
import algorithm.Activity;
import layout.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
        connect.close();

    }

    @Test
    public void addAndRemoveSubjectsToStudent() throws SQLException {
        Connect connect = new Connect();
        connect.addStudent("testStud@test.com", "test", "test", "test", 1, "test");
        User.getInstance().setUsername("testStud@test.com");
        connect.addStudentSubject("TDT4100");
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM STUDTAKESUB WHERE studentEmail='testStud@test.com'");
        m_result_set.next();
        assertEquals("testStud@test.com", m_result_set.getString(2));
        connect.removeStudentSubject("TDT4100");
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
        connect.close();
    }

    @Test
    public void addAndRemoveActivity(){
        algorithm.Activity activity = new Activity(new Date(), new Date(),
                "11", "12",
                "testAct", "testAct",
                5, false,
                10, "FFFFFF");
        Connect connect = new Connect();
//        connect.addActivity(activity.getName(), new java.sql.Date(activity.getStartDate().getTime()), activity.isRepeating(), activity.getSlotPriority()
//        , activity.getStartTime(), activity.getEndTime(), User.getInstance().getUsername(), activity.getDescription());
    }

}