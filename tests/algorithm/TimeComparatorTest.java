package algorithm;

import calendar.Cell;
import org.junit.Test;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by Henning on 18.04.2017.
 */
public class TimeComparatorTest {
    Cell dummy1 = new Cell(new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), "09", "14", null, null, 3, false, 0, null);
    Cell dummy2 = new Cell(new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), "13", "16", null, null, 3, false, 0, null);
    Cell dummy3 = new Cell(new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), "17", "19", null, null, 3, false, 0, null);
    Cell dummy4 = new Cell(new java.util.Date(), new java.util.Date(), "18", "19", null, null, 3, false, 0, null);

    @Test
    public void testOfOverlapingDatesAndNonOverlapingDates(){
        TimeComparator tc = new TimeComparator();
        assertEquals(true, tc.compare(dummy1, dummy2));
        assertEquals(false, tc.compare(dummy2, dummy3));
        assertEquals(true, tc.compare(dummy3, dummy4));
    }

}