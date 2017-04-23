package database;

import layout.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Henning on 18.04.2017.
 */
public class TeacherTest {
    Connect connect;
    Teacher coordinator;
    Event event;
    LocalDate date;
    @Before
    public void setUp() throws Exception {
        User.getInstance().setUsername("testTeach@test.com");
        connect = new Connect();
        coordinator = new Teacher();
        event = new Event();
        coordinator.addCourseCoordinator("testTeach@test.com", "test", "test", "test", "test", "test");
        connect.addStudent("testStud@test.com", "test", "test", "test", 2, "test");
        date = LocalDate.now();
    }

    @Test
    public void testAddingANewCourseCoordinator() throws SQLException {
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        m_result_set.next();
        assertEquals("testTeach@test.com", m_result_set.getString(1));
    }

    @Test
    public void testGetTeacherEmail(){
        String coordinatorEmail = coordinator.getTeacher("AAR4335");
        assertEquals("francesco.goia@ntnu.no", coordinatorEmail);

    }

    @Test
    public void testAddLecture() throws Exception{

        coordinator.addLecture("test", date, "12.00", "13.00",0,
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
        coordinator.addLecture("test", date, "12.00", "13.00",5,
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
        coordinator.addLecture("test", LocalDate.now().plusMonths(5), "12.00", "13.00",5,
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddSchoolWork() throws Exception{

        coordinator.addSchoolWork("test", date, date, "12.00", "13.00",0,
                "145387465126655323", 5.0,"AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
        coordinator.addSchoolWork("test", date, date, "12.00", "13.00",5,
                "145387465126655323", 5.0,"AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
        coordinator.addSchoolWork("test", LocalDate.now().plusMonths(5), LocalDate.now().plusMonths(5), "12.00", "13.00",5,
                "145387465126655323", 5.0,"AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddDeadLine() throws Exception{

        coordinator.addDeadLine("test", date, "13",
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddExam() throws Exception{

        coordinator.addExam("test", date, "12.00", "13.00",
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddHomeExam() throws Exception{

        coordinator.addHomeExam("test", date, date, "12.00", "13.00",
                "145387465126655323", 5.0,"AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        connect.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddingANewSubject(){
        coordinator.addSubject("TST0000", "test", "test", User.getInstance().getUsername());
        ArrayList<ArrayList<String>> subjects = coordinator.getSubjects(User.getInstance().getUsername());
        assertEquals(1, subjects.size());
        assertEquals("TST0000", subjects.get(0).get(0));

    }

    @Test
    public void testGettingAllEventsInSubject(){
        coordinator.addSubject("TST0000", "test", "test", User.getInstance().getUsername());
        coordinator.addLecture("test", date, "12.00", "13.00",0,
                "145387465126655323","TST0000");
        coordinator.addSchoolWork("test2", date, date, "13.00", "14.00",0,
                "145387465126655323", Double.valueOf(2),"TST0000");
        ArrayList<ArrayList<String>> events = coordinator.getEvents("TST0000");
        assertEquals(2, events.size());
        assertEquals("test", events.get(0).get(1));
    }

    @Test
    public void testGettingHoursOfWork() throws SQLException {
        coordinator.addSubject("TST0000", "test", "test", User.getInstance().getUsername());
        coordinator.addSchoolWork("test2", date, date, "13.00", "14.00",0,
                "145387465126655323", Double.valueOf(2),"TST0000");
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM EVENT WHERE subjectCode='TST0000'");
        m_result_set.next();
        event.schoolWorkFeedBack(m_result_set.getInt("eventID"), "testStud@test.com", 4);
        ArrayList<ArrayList<String>> feedback = coordinator.getStudentFeedBack("TST0000");
        assertEquals(1, feedback.size());
        assertEquals("4", feedback.get(0).get(0));
        assertEquals("2", feedback.get(0).get(3));
    }

    @After
    public void tearDown() throws Exception {
        connect.stmt.execute("DELETE FROM FEEDBACK WHERE Username='testStud@test.com'");
        connect.stmt.execute("DELETE FROM EVENT WHERE description='145387465126655323'");
        connect.stmt.execute("DELETE FROM SUBJECT WHERE coordinatorEmail='testTeach@test.com'");
        connect.stmt.execute("DELETE FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
    }

}