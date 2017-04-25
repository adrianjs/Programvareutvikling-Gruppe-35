package algorithm;

import calendar.Activity;
import calendar.Cell;
import calendar.Event;
import controllers.JavaFXThreadingRule;
import database.Connect;
import database.Teacher;
import javafx.scene.control.Alert;
import layout.*;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Calendar;

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
    Event eventDummy1;
    Event eventDummy2;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {
        superSorter = new SuperSorter();
        connect = Connect.getInstance();

        connect.addStudent("testStud@test.com", "test", "test", "test", 1, "test");
        User.getInstance().setUsername("testStud@test.com");
        dummy1 = new Activity(new Date(), new Date(), "08", "10", "dummy1", "testAct", 2, false, 10, "FFFFFF");
        dummy2 = new Activity(new Date(), new Date(), "11", "12", "dummy2", "testAct", 3, false, 11, "FFFFFF");
        dummy3 = new Activity(new Date(), new Date(), "11", "14", "dummy3", "testAct", 4, false, 12, "FFFFFF");
        dummy4 = new Activity(new Date(), new Date(), "15", "18", "dummy4", "testAct", 3, false, 13, "FFFFFF");
        dummy5 = new Activity(new Date(), new Date(), "17", "20", "dummy5", "testAct", 3, false, 14, "FFFFFF");

        eventDummy2 = new Event(new Date(), new Date(), "08", "10", "eventDummy2", "Schoolwork", 96, false, 2, "TDT4100", 0, "FFFFFF");
        eventDummy1 = new Event(new Date(), new Date(), "12", "14", "eventDummy1", "testEvent", 90, false, 0, "TDT4100", 0, "FFFFFF");
        Teacher teacher = new Teacher();
        teacher.addSchoolWork(eventDummy2.getName(),
                eventDummy2.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                eventDummy2.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                eventDummy2.getStartTime(),
                eventDummy2.getEndTime(),
                eventDummy2.isRepeating() ? 1 : 0,
                eventDummy2.getDescription(),
                Double.parseDouble(String.valueOf(eventDummy2.getHoursOfWork())),
                eventDummy2.getSubjectCode());
        teacher.addLecture(eventDummy1.getName(),
                eventDummy1.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                eventDummy1.getStartTime(),
                eventDummy1.getEndTime(),
                eventDummy1.isRepeating() ? 1 : 0,
                eventDummy1.getDescription(),
                eventDummy1.getSubjectCode());

        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM EVENT WHERE name='eventDummy2'");
        m_result_set.next();
        eventDummy2.setID(m_result_set.getInt("eventID"));
        m_result_set = connect.stmt.executeQuery("SELECT * FROM EVENT WHERE name='eventDummy1'");
        m_result_set.next();
        eventDummy1.setID(m_result_set.getInt("eventID"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(eventDummy2.getStartDate());
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        eventDummy2.setStartDate(cal.getTime());
        cal.setTime(eventDummy1.getStartDate());
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        eventDummy1.setStartDate(cal.getTime());

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

    @Test
    public void testChangeTimeOfActivity() throws SQLException, ParseException {
        superSorter.changeTime(dummy1,
                dummy1.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));
        superSorter.dataCollect();
        LinkedHashSet<Cell> prioritized = (LinkedHashSet) superSorter.prioritySort(superSorter.getPrioritizedSchedule());
        ArrayList<Cell> prioAsList = new ArrayList<>(prioritized);
        assertEquals(2, prioAsList.get(prioAsList.size()-1).getSlotPriority());
        assertEquals("8", prioAsList.get(prioAsList.size()-1).getStartTime());
        assertEquals("9", prioAsList.get(prioAsList.size()-1).getEndTime());
        connect.stmt.executeUpdate("DELETE FROM ACTIVITY WHERE name='"+dummy1.getName()+"' AND date='" +(new SimpleDateFormat("yyyy-MM-dd").format(dummy1.getStartDate()))+
                "' AND startTime='8' AND endTime='9' AND studentEmail='"+User.getInstance().getUsername()+"'");
    }

    @Test
    public void testStringFormatterCell(){
        assertEquals("dummy1", superSorter.stringFormatterForCell(dummy1));
        assertEquals("eventDummy1 - TDT4100", superSorter.stringFormatterForCell(eventDummy1));
    }

    @Test
    public void testHandleUnprioritzedEvent() throws SQLException {
        superSorter.handleUnprioritizedEvent(eventDummy1);
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM NOTATTENDINGEVENT WHERE studentEmail='testStud@test.com' AND eventId='"+eventDummy1.getID()+"'");
        m_result_set.next();
        assertEquals(eventDummy1.getID(), m_result_set.getInt(1));
        assertEquals("testStud@test.com", m_result_set.getString(2));
    }

    @Test
    public void testGetLastPossibleDate(){
        Date date = superSorter.getLastPossibleDate(dummy1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dummy1.getStartDate());
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
        assertEquals(date, cal.getTime());
    }

    @Test
    public void testGetClosestLectureFromSchoolWork() throws SQLException {
        Date date = superSorter.getClosestLecture(eventDummy2);
        assertEquals(eventDummy1.getStartDate(), date);
    }

    @Test
    public void testDetectCollisionLocally(){
        Set<calendar.Cell> testCells = new LinkedHashSet<>();
        testCells.add(dummy2);
        superSorter.setScheduleWithoutCollision(testCells);
        assertEquals(false, superSorter.detectCollision(dummy3));
    }

    @Test
    public void testResetDroppedEventAndEvents() throws SQLException {
        superSorter.handleUnprioritizedEvent(eventDummy1);
        superSorter.handleUnprioritizedEvent(eventDummy2);
        //Reset one
        superSorter.resetDroppedEvent(eventDummy1);
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM NOTATTENDINGEVENT WHERE studentEmail='testStud@test.com'");
        List<Integer> rows = new ArrayList<>();
        while(m_result_set.next()){
            rows.add(m_result_set.getInt("eventID"));
        }
        assertEquals(1, rows.size());
        superSorter.handleUnprioritizedEvent(eventDummy1);
        //Reset all
        superSorter.resetDroppedEvents();
        m_result_set = connect.stmt.executeQuery("SELECT * FROM NOTATTENDINGEVENT WHERE studentEmail='testStud@test.com'");
        rows.clear();
        while(m_result_set.next()){
            rows.add(m_result_set.getInt("eventID"));
        }
        assertEquals(0, rows.size());
    }

//    @Test
//    public void testRescheduleAutomatic() throws SQLException, ParseException {
//        Activity originalDummy1 = new Activity(dummy1);
//        superSorter.findNewTime(dummy1);
//        superSorter.dataCollect();
//        assertNotEquals(originalDummy1.getStartTime(), dummy1.getStartTime());
//    }

//    @Test
//    public void testRescheduleManual() throws ParseException, SQLException, IOException {
//        //Choose another time than was before.
//        Alert alert = new Alert(Alert.AlertType.INFORMATION,
//                "Dette og det følgene vinduet hører til testing\nVelg en tid som ikke er starttid: 8 og slutttid: 10\n"+
//                        "f.eks startid: 11 og sluttid 13");
//        alert.showAndWait();
//        superSorter.setupDialog(dummy1);
//        superSorter.dataCollect();
//        Set<Cell> prioritized = superSorter.prioritySort(superSorter.getPrioritizedSchedule());
//        ArrayList<Cell> prioAsList = new ArrayList<>(prioritized);
//        assertNotEquals("8", prioAsList.get(prioAsList.size()-1).getStartTime());
//        assertNotEquals("10", prioAsList.get(prioAsList.size()-1).getEndTime());
//
//    }

//    @Test
//    public void testHandleSamePriority() throws ParseException, SQLException, IOException {
//        superSorter.handleSamePriority(dummy4, dummy5);
//        assertNotEquals(false, superSorter.getScheduleWithoutCollision().contains(dummy4));
//    }

    @After
    public void tearDown() throws Exception {
        connect.deleteActivity(dummy1);
        connect.deleteActivity(dummy2);
        connect.deleteActivity(dummy3);
        connect.deleteActivity(dummy4);
        connect.deleteActivity(dummy5);

        connect.removeStudentSubject("TDT4100");
        connect.removeStudentSubject("TDT4145");

        connect.stmt.execute("DELETE FROM NOTATTENDINGEVENT WHERE studentEmail='testStud@test.com'");
        connect.stmt.execute("DELETE FROM EVENT WHERE name='eventDummy1' OR name='eventDummy2'");
        connect.stmt.execute("DELETE FROM ACTIVITY WHERE studentEmail='testStud@test.com'");
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
    }

}