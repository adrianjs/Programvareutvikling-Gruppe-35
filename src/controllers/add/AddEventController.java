package controllers.add;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import controllers.TeacherCalendarController;
import database.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 08.03.2017.
 */
public class AddEventController implements Initializable{

    //Lecture
    @FXML Group mainGroup;
    @FXML Group endDateGroup;
    @FXML Group endTimeGroup;
    @FXML Group repeatingGroup;

    //Inputfields
    final ToggleGroup group = new ToggleGroup();
    @FXML JFXTextField subject;
    @FXML JFXTextField eventName;
    @FXML JFXRadioButton classRadio;
    @FXML JFXRadioButton schoolWork;
    @FXML JFXRadioButton deadline;
    @FXML JFXRadioButton exam;
    @FXML JFXRadioButton homeExam;
    @FXML JFXTextField description;
    @FXML JFXDatePicker startDate;
    @FXML JFXDatePicker endDate;
    @FXML JFXDatePicker startTime;
    @FXML JFXDatePicker endTime;
    @FXML JFXTextField repeating;
    @FXML Label errorLabel;
    TeacherCalendarController teach;

    //Values
    String subject1;
    String eventName1;
    String description1;
    LocalDate startDate1;
    LocalDate endDate1;
    int repeating1;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;

    Teacher t = new Teacher();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setToggle();
        teach = TeacherCalendarController.getInstance();
    }

    /**
     * Set radiobuttons in one group.
     */
    public void setToggle(){
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
            if(startTime == true && endTime == true){
                startTbeforeEndT = validateStartTimeBeforeEndTime();
            }
            boolean rep = validateRepeating();
            if(subject == true && name == true && description == true && start == true && startTbeforeEndT == true && rep == true){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour + ":00:00";
                String endTimeString = endHour + ":00:00";
                t.addLecture(eventName1, startDate1, startTimeString, endTimeString, repeating1, description1, subject1);
                teach.addEvent();
                teach.snack(0, "New Lecture entered to database");
            }
        }
        else if(schoolWork.isSelected()){//schoolwork
            boolean name = validateEventName();
            boolean startDate = validateStartDate();
            boolean endDate = validateEndDate();
            boolean startToEnd = false;
            if(startDate == true && endDate == true){
                startToEnd =validateStartBeforeEnd();
            }
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startToEndT = false;
            if(startTime == true && endTime == true){
                startToEndT = validateStartTimeBeforeEndTime();
            }
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            boolean rep = validateRepeating();
            if(name == true && startToEnd == true && startToEnd == true && desc == true && subject == true && rep == true){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour + ":00:00";
                String endTimeString = endHour + ":00:00";
                t.addSchoolWork(eventName1, startDate1, endDate1, startTimeString, endTimeString, repeating1, description1, (double) 0, subject1 );
                System.out.println("New schoolwork entered to database");
                teach.addEvent();
                teach.snack(0, "New schoolwork entered to database");
            }
        }
        else if(deadline.isSelected()){//deadline
            boolean name = validateEventName();
            boolean date = validateStartDate();
            boolean time = validateStartTime();
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            if(name == true && date == true && time == true && desc == true && subject == true){
                System.out.println("all good deadline");
                String starthour = changeHour(this.startHour);
                String startTimeString = starthour + ":00:00";
                t.addDeadLine(eventName1, startDate1, startTimeString, description1, subject1);
                System.out.println("New deadline entered to database");
                teach.addEvent();
                teach.snack(0, "New deadline entered to database");
            }

        }
        else if(exam.isSelected()){//exam
            boolean name = validateEventName();
            boolean date = validateStartDate();
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startToEndT = false;
            if(startTime == true && endTime == true){
                startToEndT = validateStartTimeBeforeEndTime();
            }
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            if(name == true && date == true && startToEndT == true && desc == true && subject == true){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour + ":00:00";
                String endTimeString = endHour + ":00:00";
                t.addExam(eventName1, startDate1, startTimeString, endTimeString, description1, subject1);
                teach.addEvent();
                teach.snack(0, "New exam entered to database");
            }
        }
        else if(homeExam.isSelected()){
            boolean name = validateEventName();
            boolean startDate = validateStartDate();
            boolean endDate = validateEndDate();
            boolean startToEndD = false;
            if(startDate == true && endDate == true){
                startToEndD = validateStartBeforeEnd();
            }
            boolean startTime = validateStartTime();
            boolean endTime = validateeEndTime();
            boolean startToEndT = false;
            if(startTime == true && endTime == true){
                startToEndT = validateStartTimeBeforeEndTime();
            }
            boolean desc = validateDescription();
            boolean subject = validateSubject();
            if(name == true && startToEndD == true && startToEndT == true && desc == true && subject == true){
                String starthour = changeHour(this.startHour);
                String endHour = changeHour(this.endHour);
                String startTimeString = starthour + ":00:00";
                String endTimeString = endHour + ":00:00";
                t.addHomeExam(eventName1, startDate1, endDate1, startTimeString, endTimeString, description1, (double) 0, subject1);
                teach.addEvent();
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
        teach.addEvent();

    }

    //Validate methods --> Validates all fields in add event controller.
    public boolean validateSubject(){
        String subject = this.subject.getText();
        //TODO: Validate subject to database and check if it is cannot put subject to database unless it exist.
        subject1 = subject;
        return true;
    }

    public boolean validateEventName(){
        String name = eventName.getText();
        if(name.length() == 0){
            errorLabel.setText("Must have an event name");
            System.out.println("Must have an event name");
            return false;
        }
        eventName1 = name;
        return true;
    }

    public boolean validateDescription(){
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

    public boolean validateStartDate(){
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

    public boolean validateEndDate(){
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

    public boolean validateStartBeforeEnd(){
        if(startDate1.isAfter(endDate1)){
            errorLabel.setText("Start must be before end");
            System.out.println("start must be before end");
            return false;
        }
        return true;
    }

    public boolean validateStartTime(){
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

    public boolean validateeEndTime(){
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

    public boolean validateStartTimeBeforeEndTime(){
        if(startHour > endHour){
            errorLabel.setText("Starthour must be before end hour");
            System.out.println("starthour must be before end hour");
            return false;
        }
        return true;
    }

    public boolean validateRepeating(){
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
     * @param hour
     * @return Right formated string for database.
     */
    public String changeHour(int hour){
        String string = Integer.toString(hour);
        if(string.length() == 1){
            return "0"+ string;
        }return string;
    }
}
