package controllers;

import algorithm.Activity;
import algorithm.Event;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */
public class CalendarControllerTest {
    CalendarController con;
    Date date;
    LocalDate datel;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();



    @Before
    public void setUp() throws Exception {
        new JFXPanel();
        con = new CalendarController();
        date = new Date();
        datel = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();



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

        con.monday    = new Label();
        con.tuesday   = new Label();
        con.wednesday = new Label();
        con.thursday  = new Label();
        con.friday    = new Label();
        con.saturday  = new Label();
        con.sunday    = new Label();

        con.date = new JFXDatePicker();
        con.thisday = new Label();









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
        test = con.removeZero("123");
        assertEquals(123, test);

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



    @Test
    public void testAskBotto(){
        assertEquals(con.askButton.getScene(), con.getScene());

    }

    @Test
    public void testTimeLayout(){

        con.weekCalendarList.clear();
        con.timeLayout();


        assertEquals("-fx-background-color: white;", con.monday.getStyle());
        assertEquals("-fx-background-color: white;", con.tuesday.getStyle());
        assertEquals("-fx-background-color: white;", con.wednesday.getStyle());
        assertEquals("-fx-background-color: white;", con.thursday.getStyle());
        assertEquals("-fx-background-color: white;", con.friday.getStyle());
        assertEquals("-fx-background-color: white;", con.saturday.getStyle());
        assertEquals("-fx-background-color: white;", con.sunday.getStyle());

    }


    @Test
    public void testSetDate(){
        Date date = new Date();
        assertEquals(date.getDate(), con.getChosenDate().getDate());


    }


    @Test
    public void testInsertCells(){
        // cells ligger cellene som skal settes inn arrayList med celler
        //skal legges inn i day
        con.weekCalendarList.add(datel);


        Event cell = new Event(date, date, "13", "14", "test", "detter er en test",
                98, false, 333,"TTT4140", 999, "F44336");

        Activity activity = new Activity(date, date, "15", "16", "test", "detter er en test",
                5, false,998, "F44336");
        con.cellsAtCurrentDate.add(cell);
        con.cellsAtCurrentDate.add(activity);
        con.cells.add(cell);
        con.cells.add(activity);
        con.insertCells();
        assertEquals(true, con.day.getChildren().size() == 2);


    }
}
