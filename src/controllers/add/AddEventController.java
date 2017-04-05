package controllers.add;

import com.jfoenix.controls.*;
import controllers.TeacherCalendarController;
import database.Connect;
import database.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import layout.User;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 08.03.2017.
 * Controller for addEvent.fxml.
 * Check all the parametert so right information is put in database.
 */
public class AddEventController extends Connect implements Initializable{

    //Lecture
    @FXML Group mainGroup;
    @FXML Group endDateGroup;
    @FXML Group endTimeGroup;
    @FXML Group repeatingGroup;

    //Inputfields
    private final ToggleGroup group = new ToggleGroup();
    @FXML JFXTextField eventName;
    @FXML JFXRadioButton classRadio;
    @FXML JFXRadioButton schoolWork;
    @FXML JFXRadioButton deadline;
    @FXML JFXRadioButton exam;
    @FXML JFXRadioButton homeExam;
//    @FXML JFXRadioButton kahoot;
//    @FXML JFXRadioButton extra;
//    @FXML JFXRadioButton examLecture;
    @FXML JFXTextField description;
    @FXML JFXDatePicker startDate;
    @FXML JFXDatePicker endDate;
    @FXML JFXDatePicker startTime;
    @FXML JFXDatePicker endTime;
    @FXML JFXTextField repeating;
    @FXML Label errorLabel;
    @FXML JFXComboBox subjectsDropDown;
    private TeacherCalendarController teach;

    //Values
    private String subject1;
    private String eventName1;
    private String description1;
    private LocalDate startDate1;
    private LocalDate endDate1;
    private int repeating1;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    private Teacher t = new Teacher();

