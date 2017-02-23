package layout;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;


public class AddController implements Initializable{

    @FXML
    TextField activity;

    @FXML
    JFXDatePicker date;

    @FXML
    JFXDatePicker startTime;

    @FXML
    JFXDatePicker endTime;

    @FXML
    JFXButton cancel;

    @FXML
    JFXButton sendInn;

    @FXML
    JFXCheckBox everyWeek;

    @FXML
    Label errorActivity;

    @FXML
    Label errorDate;

    @FXML
    Label errorTime;

    @FXML
    ChoiceBox priority;

    @FXML
    Label priorityError;

    Stage stage;


    //Time set.
    String act;

    LocalDate dateSet;

    int start;
    int stop;
    boolean repeat;


    //On action from Send-in button
    public void sendIn(){
        errorActivity.setText("");
        errorDate.setText("");
        priorityError.setText("");
        //Må sjekke i forhold til resten av kalenderen om det kan settes inn en aktivitet på dette tidspunktet..
        boolean check1 = checkActivity();
        boolean check2 = checkDate();
        boolean check3 = checkTime();
        //
        if((check1 == true) && (check2 == true)&& (check3 == true)){
            //legg til i database.. Eventuelt sjekk opp i mot database om man kan legge til noe på dette tidspunktet.
            stage = (Stage) cancel.getScene().getWindow();

            //setter verdier, må da lagres i databasen...
            act = activity.getText();
            dateSet = date.getValue();
            start = startTime.getTime().getHour();
            stop = endTime.getTime().getHour();
            repeat = everyWeek.isSelected();


            stage.close();
        }

        System.out.println("send in");
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
           errorDate.setText("Must hava a date");
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



        System.out.println(start);
        System.out.println(stop + "" + "stop");


        if(start >= stop){
            errorTime.setText("Start must be before end");
            return false;
        }


        return true;
    }

    public boolean checkPriority(){

        return true;

    }

    public void setElementsToProirityChoiceBox(){
        System.out.println("priority");
        priority = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5"));
        System.out.println("loaded that sjiet");

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
