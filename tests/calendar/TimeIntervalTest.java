package calendar;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Henning on 02.03.2017.
 */
public class TimeIntervalTest {
    TimeInterval ti = new TimeInterval(new Date(4), new Date(5));

    @Test
    public void endIsAfterStart() throws Exception{
        assertEquals(true, ti.getStartTime().before(ti.getEndTime()));
    }
}