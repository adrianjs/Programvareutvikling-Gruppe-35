package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import com.jfoenix.controls.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */
public class CalendarControllerTest {
    CalendarController con;
    Date date;


    @Before
    public void setUp() throws Exception {
        new JFXPanel();
        con = new CalendarController();
        date = new Date();

        con.askButton = new JFXButton();
        con.logout    = new JFXButton();
        con.remove    = new JFXButton();
        con.dayTab     = new Tab();
        con.weekTab    = new Tab();
        con.monthTab   = new Tab();
        con.tabs = new JFXTabPane();

        con.day = new GridPane();
        con.week = new GridPane();
        con.month = new GridPane();

    }

    @Test
    public void testCurrentDate(){
        assertEquals(date.getMinutes(), con.getChosenDate().getMinutes());
        assertEquals(date.getHours(), con.getChosenDate().getHours());

    }

    @Test
    public void testRemoveZero(){
        int test = con.removeZero("01");
        assertEquals(1, test);
    }

    @Test
    public void testSetLines(){
        con.setLines();
        assertEquals(true, con.day.isVisible());
        assertEquals(true, con.week.isVisible());
        assertEquals(true, con.month.isVisible());
    }

    @Test
    public void testClearTimeSlot(){
        Label testL1 = new Label();
        Label testL2 = new Label();
        testL1.setText("hei");
        testL2.setText("hei");
        con.timeToTime.add(testL1);
        con.timeToTime.add(testL2);
        con.clearTimeSlot(0);
        con.clearTimeSlot(1);
        for(Label testL : con.timeToTime){
            assertEquals("", testL.getText());
        }
    }




}
