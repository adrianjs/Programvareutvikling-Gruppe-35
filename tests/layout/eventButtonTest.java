package layout;

import calendar.Cell;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


import java.util.Date;
import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class eventButtonTest {

    eventButton eb;
    Cell cell;
    Date date;
    Button event1;

    @Before
    public void setup(){
        new JFXPanel();
        date = new Date();
        cell = new Cell(date, date, "13", "14", "test", "detter er en test",
                99, false, 333, "F44336");
        eb = new eventButton(cell.getName(), cell.getDescription(), cell);
        event1 = eb.getEvent();
        eb = new eventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);


    }

    //@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
    //sjekk ut for testing av javafx
    //http://andrewtill.blogspot.no/2012/10/junit-rule-for-javafx-controller-testing.html



    @Test
    public void testGetName(){
        assertEquals("test", eb.getName());


    }

    @Test
    public void testGetDescription(){
        assertEquals("detter er en test", eb.getDescription());

    }

    @Test
    public void testEventType(){
        assertEquals("exam", eb.eventType);
        cell.setSlotPriority(98);
        eb = new eventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("deadline", eb.eventType);
        cell.setSlotPriority(97);
        eb = new eventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("home work", eb.eventType);
        cell.setSlotPriority(96);
        eb = new eventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("lecture", eb.eventType);
        cell.setSlotPriority(95);
        eb = new eventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("home exam", eb.eventType);
    }








}
