package calendar;

import calendar.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Henning on 20.03.2017.
 */
public class EventTest {
    private Event event;
    @Before
    public void setUp() throws Exception {

        //Setting up a fake event that will be altered.
        this.event = new Event(new Date(), new Date(),
                "11", "12","TEST", "Test description", 20,
                false, 3, "TDT4100", 0, "009688");
    }

    @Test
    public void testConstructNewEventFromEvent(){
        Event event = new Event(this.event);
        assertEquals(this.event.getName(), event.getName());
    }

    @Test
    public void testSetName() throws Exception {
        String origName = this.event.getName();
        this.event.setName("Other than TEST");
        assertEquals("Other than TEST", this.event.getName());
        assertNotEquals(origName, this.event.getName());
    }

    @Test
    public void testSetStartDate() throws Exception {
        Date origDate = this.event.getStartDate();
        this.event.setStartDate(new Date());
        assertNotEquals(origDate.getTime(), this.event.getStartDate());
    }

    @Test
    public void testSetEndDate() throws Exception {
        Date origDate = this.event.getEndDate();
        this.event.setEndDate(new Date());
        assertNotEquals(origDate.getTime(), this.event.getEndDate());
    }

    @Test
    public void testSetStartTime() throws Exception {
        String origTime = this.event.getStartTime();
        this.event.setStartTime("09");
        assertEquals("09", this.event.getStartTime());
        assertNotEquals(origTime, this.event.getStartTime());
    }

    @Test
    public void testSetEndTime() throws Exception {
        String origTime = this.event.getEndTime();
        this.event.setEndTime("15");
        assertEquals("15", this.event.getEndTime());
        assertNotEquals(origTime, this.event.getEndTime());
    }

    @Test
    public void testSetRepeating() throws Exception {
        boolean origRepeat = this.event.isRepeating();
        this.event.setRepeating(true);
        assertEquals(true, this.event.isRepeating());
        assertNotEquals(origRepeat, this.event.isRepeating());
    }

    @Test
    public void testSetPriority() throws Exception {
        int origPrio = this.event.getSlotPriority();
        this.event.setSlotPriority(50);
        assertEquals(50, this.event.getSlotPriority());
        assertNotEquals(origPrio, this.event.getSlotPriority());
    }

    @Test
    public void testSetDescription() throws Exception {
        String origDesc = this.event.getDescription();
        this.event.setDescription("Some other description");
        assertEquals("Some other description", this.event.getDescription());
        assertNotEquals(origDesc, this.event.getDescription());
    }

    @Test
    public void testSetHoursOfWork() throws Exception {
        int origHours = this.event.getHoursOfWork();
        this.event.setHoursOfWork(6);
        assertEquals(6, this.event.getHoursOfWork());
        assertNotEquals(origHours, this.event.getName());
    }

    @Test
    public void testSetSubjectCode() throws Exception {
        String origCode = this.event.getSubjectCode();
        this.event.setSubjectCode("IT2509");
        assertEquals("IT2509", this.event.getSubjectCode());
        assertNotEquals(origCode, this.event.getSubjectCode());
    }

}