package layout;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class testFeedbackElement {

    FeedbackElement FBE;


    @Before
    public void setup() {
        new JFXPanel();

        FBE = new FeedbackElement("11.04.1994", "testE", "testA", "testD");
    }


    @Test
    public void testGetAvg(){
        assertEquals("testA", FBE.getAvg());
    }

    @Test
    public void testGetDate(){
        assertEquals("11.04.1994", FBE.getDate());
    }

    @Test
    public void testGetDescription(){
        assertEquals("testD", FBE.getDescription());
    }

    @Test
    public void testGetEstimate(){
        assertEquals("testE", FBE.getEstimate());
    }

    @Test
    public void testsetAvg(){
        FBE.setAvg("testAA");
        assertEquals("testAA", FBE.getAvg());
    }

    @Test
    public void testSetDate(){
        FBE.setDate("11.04");
        assertEquals("11.04", FBE.getDate());
    }

    @Test
    public void testSetDescription(){
        FBE.setDescription("testDD");
        assertEquals("testDD", FBE.getDescription());
    }

    @Test
    public void testSetEstimate(){
        FBE.setEstimate("testEE");
        assertEquals("testEE", FBE.getEstimate());
    }

}