    private ResultSet m_ResultSet;
    private ObservableList<String> subjects = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setToggle();
        teach = TeacherCalendarController.getInstance();
        subjects = TeacherCalendarController.getInstance().getSubject();
        ComboBox box = new ComboBox();
        box.setItems(subjects.sorted());
        subjectsDropDown.setItems(subjects.sorted());
    }


    /**
     * Set radiobuttons in one group.
     */
    private void setToggle(){
        classRadio.setToggleGroup(group);
        schoolWork.setToggleGroup(group);
        deadline.setToggleGroup(group);
        exam.setToggleGroup(group);
        homeExam.setToggleGroup(group);
    }

    /**
     * Changes the veiw in relation to wich radiobutton who is selected.
     * Inputfields in addActivity.fxml will change in wich radiobutton is pressed.
     */
    public void checkSelectedRadioButtons(){//Change in relation to radiobutton click.
        if(classRadio.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(true);
            endDateGroup.setVisible(false);
        }
        if(schoolWork.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(true);
            endDateGroup.setVisible(true);
        }
        if(deadline.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(false);
            endDateGroup.setVisible(false);
            endTimeGroup.setVisible(false);
        }
        if(exam.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(false);
            endDateGroup.setVisible(false);
            endTimeGroup.setVisible(true);
        }
        if(homeExam.isSelected()){
            mainGroup.setVisible(true);
            endDateGroup.setVisible(true);
            endTimeGroup.setVisible(true);
            repeatingGroup.setVisible(false);
        }
    }

    /**
     * Valdidor for new event
     * Check the selected radiobutton, and uses validatemethods for the possible
     * inputfields, validate methods is in same class
     */
    public void add(){//Onaction from add button
        errorLabel.setText("");
        if(classRadio.isSelected()){//lecture
            boolean subject = validateSubject();
            boolean name = validateEventName();
            boolean description = validateDescription();
            boolean start = validateStartDate();
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startTbeforeEndT = false;
            if(startTime && endTime){
                startTbeforeEndT = validateStartTimeBeforeEndTime();
            }
            boolean rep = validateRepeating();
            if(subject && name && description && start && startTbeforeEndT && rep){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour + ":00:00";
                String endTimeString = endHour + ":00:00";
                t.addLecture(eventName1, startDate1, startTimeString, endTimeString, repeating1, description1, subject1);
                teach.cancel();
                teach.snack(0, "New Lecture entered to database");
            }
        }
        else if(schoolWork.isSelected()){//schoolwork
            boolean name = validateEventName();
            boolean startDate = validateStartDate();
            boolean endDate = validateEndDate();
            boolean startToEnd = false;
            if(startDate && endDate){
                startToEnd =validateStartBeforeEnd();
            }
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startToEndT = false;
            if(startTime && endTime){
                startToEndT = validateStartTimeBeforeEndTime();
            }
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            boolean rep = validateRepeating();
            if(name && startToEnd && startToEnd && desc && subject && rep){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour;
                String endTimeString = endHour;
                t.addSchoolWork(eventName1, startDate1, endDate1, startTimeString, endTimeString, repeating1, description1, (double) 0, subject1 );
                System.out.println("New schoolwork entered to database");
                teach.cancel();
                teach.snack(0, "New schoolwork entered to database");
            }
        }
        else if(deadline.isSelected()){//deadline
            boolean name = validateEventName();
            boolean date = validateStartDate();
            boolean time = validateStartTime();
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            if(name && date && time && desc && subject){
                System.out.println("all good deadline");
                String starthour = changeHour(this.startHour);
                //String startTimeString = starthour + ":00:00";
                String startTimeString = starthour;
                t.addDeadLine(eventName1, startDate1, startTimeString, description1, subject1);
                System.out.println("New deadline entered to database");
                teach.cancel();
                teach.snack(0, "New deadline entered to database");
            }

        }
        else if(exam.isSelected()){//exam
            boolean name = validateEventName();
            boolean date = validateStartDate();
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startToEndT = false;
            if(startTime && endTime){
                startToEndT = validateStartTimeBeforeEndTime();
            }
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            if(name && date && startToEndT && desc && subject){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour;
                String endTimeString = endHour;
                t.addExam(eventName1, startDate1, startTimeString, endTimeString, description1, subject1);
                teach.cancel();
                teach.snack(0, "New exam entered to database");
            }
        }
        else if(homeExam.isSelected()){
            boolean name = validateEventName();
            boolean startDate = validateStartDate();
            boolean endDate = validateEndDate();
            boolean startToEndD = false;
            if(startDate && endDate){
                startToEndD = validateStartBeforeEnd();
            }
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startToEndT = false;
            if(startTime && endTime){
                startToEndT = validateStartTimeBeforeEndTime();
            }
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            if(name && startToEndD && startToEndT && desc && subject){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour;
                String endTimeString = endHour;
                t.addHomeExam(eventName1, startDate1, endDate1, startTimeString, endTimeString, description1, (double) 0, subject1);
                teach.cancel();
                teach.snack(0, "New homeExam entered to database");
            }
        }
        else{
            errorLabel.setText("Must select a radiobutton");
            System.out.println("Must select a radiobutton");
        }
    }

    /**
     * On action from cancel button.
     * Use methods in teacherCalenaderController.
     */
    public void cancel(){//Onaction from cancel-button
        System.out.println("cancel");
        teach.snack(1, "");
        teach.cancel();

    }

    //Validate methods --> Validates all fields in add event controller.

    /**
     * Validates that subject is correct.
     * @return boolean
     */
    private boolean validateSubject(){
        String subject = subjectsDropDown.getValue().toString();
        //TODO: Validate subject to database and check if it is cannot put subject to database unless it exist.
        if(!subjects.contains(subject)){
            errorLabel.setText("This subject is not in database");
            return false;
        }
        subject1 = subject;
        return true;
    }

    /**
     * Validate that eventname is set
     * @return boolean
     */
    private boolean validateEventName(){
        String name = eventName.getText();
        if(name.length() == 0){
            errorLabel.setText("Must have an event name");
            System.out.println("Must have an event name");
            return false;
        }
        eventName1 = name;
        return true;
    }

    /**
     * Validate that descriotion is set.
     * @return boolean
     */
    private boolean validateDescription(){
        String description = this.description.getText();
        if(description.length() == 0){
            errorLabel.setText("Must have a description");
            System.out.println("must have a description");
            return false;
        }
        else{
            description1 = description;
            return true;
        }
    }

    /**
     * Validate that startdate is set, and that startdate is after today.
     * @return boolean
     */
    private boolean validateStartDate(){
        LocalDate start = startDate.getValue();
        if(start == null){
            errorLabel.setText("Must have a start date");
            System.out.println("Must have a start date");
            return false;
        }
        Date today = new Date();
        LocalDate thisdate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(start.isBefore(thisdate)){
            errorLabel.setText("Start-date must be after today");
            System.out.println("StartDate must be after today");
            return false;
        }else {
            startDate1 = start;
            return true;
        }
    }

    /**
     * Validates that end-date is set, and that end date is after today
     * @return boolean
     */
    private boolean validateEndDate(){
        LocalDate end = endDate.getValue();
        if(end == null){
            errorLabel.setText("Must have a end date");
            System.out.println("Must have a end date");
            return false;
        }
        Date today = new Date();
        LocalDate thisdate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(end.isBefore(thisdate)){
            errorLabel.setText("End must be after today");
            System.out.println("end must be after today");
            return false;
        }else{
            endDate1 = end;
            return true;
        }

    }

    /**
     * Validate that starttime is before endtime.
     * @return boolean
     */
    private boolean validateStartBeforeEnd(){
        if(startDate1.isAfter(endDate1)){
            errorLabel.setText("Start must be before end");
            System.out.println("start must be before end");
            return false;
        }
        return true;
    }

    /**
     * Validate that a startime is set.
     * @return boolean.
     */
    private boolean validateStartTime(){
        int hour = 0;
        int minute = 0;
        try {
            hour = startTime.getTime().getHour();
            minute = startTime.getTime().getMinute();
        }catch (Exception e){
            errorLabel.setText("Must have a start time");
            System.out.println("Must have a start time");
            return false;
        }
        startHour = hour;
        startMinute = minute;
        return true;
    }

    /**
     * Validates that an endtime is set.
     * @return boolean
     */
    private boolean validateeEndTime(){
        int hour = 0;
        int minute = 0;
        try {
            hour = endTime.getTime().getHour();
            minute = endTime.getTime().getMinute();
        }catch (Exception e){
            errorLabel.setText("Must have a end time");
            System.out.println("Must have a end time");
            return false;
        }
        endHour = hour;
        endMinute = minute;
        return true;
    }

    /**
     * Validates that strattime is before end time.
     * @return boolean
     */
    private boolean validateStartTimeBeforeEndTime(){
        if(startHour > endHour){
            errorLabel.setText("Starthour must be before end hour");
            System.out.println("starthour must be before end hour");
            return false;
        }
        return true;
    }

    /**
     * Validate repeating field.
     * @return boolean.
     */
    private boolean validateRepeating(){
        String rep = repeating.getText();
        if(rep.length() == 0){
            errorLabel.setText("Must have a value in rep field");
            System.out.println("must have a value in rep field");
            return false;
        }
        int number = 0;
        try {
            number = Integer.parseInt(rep);
        }catch (Exception e){
            errorLabel.setText("Must be an integer in rep field");
            System.out.println("must be an integer in rep field");
            return false;
        }
        repeating1 = number;
        return true;
    }

    /**
     * Set a 0 if ther is only one integer in hour so it can set string to right format.
     * @param hour integer of hour parameter
     * @return Right formated string for database.
     */
    private String changeHour(int hour){
        String string = Integer.toString(hour);
        if(string.length() == 1){
            return "0"+ string;
        }return string;
    }

    /**
     * Get subjects related to this teacher
     * @return subjects.
     */
    public String getSubjects(){
        return subjects.toString();
    }
}
