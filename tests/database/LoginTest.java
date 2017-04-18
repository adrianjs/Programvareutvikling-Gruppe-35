package database;

import layout.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Henning on 10.03.2017.
 */
public class LoginTest {
    Connect connect;

    @Before
    public void setup(){
        //TODO: Make a new User so that we can test getter methods for.
        User.getInstance().setUsername("testStud@test.com");
        connect = new Connect();
        connect.addStudent("testStud@test.com", "test", "test", "test", 1, "test");
        connect.addTeacher("testTeach@test.com", "test", "test", "test", "test", "test");
    }

    @Test
    public void checkIfDBContainsUser() throws SQLException {
        boolean contains = false;
        Login login = new Login();
        Set<ArrayList> students = login.getStudent();
        Set<ArrayList> teachers = login.getCourseCoordinator();
        for(ArrayList list : students){
            if(list.get(0).equals(User.getInstance().getUsername())){
                contains = true;
            }
        }

        assertEquals(true, contains);
        contains = false;
        User.getInstance().setUsername("testTeach@test.com");
        for(ArrayList list : teachers){
            if(list.get(0).equals(User.getInstance().getUsername())){
                contains = true;
            }
        }

        assertEquals(true, contains);
    }


    @After
    public void breakDown() throws SQLException {
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
        connect.stmt.execute("DELETE FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        connect.close();
    }

}