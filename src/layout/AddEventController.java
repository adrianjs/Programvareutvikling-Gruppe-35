package layout;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.sql.Time;
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
    @FXML JFXCheckBox repeating;
    TeacherCalendarController teach;

    //Values
    String subject1;
    String eventName1;
    String description1;
    LocalDate startDate1;
    LocalDate endDate1;
    int startHour;
    int startMinute;
    int endHour;
    int endMinute;

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

    public void checkSelectedRadioButtons(){//Change in relation to radiobutton click.
        if(classRadio.isSelected()){
            System.out.println("Class");
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(true);
            endDateGroup.setVisible(false);
        }
        if(schoolWork.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(false);
            endDateGroup.setVisible(true);
            System.out.println("Schoolwork");
        }
        if(deadline.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(false);
            endDateGroup.setVisible(false);
            endTimeGroup.setVisible(false);
            System.out.println("deadline");
        }
        if(exam.isSelected()){
            mainGroup.setVisible(true);
            repeatingGroup.setVisible(false);
            endDateGroup.setVisible(false);
            endTimeGroup.setVisible(true);
            System.out.println("Exam");
        }
        if(homeExam.isSelected()){
            mainGroup.setVisible(true);
            endDateGroup.setVisible(true);
            endTimeGroup.setVisible(true);
            repeatingGroup.setVisible(false);
            System.out.println("homeExam");
        }
    }

    /**
     * Valdidor for new event
     * Check the selected radiobutton, and uses validatemethods for the possible
     * inputfields, validate methods is in same class.
     */
    public void add(){//Onaction from add button
        System.out.println("add");
        if(classRadio.isSelected()){//lecture
            System.out.println("lecture is selected");
            int priority = 0;
            //String name, LocalDate startDate, Time startTime, Time endTime, int repeating, String description, String subjectCode
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
            boolean rep = repeating.isSelected();
            if(subject == true && name == true && description == true && start == true && startTbeforeEndT == true){
                System.out.println("All good");
                //TODO:
            }

        }
        else if(schoolWork.isSelected()){//schoolwork
            System.out.println("schoolwork is selected");
            int priority = 0;
            //String name, LocalDate startDate, LocalDate endDate, Time startTime, Time endTime,
            //int repeating, String description, Double houersOfWork, String subjectCode
            boolean start = validateStartDate();
            boolean end = validateEndDate();
            if(start == true && end == true){
                validateStartBeforeEnd();
            }
        }
        else if(deadline.isSelected()){//deadline
            System.out.println("deadline is selected");
            //String name, LocalDate date, Time time, String description, String subjectCode
            int priority = 0;
            //Description, startdate, starttime
        }
        else if(exam.isSelected()){//exam
            System.out.println("exam is selected");
            //String name, LocalDate date, Time startTime, Time endTime, String description, String subjectCode
            int priority = 0;
            //description, startdate, enddate, starttime, end time.
        }
        else if(homeExam.isSelected()){
            System.out.println("homeExam is selected");
            //String name, LocalDate startDate, LocalDate endDate, Time startTime, Time endTime,
            // String description, Double houersOfWork, String subjectCode
        }
        else{
            System.out.println("Must select a radiobutton");
        }

    }
    public void cancel(){//Onaction from cancel-button
        System.out.println("cancel");
        teach.addEvent();

    }

    //Validate methods.
    public boolean validateSubject(){
        String subject = this.subject.getText();
        //TODO: Validate subject to database and check if it is
        subject1 = subject;
        return true;
    }

    public boolean validateEventName(){
        String name = eventName.getText();
        if(name.length() == 0){
            System.out.println("Must have an event name");
            return false;
        }
        eventName1 = name;
        return true;
    }

    public boolean validateDescription(){
        String description = this.description.getText();
        if(description.length() == 0){
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
            System.out.println("Must have a start date");
            return false;
        }
        Date today = new Date();
        LocalDate thisdate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(start.isBefore(thisdate)){
            System.out.println("startDate must be after today");
            return false;
        }else {
            startDate1 = start;
            return true;
        }
    }

    public boolean validateEndDate(){
        LocalDate end = endDate.getValue();
        if(end == null){
            System.out.println("Must have a end date");
            return false;
        }
        Date today = new Date();
        LocalDate thisdate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(end.isBefore(thisdate)){
            System.out.println("end must be after today");
            return false;
        }else{
            endDate1 = end;
            return true;
        }

    }

    public boolean validateStartBeforeEnd(){
        if(startDate1.isAfter(endDate1)){
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
            System.out.println("Must have a end time");
            return false;
        }
        endHour = hour;
        endMinute = minute;
        return true;
    }

    public boolean validateStartTimeBeforeEndTime(){
        if(startHour > endHour){
            System.out.println("starthour must be before end hour");
            return false;
        }
        return true;
    }
}
