package layout;

import calendar.UserCell;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;


public class AddController implements Initializable{

    @FXML JFXTextField activity;
    @FXML JFXDatePicker date;
    @FXML JFXDatePicker startTime;
    @FXML JFXDatePicker endTime;
    @FXML JFXButton cancel;
    @FXML JFXButton sendInn;
    @FXML JFXCheckBox everyWeek;
    @FXML Label errorActivity;
    @FXML Label errorDate;
    @FXML Label errorTime;
    @FXML ChoiceBox priority;
    @FXML Label priorityError;
    Stage stage;
    //Time set.
    String act;
    LocalDate dateSet;
    int start;
    int stop;
    boolean repeat;
    int priorityNumber;

    //Methods
    //On action from Send-in button
    public void sendIn(){
        errorActivity.setText("");
        errorDate.setText("");
        priorityError.setText("");
        //Må sjekke i forhold til resten av kalenderen om det kan settes inn en aktivitet på dette tidspunktet..
        boolean check1 = checkActivity();
        boolean check2 = checkDate();
        boolean check3 = checkTime();
        boolean check4 = checkPriority();
        //
        if((check1 == true) && (check2 == true)&& (check3 == true) && (check4 == true)){
            //legg til i database.. Eventuelt sjekk opp i mot database om man kan legge til noe på dette tidspunktet.
            stage = (Stage) cancel.getScene().getWindow();
            //setter verdier, må da lagres i databasen...
            act = activity.getText();
            dateSet = date.getValue();
            String value = priority.getValue().toString();
            priorityNumber = Integer.parseInt(value);
            start = startTime.getTime().getHour();
            stop = endTime.getTime().getHour();
            repeat = everyWeek.isSelected();
            toUserCell();
            stage.close();
        }
    }

    //Send information to usercell.
    public void toUserCell(){
        LocalDateTime startTime = dateSet.atTime(start, 0);
        LocalDateTime endTime = dateSet.atTime(stop, 0);

        Date dateStart = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date dateEnd = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
        //Discriptionfield not made in add.fxml yet..
        UserCell cell = new UserCell(dateStart, dateEnd, act, "Description/more details", priorityNumber, repeat);

    }

    //Must have something in the textfield... else not valid activity.
    public boolean checkActivity(){

        String activ = activity.getText();
        if(activ.length() == 0){
            errorActivity.setText("Must have an activity name");
            return false;
        }
        return true;
    }

    //Check if date is after today, else not valid.
    public boolean checkDate(){
        LocalDate tryDate = date.getValue();
        if(tryDate == null){
           errorDate.setText("Must hav a a date");
           return false;
       }
        Date today = new Date();
        LocalDate thisdate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(tryDate.isBefore(thisdate)){
            errorDate.setText("Date must be after today");
            return false;
        }
        return true;
    }

    //Time must be set, and start must be before.
    public boolean checkTime(){
        int start = 0;
        int stop = 0;
        try{
            start = startTime.getTime().getHour();
            stop = endTime.getTime().getHour();

        }catch (Exception e){
            errorTime.setText("Time must be added");
        }
        if(start >= stop){
            errorTime.setText("Start must be before end");
            return false;
        }
        return true;
    }

    public boolean checkPriority(){
        String priorityValue = "";
        int priorityNumber = 0;
        try{
            priorityValue = priority.getValue().toString();
            priorityNumber = Integer.parseInt(priorityValue);
        }catch(Exception e){
            priorityError.setText("Must have priority");
            return false;
        }
        return true;
    }

    public void setElementsToProirityChoiceBox(){
        priority.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
    }

    //Close scene. On action from cancel button.
    public void cancel(){
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setElementsToProirityChoiceBox();
    }
}
