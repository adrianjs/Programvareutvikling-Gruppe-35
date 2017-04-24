package algorithm;

import calendar.Activity;
import calendar.Cell;
import database.Connect;
import layout.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;

import static org.junit.Assert.*;

/**
 * Created by Henning on 19.04.2017.
 */
public class SuperSorterTest {
    Connect connect;
    SuperSorter superSorter;
    Activity dummy1;
    Activity dummy2;
    Activity dummy3;
    Activity dummy4;
    Activity dummy5;
    @Before
    public void setUp() throws Exception {
        superSorter = new SuperSorter();
        connect = new Connect();
        connect.addStudent("testStud@test.com", "test", "test", "test", 1, "test");
        User.getInstance().setUsername("testStud@test.com");
        dummy1 = new Activity(new Date(), new Date(), "08", "10", "testAct", "testAct", 2, false, 10, "FFFFFF");
        dummy2 = new Activity(new Date(), new Date(), "11", "12", "testAct", "testAct", 3, false, 11, "FFFFFF");
        dummy3 = new Activity(new Date(), new Date(), "11", "14", "testAct", "testAct", 4, false, 12, "FFFFFF");
        dummy4 = new Activity(new Date(), new Date(), "15", "18", "testAct", "testAct", 3, false, 13, "FFFFFF");
        dummy5 = new Activity(new Date(), new Date(), "17", "20", "testAct", "testAct", 3, false, 14, "FFFFFF");

        connect.addActivity(dummy1.getName(), new java.sql.Date(dummy1.getStartDate().getTime()), dummy1.isRepeating(), dummy1.getSlotPriority(), Double.parseDouble(dummy1.getStartTime()), Double.parseDouble(dummy1.getEndTime()), User.getInstance().getUsername(), dummy1.getDescription());
        connect.addActivity(dummy2.getName(), new java.sql.Date(dummy2.getStartDate().getTime()), dummy2.isRepeating(), dummy2.getSlotPriority(), Double.parseDouble(dummy2.getStartTime()), Double.parseDouble(dummy2.getEndTime()), User.getInstance().getUsername(), dummy2.getDescription());
        connect.addActivity(dummy3.getName(), new java.sql.Date(dummy3.getStartDate().getTime()), dummy3.isRepeating(), dummy3.getSlotPriority(), Double.parseDouble(dummy3.getStartTime()), Double.parseDouble(dummy3.getEndTime()), User.getInstance().getUsername(), dummy3.getDescription());
        connect.addActivity(dummy4.getName(), new java.sql.Date(dummy4.getStartDate().getTime()), dummy4.isRepeating(), dummy4.getSlotPriority(), Double.parseDouble(dummy4.getStartTime()), Double.parseDouble(dummy4.getEndTime()), User.getInstance().getUsername(), dummy4.getDescription());
        connect.addActivity(dummy5.getName(), new java.sql.Date(dummy5.getStartDate().getTime()), dummy5.isRepeating(), dummy5.getSlotPriority(), Double.parseDouble(dummy5.getStartTime()), Double.parseDouble(dummy5.getEndTime()), User.getInstance().getUsername(), dummy5.getDescription());

        connect.addStudentSubject("TDT4100");
        connect.addStudentSubject("TDT4145");
    }

    @Test
    public void testOfDataCollecting() throws ParseException, SQLException, IOException {
        superSorter.dataCollect();
        assertEquals(5, superSorter.getActivities().size());
        assertEquals(2, superSorter.getSubjects().size());
    }

    @Test
    public void testOfSortingOnPriority() throws SQLException, ParseException {
        superSorter.dataCollect();
        LinkedHashSet<Cell> prioritized = (LinkedHashSet) superSorter.prioritySort(superSorter.getPrioritizedSchedule());
        ArrayList<Cell> prioAsList = new ArrayList<>(prioritized);
        assertEquals(2, prioAsList.get(prioritized.size()-1).getSlotPriority());
        if(!superSorter.getEvents().isEmpty()){
            assertEquals(true, prioAsList.get(0).getSlotPriority() > 90);
        }
        assertEquals(true, prioAsList.get(0).getSlotPriority() > prioAsList.get(1).getSlotPriority());
    }

    @Test
    public void testOfRemovingDeadlines() throws SQLException, ParseException {
        superSorter.dataCollect();
        superSorter.pickOutDeadlines(superSorter.getPrioritizedSchedule());
        if(!superSorter.getDeadlines().isEmpty()){
            superSorter.applyDeadlines();
        }
    }

    @After
    public void tearDown() throws Exception {
        connect.deleteActivity(dummy1);
        connect.deleteActivity(dummy2);
        connect.deleteActivity(dummy3);
        connect.deleteActivity(dummy4);
        connect.deleteActivity(dummy5);

        connect.removeStudentSubject("TDT4100");
        connect.removeStudentSubject("TDT4145");

        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
    }

}