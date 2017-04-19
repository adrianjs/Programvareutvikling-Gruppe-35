package controllers.add;

import com.jfoenix.controls.*;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */
public class AddEventControllerTest {
    /*
    private Group mainGroup;
    private Group endDateGroup;
    private Group endTimeGroup;
    private Group repeatingGroup;
    private Group hoursOfWOrk;

    //Inputfields
    private final ToggleGroup group = new ToggleGroup();

    private JFXTextField eventName;

    private JFXRadioButton classRadio;
    private JFXRadioButton schoolWork;
    private JFXRadioButton deadline;
    private JFXRadioButton exam;
    private JFXRadioButton homeExam;



     private JFXTextField description;
     private JFXDatePicker startDate;
     private JFXDatePicker endDate;
     private JFXDatePicker startTime;
     private JFXDatePicker endTime;
     private JFXCheckBox repeating;
     private Label errorLabel;
     private JFXComboBox subjectsDropDown;
     private JFXTextField hours;
    */
     private AddEventController AEC;
     private Date date;
     private LocalDate testDate;
     private LocalDateTime testDateTime;
     private LocalTime testTime;

    @Before
    public void setup() {
        new JFXPanel();
        AEC = new AddEventController();

        AEC.mainGroup = new Group();
        AEC.endDateGroup = new Group();
        AEC.endTimeGroup = new Group();
        AEC.repeatingGroup = new Group();
        AEC.hoursOfWOrk = new Group();
        AEC.eventName = new JFXTextField();
        AEC.classRadio = new JFXRadioButton();
        AEC.schoolWork = new JFXRadioButton();
        AEC.deadline = new JFXRadioButton();
        AEC.exam = new JFXRadioButton();
        AEC.homeExam = new JFXRadioButton();
        AEC.description = new JFXTextField();
        AEC.startDate = new JFXDatePicker();
        AEC.endDate = new JFXDatePicker();
        AEC.startTime = new JFXDatePicker();
        AEC.endTime = new JFXDatePicker();
        AEC.errorLabel = new Label();
        AEC.subjectsDropDown = new JFXComboBox();
        AEC.hours = new JFXTextField();
        AEC.repeating = new JFXCheckBox();


        date = new Date();
        testDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        testDateTime = LocalDateTime.of(2017, 4,11,12,00);
        testTime = testDateTime.toLocalTime();





    }

    //TODO: husk og teste subject

    @Test
    public void testValidateEventName(){
        assertEquals(false, AEC.validateEventName());
        AEC.eventName.setText("test");
        assertEquals(true, AEC.validateEventName());

    }

    @Test
    public void testValidateDescription(){
        assertEquals(false, AEC.validateDescription());
        AEC.description.setText("test");
        assertEquals(true, AEC.validateDescription());

    }

    @Test
    public void testValidateSatartDate(){
        assertEquals(false, AEC.validateStartDate());
        AEC.startDate.setValue(testDate.minusDays(1));
        assertEquals(false, AEC.validateStartDate());
        AEC.startDate.setValue(testDate.plusDays(2));
        assertEquals(true, AEC.validateStartDate());

    }

    @Test
    public void testValidateEndDate(){
        assertEquals(false, AEC.validateEndDate());
        AEC.endDate.setValue(testDate.minusDays(1));
        assertEquals(false, AEC.validateEndDate());
        AEC.endDate.setValue(testDate.plusDays(2));
        assertEquals(true, AEC.validateEndDate());

    }

    @Test
    public void testValidateStartBeforeEnd() {
        AEC.startDate.setValue(testDate.plusDays(2));
        AEC.endDate.setValue(testDate);
        AEC.validateEndDate();
        AEC.validateStartDate();
        assertEquals(false, AEC.validateStartBeforeEnd());
        AEC.endDate.setValue(testDate.plusDays(2));
        AEC.startDate.setValue(testDate);
        assertEquals(true, AEC.validateEndDate());

    }

    @Test
    public void testValidateStartTime(){
        assertEquals(false, AEC.validateStartTime());
        AEC.startTime.setTime(testTime.minusHours(9));
        assertEquals(false, AEC.validateStartTime());
        AEC.startTime.setTime(testTime);
        assertEquals(true, AEC.validateStartTime());

    }

    @Test
    public void testValidateEndTime(){
        assertEquals(false, AEC.validateeEndTime());
        AEC.endTime.setTime(testTime.minusHours(9));
        assertEquals(false, AEC.validateeEndTime());
        AEC.endTime.setTime(testTime);
        assertEquals(true, AEC.validateeEndTime());

    }

    @Test
    public void testValidateStartTimeBeforeEndTime(){
        AEC.startTime.setTime(testTime.plusHours(1));
        AEC.endTime.setTime(testTime);
        AEC.validateeEndTime();
        AEC.validateStartTime();
        assertEquals(false, AEC.validateStartTimeBeforeEndTime());
        AEC.endTime.setTime(testTime.plusHours(3));
        AEC.validateeEndTime();
        assertEquals(true, AEC.validateStartTimeBeforeEndTime());
    }

    @Test
    public void testValidateRepeating(){
        assertEquals(false, AEC.validateRepeating());
        AEC.repeating.setSelected(true);
        assertEquals(true, AEC.validateRepeating());
    }

    @Test
    public void testValidateHoursOfWork(){
        assertEquals(false, AEC.validateHoursOfWork());
        AEC.hours.setText("test");
        assertEquals(false, AEC.validateHoursOfWork());
        AEC.hours.setText("2");
        assertEquals(true, AEC.validateHoursOfWork());
    }













}
