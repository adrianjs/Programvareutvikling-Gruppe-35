package database;

import layout.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

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
        connect.addTeacher("testTeach@test.com", "test", "test", "test", "test", "test");
        date = LocalDate.now();
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
        event.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddSchoolWork() throws Exception{

        coordinator.addSchoolWork("test", date, date, "12.00", "13.00",0,
                "145387465126655323", 5.0,"AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        event.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddDeadLine() throws Exception{

        coordinator.addDeadLine("test", date, "13",
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        event.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddExam() throws Exception{

        coordinator.addExam("test", date, "12.00", "13.00",
                "145387465126655323","AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        event.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }

    @Test
    public void testAddHomeExam() throws Exception{

        coordinator.addHomeExam("test", date, date, "12.00", "13.00",
                "145387465126655323", 5.0,"AAR4335");
        assertEquals("145387465126655323", event.getLastAddedDes());
        event.stmt.execute("DELETE FROM EVENT WHERE description  ='145387465126655323'");
    }







    @After
    public void tearDown() throws Exception {
        connect.stmt.execute("DELETE FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        connect.close();
        coordinator.close();
        event.close();
    }

}