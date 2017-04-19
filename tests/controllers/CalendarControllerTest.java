package controllers;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */
public class CalendarControllerTest {
    CalendarController con;
    Date date;

    @Before
    public void setUp() throws Exception {
        con = new CalendarController();
        date = new Date();

    }

    @Test
    public void testCurrentDate(){
        assertEquals(date.getMinutes(), con.getChosenDate().getMinutes());
        assertEquals(date.getHours(), con.getChosenDate().getHours());

    }


}
