package layout;

import calendar.Cell;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class MonthEventButtonTest {

    MonthEventButton meb;
    Cell cell;
    Date date;

    @Before
    public void setup() {
        new JFXPanel();
        date = new Date();
        cell = new Cell(date, date, "13", "14", "test", "detter er en test",
                99, false, 333, "F44336");

        meb = new MonthEventButton(cell);
    }

    @Test
    public void testGetButton(){
        assertEquals(meb.btn.getText(), meb.getEvent().getText());
    }
}
