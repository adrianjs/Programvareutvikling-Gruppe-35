package database;

import layout.User;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by Henning on 18.04.2017.
 */
public class TeacherTest {
    Connect connect;
    @Before
    public void setUp() throws Exception {
        User.getInstance().setUsername("testTeach@test.com");
        connect = new Connect();
        connect.addTeacher("testTeach@test.com", "test", "test", "test", "test", "test");
    }





    @After
    public void tearDown() throws Exception {
        connect.stmt.execute("DELETE FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        connect.close();
    }

}