package algorithm;

import calendar.Cell;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Henning on 18.04.2017.
 */
public class PriorityComparatorTest {
    Cell dummyCell1 = new Cell(new Date(), new Date(), "10", "10", null, null, 3, false, 0, null);
    Cell dummyCell2 = new Cell(new Date(), new Date(), "10", "10", null, null, 5, false, 0, null);

    @Test
    public void testOfPriorityComparator(){
        PriorityComparator pc = new PriorityComparator();
        assertEquals(1, pc.compare(dummyCell1, dummyCell2));
    }

}