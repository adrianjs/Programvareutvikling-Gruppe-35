package database;

import layout.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Henning on 02.03.2017.
 */
public class FetcherTest {
    private Fetcher fetcher;
    @Before
    public void setup(){
        User user = User.getInstance();
        user.setUsername("larsmade@stud.ntnu.no");
        fetcher = new Fetcher("SELECT * FROM ACTIVITY");
    }
    @Test
    public void testGetAllActivitiesFromDatabase() throws SQLException {
        Set<List> results = fetcher.getUserRelatedResults(5); //Trying to get the first 5 columns
        assertNotEquals(new HashSet<List>(), results); //Checks that the set is not empty
    }

}