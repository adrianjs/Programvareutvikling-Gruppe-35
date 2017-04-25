package controllers;

import calendar.Activity;
import calendar.Event;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import layout.User;
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
    User user;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();


    @Before
    public void setUp() throws Exception {
        new JFXPanel();
        con = new CalendarController();
        date = new Date();
        datel = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        user.getInstance().setUsername("torres.lande@gmail.com");
        con.username = new Label();
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
        con.clearTimeSlots();
        assertEquals(0, con.dayTabLabels.size());
        assertEquals(0, con.cellsAtCurrentDate.size());
        assertEquals(0, con.dayTabLabels.size());
        assertEquals(0, con.labelMappedCells.size());
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
        con.setDate();
        assertEquals(date.getDate(), con.getChosenDate().getDate());
    }

    @Test
    public void testAddLabelsToList(){
        con.addLabelsToList();
    }

    @Test
    public void testMapMonthTab(){
        con.mapMonthTab();
    }

    @Test // ikke ferdig
    public void testInsertCells(){
        // cells ligger cellene som skal settes inn arrayList med celler
        //skal legges inn i day
        con.weekCalendarList.add(datel);
        con.mapMonthTab();


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

    @Test
    public void testMonthClicked(){
        Tab dayTab = con.dayTab;
        Date testDate = con.getChosenDate();

        for( int i = 1; i <= 37 ; i++){
            Label testLabel = new Label();
            testLabel.setText(""+i);
            con.monthLabels.add(testLabel);
            con.monthClicked(i);
        }

        assertEquals(false, con.monthTab.isSelected());
        assertEquals(false, con.weekTab.isSelected());
        assertEquals(false, con.dayTab.isSelected());

        con.handleCalendarClick1();
        con.handleCalendarClick2();
        con.handleCalendarClick3();
        con.handleCalendarClick4();
        con.handleCalendarClick5();
        con.handleCalendarClick6();
        con.handleCalendarClick7();
        con.handleCalendarClick8();
        con.handleCalendarClick9();
        con.handleCalendarClick10();
        con.handleCalendarClick11();
        con.handleCalendarClick12();
        con.handleCalendarClick13();
        con.handleCalendarClick14();
        con.handleCalendarClick15();
        con.handleCalendarClick16();
        con.handleCalendarClick17();
        con.handleCalendarClick18();
        con.handleCalendarClick19();
        con.handleCalendarClick20();
        con.handleCalendarClick21();
        con.handleCalendarClick22();
        con.handleCalendarClick23();
        con.handleCalendarClick24();
        con.handleCalendarClick25();
        con.handleCalendarClick26();
        con.handleCalendarClick27();
        con.handleCalendarClick28();
        con.handleCalendarClick29();
        con.handleCalendarClick30();
        con.handleCalendarClick31();
        con.handleCalendarClick32();
        con.handleCalendarClick33();
        con.handleCalendarClick34();
        con.handleCalendarClick35();
        con.handleCalendarClick36();
        con.handleCalendarClick37();
    }


    @Test public void testNext(){

    }






    @Test
    public void testInitialized(){
        con.initialize(null,null);
    }

}
