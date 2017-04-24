package layout;

import calendar.Cell;
import controllers.JavaFXThreadingRule;
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
public class EventButtonTest {

    EventButton eb;
    Cell cell;
    Date date;
    Button event1;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setup(){
        new JFXPanel();
        date = new Date();
        cell = new Cell(date, date, "13", "14", "test", "detter er en test",
                99, false, 333, "F44336");
        eb = new EventButton(cell.getName(), cell.getDescription(), cell);
        event1 = eb.getEvent();
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
    }

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
        assertEquals("Exam", eb.eventType);
        cell.setSlotPriority(98);
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("Deadline", eb.eventType);
        cell.setSlotPriority(97);
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("home work", eb.eventType);
        cell.setSlotPriority(96);
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("Lecture", eb.eventType);
        cell.setSlotPriority(95);
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        assertEquals("Home exam", eb.eventType);
    }

    @Test
    public void testEventOnAction(){
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        eb.event = new Button();
        eb.event.fire();
        assertEquals(cell.getName(), eb.getName());
        assertEquals(cell.getDescription(), eb.getDescription());
    }

    @Test
    public void testHomeWorkOnAction(){
        cell.setSlotPriority(97);
        eb = new EventButton(cell.getName(), cell.getDescription(), "TDT4140", cell, 333);
        eb.event = new Button();
        eb.event.fire();
        assertEquals(cell.getName(), eb.getName());
        assertEquals(cell.getDescription(), eb.getDescription());
    }

    @Test
    public void testActivityOnAction(){
        eb = new EventButton(cell.getName(), cell.getDescription(), cell);
        eb.event = new Button();
        eb.event.fire();
        assertEquals(cell.getName(), eb.getName());
        assertEquals(cell.getDescription(), eb.getDescription());
    }
}
