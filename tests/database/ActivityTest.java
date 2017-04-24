package database;

import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by torresrl on 17/04/2017.
 */
public class ActivityTest {
    //TODO hvorfor virker kun add med larsmade@stud.ntnu.no?

    @Test
    public void testAddAndRemove(){

        ArrayList lastAddedActivity;
        int IdTestActivity;
        Date date = new Date(2017-04-17);
        Connect add = Connect.getInstance();
        add.addActivity("Activity test", date, false, 1, 13.0, 14.0, "larsmade@stud.ntnu.no",
                "denne activiteten er kun for testting hvis den ligger i databasen har noe gale skjedd og den skal slettes");

        Activity getTest = new Activity();
        lastAddedActivity = getTest.getLastActivity();
        assertEquals("Activity test", lastAddedActivity.get(1));
        assertEquals(0, lastAddedActivity.get(3));
        assertEquals(13.0, lastAddedActivity.get(4));
        assertEquals(14.0, lastAddedActivity.get(5));
        assertEquals(1, lastAddedActivity.get(6));
        assertEquals("larsmade@stud.ntnu.no", lastAddedActivity.get(7));
        assertEquals("denne activiteten er kun for testting hvis den ligger i databasen har noe gale skjedd " +
                "og den skal slettes", lastAddedActivity.get(8));
        IdTestActivity = (int) lastAddedActivity.get(0);

        getTest.deleteActivity(IdTestActivity);
        lastAddedActivity = getTest.getLastActivity();
        assertNotEquals(IdTestActivity, lastAddedActivity.get(0));
    }
}
