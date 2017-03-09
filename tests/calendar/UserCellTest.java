package calendar;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Henning on 02.03.2017.
 */
public class UserCellTest {
    UserCell cell = new UserCell(new Date(), new Date(), "Svømming", "Skal svømme med Lars.", 5, false);
    @Test
    public void getStartDate() throws Exception {
        //Tests if it's the same classes.
        assertEquals(new Date().getClass(), cell.getStartDate().getClass());
        System.out.println(cell.getStartDate());
    }

    @Test
    public void setStartDate() throws Exception {
        //Tests to see if a new date is set.
        Date prev = cell.getStartDate();
        cell.setStartDate(new Date(12));
        assertNotEquals(prev, cell.getStartDate());
    }

    @Test
    public void getEndDate() throws Exception {
        assertEquals(new Date().getClass(), cell.getEndDate().getClass());
        System.out.println(cell.getEndDate());
    }

    @Test
    public void setEndDate() throws Exception {
        Date prev = cell.getEndDate();
        cell.setEndDate(new Date(20));
        assertNotEquals(prev, cell.getEndDate());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Svømming", cell.getName());
    }

    @Test
    public void setName() throws Exception {
        String prev = cell.getName();
        cell.setName("Klatring");
        assertNotEquals(prev, cell.getName());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("Skal svømme med Lars.", cell.getDescription());
    }

    @Test
    public void setDescription() throws Exception {
        String prev = cell.getDescription();
        cell.setDescription("Klatring er mye tøffere!");
        assertNotEquals(prev, cell.getDescription());
    }

    @Test
    public void getSlotPriority() throws Exception {
        assertEquals(5, cell.getSlotPriority());
    }

    @Test
    public void setSlotPriority() throws Exception {
        int prev = cell.getSlotPriority();
        cell.setSlotPriority(3);
        assertNotEquals(prev, cell.getSlotPriority());
    }

    @Test
    public void isRepeating() throws Exception {
        assertEquals(false, cell.isRepeating());
    }

    @Test
    public void setRepeating() throws Exception {
        boolean prev = cell.isRepeating();
        cell.setRepeating(true);
        assertNotEquals(prev, cell.isRepeating());
    }

}