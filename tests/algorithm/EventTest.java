package algorithm;

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
        this.event = new Event("TEST", new Date(), new Date(),
                "11", "12", 0, 20,
                "Test description", 3, "TDT4100");
    }

    @Test
    public void setName() throws Exception {
        String origName = this.event.getName();
        this.event.setName("Other than TEST");
        assertEquals("Other than TEST", this.event.getName());
        assertNotEquals(origName, this.event.getName());
    }

    @Test
    public void setStartDate() throws Exception {
        Date origDate = this.event.getStartDate();
        this.event.setStartDate(new Date());
        assertNotEquals(origDate.getTime(), this.event.getStartDate());
    }

    @Test
    public void setEndDate() throws Exception {
        Date origDate = this.event.getEndDate();
        this.event.setEndDate(new Date());
        assertNotEquals(origDate.getTime(), this.event.getEndDate());
    }

    @Test
    public void setStartTime() throws Exception {
        String origTime = this.event.getStartTime();
        this.event.setStartTime("09");
        assertEquals("09", this.event.getStartTime());
        assertNotEquals(origTime, this.event.getStartTime());
    }

    @Test
    public void setEndTime() throws Exception {
        String origTime = this.event.getEndTime();
        this.event.setEndTime("15");
        assertEquals("15", this.event.getEndTime());
        assertNotEquals(origTime, this.event.getEndTime());
    }

    @Test
    public void setRepeating() throws Exception {
        int origRepeat = this.event.getRepeating();
        this.event.setRepeating(1);
        assertEquals(1, this.event.getRepeating());
        assertNotEquals(origRepeat, this.event.getRepeating());
    }

    @Test
    public void setPriority() throws Exception {
        int origPrio = this.event.getPriority();
        this.event.setPriority(50);
        assertEquals(50, this.event.getPriority());
        assertNotEquals(origPrio, this.event.getPriority());
    }

    @Test
    public void setDescription() throws Exception {
        String origDesc = this.event.getDescription();
        this.event.setDescription("Some other description");
        assertEquals("Some other description", this.event.getDescription());
        assertNotEquals(origDesc, this.event.getDescription());
    }

    @Test
    public void setHoursOfWork() throws Exception {
        int origHours = this.event.getHoursOfWork();
        this.event.setHoursOfWork(6);
        assertEquals(6, this.event.getHoursOfWork());
        assertNotEquals(origHours, this.event.getName());
    }

    @Test
    public void setSubjectCode() throws Exception {
        String origCode = this.event.getSubjectCode();
        this.event.setSubjectCode("IT2509");
        assertEquals("IT2509", this.event.getSubjectCode());
        assertNotEquals(origCode, this.event.getSubjectCode());
    }

}