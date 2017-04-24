package controllers.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import controllers.CalendarController;
import database.Connect;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import layout.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import static org.junit.Assert.*;


/**
 * Created by torresrl on 20/04/2017.
 */
public class AddSubjectControllerTest {

    AddSubjectController con;
    User user;
    Connect connect;
    CalendarController CC;

    @Before
    public void setup() {
        new JFXPanel();
        con = new AddSubjectController();
        connect = new Connect();
        connect.addStudent("testStud@test.com", "test", "test", "test", 1,
                "test");
        con.subject = new TextField();
        user = User.getInstance();
        user.setUsername("testStud@test.com");
        CC = CalendarController.getInstance();
        con.subjectPicker = new JFXComboBox();
        con.anchorPane = new AnchorPane();
    }

    @Test
    public void testAddAndRemoveSubjectToCalendar()throws SQLException{
        con.subject.setText("AAR4335");
        con.addSubjectToCalendar();
        assertEquals("3",con.bar.getId());
        con.subjectPicker.getItems().add("AAR4335");
        con.addSubjectToCalendar();
        assertEquals("2",con.bar.getId());
        con.addSubjectToCalendar();
        assertEquals("1",con.bar.getId());
        con.removeSubject();
        assertEquals("4",con.bar.getId());
    }

    @After
    public void close() throws SQLException {
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");
    }
}
