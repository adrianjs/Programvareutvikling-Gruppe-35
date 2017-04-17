package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import layout.FeedbackElement;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by larsmade on 11.04.2017.
 * ControllerClass for the feedback.fxml document.
 */
public class FeedbackController implements Initializable{

    @FXML TableView<FeedbackElement> table;
    @FXML TableColumn<FeedbackElement, String> date;
    @FXML TableColumn<FeedbackElement, String> estHour;
    @FXML TableColumn<FeedbackElement, String> feedBack;
    @FXML TableColumn<FeedbackElement, String> desc;
    @FXML JFXComboBox subjectsDropDown;
    @FXML JFXButton checkButton;
    private TeacherCalendarController teach;
    private ObservableList<String> subjects = FXCollections.observableArrayList();
    int avg = 0;
    ArrayList<ArrayList<String>> events;

    //TODO: FIX ADD SCHOOLWORK FOR REPEATING BUG
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teach = TeacherCalendarController.getInstance();
        date.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        estHour.setCellValueFactory(
                new PropertyValueFactory<>("estimate"));
        feedBack.setCellValueFactory(
                new PropertyValueFactory<>("avg"));
        desc.setCellValueFactory(
                new PropertyValueFactory<>("description"));

        subjects = TeacherCalendarController.getInstance().getSubject();
        ComboBox box = new ComboBox();
        box.setItems(subjects.sorted());
        subjectsDropDown.setItems(subjects.sorted());

    }

    /**
     * On action from check button.
     * Goes through all feedbacks on a homework on a given date, ang gives the average hours spent on homework.
     */
    public void check() throws SQLException {
        try{
            events.clear();
        }catch (Exception e){

        }
        System.out.println("Ceckbutton works");
        String subCode = null;
        try{
            subCode = subjectsDropDown.getValue().toString();
            events = new Teacher().getStudentFeedBack(subCode);

        }catch(Exception e){
            System.out.println("Must set subjectcode before checking value");
        }
        String currentDate;
        float average = 0;
        int count = 0;
        ArrayList<String> splitUpDates = new ArrayList<>();
        List<FeedbackElement> data = FXCollections.observableArrayList();
        for (ArrayList<String> eve : events) {
            currentDate = eve.get(1);
            if (!splitUpDates.contains(currentDate)) { //Add all events dates in a split up list.
                splitUpDates.add(eve.get(1));
            }
        }
        String description = "";
        String estimate = "";
        String startDate = "";
        for (String date : splitUpDates){
            for (ArrayList<String> eve : events) {
                if(eve.get(1).equals(date)){
                    System.out.println("SAME");
                    average += Integer.parseInt(eve.get(0));
                    count ++;
                    description = eve.get(2);
                    estimate = eve.get(3);
                    startDate = eve.get(1);

                }
            }
            try{
                average = average/count;
                data.add(new FeedbackElement(startDate, estimate, Float.toString(average), description));
                average = 0;
                count = 0;
            }catch (Exception e){
                System.out.println("Fail");
            }
        }
        table.getItems().setAll(data);
    }

    /**
     * Closes the feedback.fxml window.
     */
    public void cancel(){
        teach.cancel();
    }
}
