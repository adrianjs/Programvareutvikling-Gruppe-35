package controllers.add;

import com.jfoenix.controls.*;

import database.Activity;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */
public class AddActivityControllerTest {
    private AddActivityController AAC;
    private Date date;
    private LocalDate testDate;
    private LocalDateTime testDateTime;
    private LocalTime testTime;
    private Activity database;


    





    @Before
    public void setup(){
        new JFXPanel();
        date = new Date();
        testDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        AAC = new AddActivityController();
        testDateTime = LocalDateTime.of(2017, 4,11,12,00);
        testTime = testDateTime.toLocalTime();
        //AAC.errorActivity = new Label();
        //AAC.errorDate = new Label();
        AAC.errorTime = new Label();
        //AAC.priorityError = new Label();
        AAC.date = new JFXDatePicker();
        AAC.activity = new JFXTextField(" hei");
        AAC.startTime = new JFXDatePicker();
        AAC.endTime = new JFXDatePicker();
        AAC.priority = new JFXComboBox();
       // database = new Activity();
        AAC.everyWeek = new JFXCheckBox();
        AAC.desc = new JFXTextField();
       

    }


    @Test
    public void testChectActivity() throws Exception{

       assertEquals(true, AAC.checkActivity());
        AAC.activity = new JFXTextField("");
        assertEquals(false, AAC.checkActivity());
    }

    @Test
    public void checkDateTest(){
        assertEquals(false, AAC.checkDate());
        AAC.date.setValue(testDate);
        assertEquals(true, AAC.checkDate());
        AAC.date.setValue(testDate.plusDays(5));
        assertEquals(true, AAC.checkDate());
        AAC.date.setValue(testDate.minusDays(5));
        assertEquals(false, AAC.checkDate());


    }

    @Test
    public void checkTimeTest(){
        AAC.startTime.setValue(testDate);
        AAC.endTime.setValue(testDate);
        AAC.startTime.setTime(testTime);
        AAC.endTime.setTime(testTime.minusHours(1));
        assertEquals(false, AAC.checkTime());
        AAC.startTime.setTime(testTime.minusHours(9));
        assertEquals(false, AAC.checkTime());
        AAC.startTime.setTime(testTime.minusHours(2));
        assertEquals(true, AAC.checkTime());

    }

    @Test
    public void testCheckPriority(){
        assertEquals(false, AAC.checkPriority());
        AAC.priority.setValue(3);
        assertEquals(true, AAC.checkPriority());

    }

 /*   @Test
    public void testSendIn(){


        AAC.activity.setText("test");
        AAC.date.setValue(testDate);
        AAC.startTime.setValue(testDate);
        AAC.endTime.setValue(testDate);
        AAC.startTime.setTime(testTime.minusHours(2));
        AAC.endTime.setTime(testTime);
        AAC.priority.setValue(3);
        AAC.sendIn();
        ArrayList lastAdded = database.getLastActivity();
        assertEquals("test",lastAdded.get(1));
        database.deleteActivity((int) lastAdded.get(0));
    }

    @After
    public void close(){
        //database.close();
    }
*/

}









