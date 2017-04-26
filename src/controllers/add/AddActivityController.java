package controllers.add;

import calendar.Activity;
import calendar.Cell;
import com.jfoenix.controls.*;
import controllers.CalendarController;
import database.Connect;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import layout.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


public class AddActivityController implements Initializable{

    @FXML JFXTextField activity;
    @FXML JFXDatePicker date;
    @FXML JFXDatePicker startTime;
    @FXML JFXDatePicker endTime;
    @FXML JFXButton cancel;
    @FXML JFXButton sendInn;
    @FXML JFXCheckBox everyWeek;
    @FXML Label errorTime;
    @FXML JFXComboBox priority;
    @FXML JFXTextField desc;
    Stage stage;
    //Time set.
    String act;
    LocalDate dateSet;
    int start;
    int stop;
    boolean repeat;
    int priorityNumber;
    String description;
    User user;

    CalendarController cal;

    //Methods
    //On action from Send-in button

    /**
     * Onaction from add in button.
     */
    public void sendIn(){
        errorTime.setText("");
        int startMin = 0;
        int endMin = 0;
        if((checkActivity()) && (checkDate())&& (checkTime()) && (checkPriority())){
            try {
                stage = (Stage) cancel.getScene().getWindow();
                startMin = startTime.getTime().getMinute();
                endMin = endTime.getTime().getMinute();
            } catch (NullPointerException e){

            }
            //setter verdier, må da lagres i databasen...
            act = activity.getText();
            dateSet = date.getValue();
            String value = priority.getValue().toString();
            priorityNumber = Integer.parseInt(value);
            start = startTime.getTime().getHour();
            stop = endTime.getTime().getHour();
            repeat = everyWeek.isSelected();
            description = desc.getText();

            startMin = startTime.getTime().getMinute();
            endMin = endTime .getTime().getMinute();


            if(start == stop ) {
                stop = stop + 1;
                startMin = 0;
                endMin = 0;
            }

            if(startMin > 45 && stop > start +1){
                start = start += 1;
                startMin = 0;
            }

            if(endMin > 20){
                endMin = 0;
                stop = stop + 1;
            }

            Cell cell = toUserCell();
            pushCell(cell);
            try {
                stage.close();
                CalendarController.getInstance().refresh();
            } catch (NullPointerException e){

            }


        }

    }

    /**
     * Push cell to database.
     * @param cell usercell.
     */
    private void pushCell(Cell cell) {
        Connect connecter = new Connect();
        User user = User.getInstance();
        System.out.println(user.getUsername());
        java.util.Calendar calendar = Calendar.getInstance();
        calendar.setTime(cell.getStartDate());
        int startHour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTime(cell.getEndDate());
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        connecter.addActivity(cell.getName(), new java.sql.Date(cell.getStartDate().getTime()) ,
                cell.isRepeating(), cell.getSlotPriority(),
                startHour, endHour,
                user.getUsername(), cell.getDescription());
    }

    /**
     * Send in information to userCell.
     * @return activity.
     */
    public Cell toUserCell(){
        LocalDateTime startTime = dateSet.atTime(start, 0);
        LocalDateTime endTime = dateSet.atTime(stop, 0);

        Date dateStart = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());

        String [] colors = {"F44336","E91E63","9C27B0","673AB7","3F51B5","2196F3","03A9F4","009688"};
        int randomNum = ThreadLocalRandom.current().nextInt(0,  7);

        Activity activity = new Activity(dateStart, dateEnd, String.valueOf(startTime.getHour()), String.valueOf(endTime.getHour()), act, description, priorityNumber, repeat, 0, colors[randomNum]);
        return activity;
    }

    /**
     * Checks the activity name.
     * @return boolean
     */
    public boolean checkActivity(){
        String activ = activity.getText();
        if(activ.length() == 0){
            errorTime.setText("Must have an activity name");
            return false;
        }
        return true;
    }

    /**
     * Check if date is after today, else not valid.
     * @return boolean
     */
    public boolean checkDate(){
        LocalDate tryDate = date.getValue();
        if(tryDate == null){
           errorTime.setText("Must hav a a date");
           return false;
       }
        Date today = new Date();
        LocalDate thisdate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(tryDate.isBefore(thisdate)){
            errorTime.setText("Date must be after today");
            return false;
        }
        return true;
    }

    /**
     * Time must be set, starttime must be earlier than end-time.
     * @return boolean
     */
    public boolean checkTime(){
        int start = 0;
        int stop = 0;
        int startMin = 0;
        int endMin = 0;

        try{
            start = startTime.getTime().getHour();
            stop = endTime.getTime().getHour();
            startMin = startTime.getTime().getMinute();
            endMin = endTime.getTime().getMinute();



        }catch (Exception e){
            errorTime.setText("Time must be added");
        }
        if(start > stop){
            errorTime.setText("Start must be before end");
            return false;
        } else if((start == stop) && startMin > endMin) {
            errorTime.setText("Start must be before end");
            return false;
        }if(start < 8 || stop < 8){
            errorTime.setText("You can not have plans before 8 clock");
            return false;
        }

        return true;
    }

    /**
     * Checks the prioritynumber.
     * @return boolean
     */
    public boolean checkPriority(){
        String priorityValue = "";
        int priorityNumber = 0;
        try{
            priorityValue = priority.getValue().toString();
            priorityNumber = Integer.parseInt(priorityValue);
        }catch(Exception e){
            errorTime.setText("Must have priority");
            return false;
        }
        return true;
    }

    /**
     * Sets the numver to priority combobox.
     */
    public void setElementsToProirityChoiceBox(){
        priority.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
    }

    /**
     * Closes the scene, onaction from canelbutton.
     */
    public void cancel(){
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cal = CalendarController.getInstance();
        setElementsToProirityChoiceBox();
        Date input = CalendarController.getInstance().getChosenDate();
        LocalDate todayDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date.setValue(todayDate);
    }
}
