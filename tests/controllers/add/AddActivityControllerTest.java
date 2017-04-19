package controllers.add;

import com.jfoenix.controls.*;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */
public class AddActivityControllerTest {
    private AddActivityController AAC;
    private Date date;
    private LocalDate testDate;
    private LocalDateTime testTime;




    @Before
    public void setup(){
        new JFXPanel();
        date = new Date();
        testDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        AAC = new AddActivityController();
        testTime = LocalDateTime.now(ZoneId.of("UTC"));


    }


    @Test
    public void testChectActivity() throws Exception{

        AAC.activity = new JFXTextField(" hei");
       assertEquals(true, AAC.checkActivity());
    }

    @Test
    public void checkDateTest(){
        AAC.date = new JFXDatePicker();
        AAC.date.setValue(testDate);
        assertEquals(true, AAC.checkDate());
        AAC.date.setValue(testDate.plusDays(5));
        assertEquals(true, AAC.checkDate());


    }

    @Test
    public void checkTimeTest(){
        AAC.startTime = new JFXDatePicker();
        AAC.endTime = new JFXDatePicker();
        AAC.startTime.setValue(testDate);
        AAC.endTime.setValue(testDate);
        System.out.println(AAC.startTime.getTime());
        //assertEquals(false, AAC.checkTime());



    }
}
