package calendar;

import calendar.Activity;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Henning on 02.03.2017.
 */
public class ActivityTest {
    String [] colors = {"F44336","E91E63","9C27B0","673AB7","3F51B5","2196F3","03A9F4","009688"};
    int randomNum = ThreadLocalRandom.current().nextInt(0,  7);

    Activity cell = new Activity(new Date(), new Date(), "12", "14","Svømming", "Skal svømme med Lars.", 5, false, 0, colors[randomNum]);

    @Test
    public void testConstructNewActivityFromActivity(){
        Activity activity = new Activity(cell);
        assertEquals(cell.getName(), activity.getName());
    }

    @Test
    public void testGetStartDate() throws Exception {
        //Tests if it's the same classes.
        assertEquals(new Date().getClass(), cell.getStartDate().getClass());
        System.out.println(cell.getStartDate());
    }

    @Test
    public void testSetStartDate() throws Exception {
        //Tests to see if a new date is set.
        Date prev = cell.getStartDate();
        cell.setStartDate(new Date(12));
        assertNotEquals(prev, cell.getStartDate());
    }

    @Test
    public void testGetEndDate() throws Exception {
        assertEquals(new Date().getClass(), cell.getEndDate().getClass());
        System.out.println(cell.getEndDate());
    }

    @Test
    public void testSetEndDate() throws Exception {
        Date prev = cell.getEndDate();
        cell.setEndDate(new Date(20));
        assertNotEquals(prev, cell.getEndDate());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Svømming", cell.getName());
    }

    @Test
    public void testSetName() throws Exception {
        String prev = cell.getName();
        cell.setName("Klatring");
        assertNotEquals(prev, cell.getName());
    }

    @Test
    public void testGetDescription() throws Exception {
        assertEquals("Skal svømme med Lars.", cell.getDescription());
    }

    @Test
    public void testSetDescription() throws Exception {
        String prev = cell.getDescription();
        cell.setDescription("Klatring er mye tøffere!");
        assertNotEquals(prev, cell.getDescription());
    }

    @Test
    public void testGetSlotPriority() throws Exception {
        assertEquals(5, cell.getSlotPriority());
    }

    @Test
    public void testSetSlotPriority() throws Exception {
        int prev = cell.getSlotPriority();
        cell.setSlotPriority(3);
        assertNotEquals(prev, cell.getSlotPriority());
    }

    @Test
    public void testIsRepeating() throws Exception {
        assertEquals(false, cell.isRepeating());
    }

    @Test
    public void testSetRepeating() throws Exception {
        boolean prev = cell.isRepeating();
        cell.setRepeating(true);
        assertNotEquals(prev, cell.isRepeating());
    }

    @Test
    public void testGetType(){
        assertEquals("activity", cell.getType());
    }

    @Test
    public void testGetAndSetId(){
        assertEquals(0, cell.getID());
        cell.setID(1);
        assertEquals(1, cell.getID());
    }

    @Test
    public void testGetColor(){
        String color = cell.getColor();
        boolean colorFound = false;
        if(Arrays.asList(colors).contains(color)){
            colorFound = true;
        }
        assertEquals(true, colorFound);
    }
}