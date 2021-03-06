package database;

import controllers.JavaFXThreadingRule;
import layout.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Henning on 20.04.2017.
 */
public class EventTest {
    Connect connect;
    Event event;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {
        connect = new Connect();
        connect.addStudent("testStud@test.com", "test", "test", "test", 1, "test");
        User.getInstance().setUsername("testStud@test.com");
        connect.addStudentSubject("TDT4100");
    }

    @Test
    public void testDroppingAnEvent() throws SQLException {
        event = new Event();
        event.deleteEvent(26089, User.getInstance().getUsername());
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM NOTATTENDINGEVENT WHERE studentEmail='testStud@test.com'");
        m_result_set.next();
        assertEquals(26089, m_result_set.getInt(1));
    }

    @Test
    public void testAddingFeedbackTooEvent() throws SQLException {
        event = new Event();
        event.schoolWorkFeedBack(27474, User.getInstance().getUsername(), 3);
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM FEEDBACK WHERE Username='testStud@test.com'");
        m_result_set.next();
        assertEquals(27474, m_result_set.getInt(1));
        assertEquals(User.getInstance().getUsername(), m_result_set.getString(2));
        assertEquals(3, m_result_set.getInt(3));
    }

    @After
    public void tearDown() throws Exception {
        connect.stmt.execute("DELETE FROM FEEDBACK WHERE Username='testStud@test.com' AND ID=27474");
        connect.stmt.execute("DELETE FROM NOTATTENDINGEVENT WHERE studentEmail='testStud@test.com' AND eventId=26089");
        connect.removeStudentSubject("TDT4100");
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
    }

}