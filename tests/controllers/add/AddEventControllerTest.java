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
        AEC.subjects.add("AAR4335");


        date = new Date();
        testDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        testDateTime = LocalDateTime.of(2017, 4,11,12,00);
        testTime = testDateTime.toLocalTime();





    }

    @Test
    public void testCheckSelectedRadioButtons(){
        AEC.classRadio.setSelected(true);
        AEC.schoolWork.setSelected(false);
        AEC.deadline.setSelected(false);
        AEC.exam.setSelected(false);
        AEC.homeExam.setSelected(false);

        AEC.checkSelectedRadioButtons();
        assertEquals(true, AEC.mainGroup.isVisible());
        assertEquals(true, AEC.repeatingGroup.isVisible());
        assertEquals(false, AEC.endDateGroup.isVisible());
        assertEquals(false, AEC.hoursOfWOrk.isVisible());

        AEC.classRadio.setSelected(false);
        AEC.schoolWork.setSelected(true);

        AEC.checkSelectedRadioButtons();
        assertEquals(true, AEC.mainGroup.isVisible());
        assertEquals(true, AEC.repeatingGroup.isVisible());
        assertEquals(true, AEC.endDateGroup.isVisible());
        assertEquals(true, AEC.endTime.isVisible());
        assertEquals(true, AEC.hoursOfWOrk.isVisible());

        AEC.schoolWork.setSelected(false);
        AEC.deadline.setSelected(true);

        AEC.checkSelectedRadioButtons();
        assertEquals(true, AEC.mainGroup.isVisible());
        assertEquals(false, AEC.repeatingGroup.isVisible());
        assertEquals(false, AEC.endDateGroup.isVisible());
        assertEquals(true, AEC.endTime.isVisible());
        assertEquals(false, AEC.hoursOfWOrk.isVisible());

        AEC.deadline.setSelected(false);
        AEC.exam.setSelected(true);

        AEC.checkSelectedRadioButtons();
        assertEquals(true, AEC.mainGroup.isVisible());
        assertEquals(false, AEC.repeatingGroup.isVisible());
        assertEquals(false, AEC.endDateGroup.isVisible());
        assertEquals(true, AEC.endTime.isVisible());
        assertEquals(false, AEC.hoursOfWOrk.isVisible());

        AEC.exam.setSelected(false);
        AEC.homeExam.setSelected(true);

        AEC.checkSelectedRadioButtons();
        assertEquals(true, AEC.mainGroup.isVisible());
        assertEquals(false, AEC.repeatingGroup.isVisible());
        assertEquals(true, AEC.endDateGroup.isVisible());
        assertEquals(true, AEC.endTime.isVisible());
        assertEquals(false, AEC.hoursOfWOrk.isVisible());

        AEC.homeExam.setSelected(false);

    }

    @Test
    public void testGetSubjects(){
        assertEquals("[AAR4335]", AEC.getSubjects());
    }

    @Test
    public void testValidateSubject(){
        assertEquals(false, AEC.validateSubject());
        AEC.subjectsDropDown.setValue("TDT999");
        assertEquals(false, AEC.validateSubject());
        AEC.subjectsDropDown.setValue("AAR4335");
        assertEquals(true, AEC.validateSubject());


    }

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
        AEC.validateEndDate();
        AEC.validateStartDate();
        assertEquals(true, AEC.validateStartBeforeEnd());

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
        assertEquals(0, AEC.repeating1 );
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


    //todo on inteliseren
    /*
    @Test
    public void testAad(){
        AEC.add();
        assertEquals("Must select a radiobutton",AEC.errorLabel.getText());
        AEC.subjectsDropDown.setValue("AAR4335");
        AEC.eventName.setText("test");
        AEC.description.setText("Hvis denne leiger i databasen slet den!");
        AEC.startDate.setValue(testDate);
        AEC.endDate.setValue(testDate.plusDays(2));
        AEC.startTime.setTime(testTime);
        AEC.endTime.setTime(testTime.plusHours(2));
        AEC.hours.setText("2");
        AEC.classRadio.setSelected(true);
        AEC.add();

    }
*/











}