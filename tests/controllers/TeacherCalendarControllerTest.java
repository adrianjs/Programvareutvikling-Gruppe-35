package controllers;

import com.jfoenix.controls.JFXDrawer;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class TeacherCalendarControllerTest {

    TeacherCalendarController con;

    @Before
    public void setup(){
        new JFXPanel();
        con = new TeacherCalendarController();

        con.drawer  = new JFXDrawer();
        con.drawer2 = new JFXDrawer();
        con.add          = new AnchorPane();
        con.feedback     = new AnchorPane();
        con.addTsbubject = new AnchorPane();
        con.userName         = new Label();
        con.mainButtons      = new Group();
        con.topButtons       = new Group();
        con.topBox           = new HBox();
        con.teachingSubjects = new Label();
        con.centerPane = new AnchorPane();
        con.barPane = new AnchorPane();

    }

    @Test
    public void addEventFeedBackTeachingSubject(){

        con.addEvent(); // 1
        assertEquals(con.drawer.getSidePane().get(0), con.add);
        assertEquals(true, con.topButtons.isVisible());
        assertEquals(false, con.mainButtons.isVisible());
        assertEquals(false, con.userName.isVisible());

        con.feedback(); // 2
        assertEquals(con.drawer.getSidePane().get(0), con.feedback);
        assertEquals(true, con.topButtons.isVisible());
        assertEquals(false, con.mainButtons.isVisible());
        assertEquals(false, con.userName.isVisible());

        con.addTeachingSubject();//3
        assertEquals(con.drawer.getSidePane().get(0), con.addTsbubject);
        assertEquals(true, con.topButtons.isVisible());
        assertEquals(false, con.mainButtons.isVisible());
        assertEquals(false, con.userName.isVisible());


    }

    @Test
    public void testCancel(){
        con.cancel();
        assertEquals(false, con.topButtons.isVisible());
        assertEquals(true, con.mainButtons.isVisible());
        assertEquals(true, con.userName.isVisible());

    }

    @Test
    public void testSnack(){
        con.snack(0, "test");
        assertEquals("1", con.bar.getId());
        con.snack(2, "test");
        assertEquals("2", con.bar.getId());
        con.snack(3, "test");
        assertEquals("3", con.bar.getId());
        con.snack(4, "test");
        assertEquals("4", con.bar.getId());

    }




}
