package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSnackbar;
import controllers.add.AddEventController;
import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import layout.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 08.03.2017.
 * Controller class for the main teacher view. teacherCalendar.fxml.
 */
public class TeacherCalendarController extends Connect implements Initializable{

    @FXML JFXButton logOut;
    @FXML JFXDrawer drawer;
    @FXML JFXDrawer drawer2;
    @FXML AnchorPane barPane;
    @FXML AnchorPane centerPane;
    AnchorPane add;
    AnchorPane feedback;
    AnchorPane addTsbubject;
    @FXML Label userName;
    @FXML Group mainButtons;
    @FXML Group topButtons;
    @FXML HBox topBox;
    @FXML Label teachingSubjects;
    JFXSnackbar bar;

    private ObservableList<String> subjects = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAddField();
        userName.setText(User.getInstance().getUsername());
        teachingSubjects.setText("Teaching subjects: " + getSubject().toString());
    }

    //SingeltonPattern
    private static TeacherCalendarController instance = null; //InstanceControl
    public static TeacherCalendarController getInstance() {
        if (instance == null) {
            instance = new TeacherCalendarController();
        }
        return instance;
    }

    /**
     * Get subjects that user user current are teaching in.
     * @return teaching subjects.
     */
    public ObservableList<String> getSubject(){
        subjects.clear();
        try{
            ResultSet m_ResultSet = stmt.executeQuery("SELECT * FROM SUBJECT WHERE coordinatorEmail = '"+ User.getInstance().getUsername()+"'");
            while(m_ResultSet.next()){
                subjects.add(m_ResultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    /**
     * Onaction from addActivity button.
     */
    public void addEvent() {
        //Slides the Botto field for bigger table layout
        addPane(1);
    }

    /**
     * On action from check Feedback-button
     */
    public void feedback(){
        addPane(2);
    }

    /**
     * On action from add Subject button.
     */
    public void addTeachingSubject(){
        addPane(3);
    }

    /**
     * On action from cancel-button.
     */
    public void cancel(){
        topButtons.setVisible(false);
        mainButtons.setVisible(true);
        drawer.close();
        centerPane.toFront();
        userName.setVisible(true);
        teachingSubjects.setText("Teaching subjects: " + getSubject().toString());

    }

    /**
     * Sets fxml-documents to anchorpanes, which can be set to the drawer when needed.
     */
    public void setAddField(){
        try {
            add = FXMLLoader.load(getClass().getResource("/resources/fxml/addEvent.fxml"));
            feedback = FXMLLoader.load(getClass().getResource("/resources/fxml/feedback.fxml"));
            addTsbubject = FXMLLoader.load(getClass().getResource("/resources/fxml/addTeachingSubject.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add pane to the drawer based on wich button is clicked.
     * @param number get number from on buttons onaction methods
     */
    private void addPane(int number){
        if(number == 1){
            drawer.setSidePane(add);
            slide(drawer, number);
        }
        if(number == 2){
            drawer.setSidePane(feedback);
            slide(drawer, number);
        }
        if(number == 3){
            drawer.setSidePane(addTsbubject);
            slide(drawer, number);
        }
    }

    /**
     * Makes topbuttonbar-visible if a document is open, and the main buttons if fxml document is closed.
     * @param drawe the drawer.
     * @param number number of fxml document.
     */
    private void slide(JFXDrawer drawe, int number){
        if(drawe.isShown()&& number == 1) {
            drawe.close();
            topButtons.setVisible(true);
            mainButtons.setVisible(false);
            drawe.open();
            userName.setVisible(false);
        }
        else if(drawe.isShown() && ((number == 2)||(number == 3))) {
            drawe.close();
            topButtons.setVisible(true);
            mainButtons.setVisible(false);
            userName.setVisible(false);
            drawe.open();
        }

        else{
            topButtons.setVisible(true);
            mainButtons.setVisible(false);
            userName.setVisible(false);
            drawe.open();
            drawe.toFront();
            add.toFront();
            centerPane.toBack();
        }
        }


    /**
     * Setting text, and displaying snackbar.
     * @param number number who desides if statement
     * @param string what to set in snackbar
     */
    public void snack(int number, String string){
        bar = new JFXSnackbar(barPane);
        if(number == 0){
            //bar.setStyle("-fx-text-fill: red;");
            bar.setId("1");
            bar.show(string, 8000);
        }
        else if(number == 2){
            bar.setId("2");
            bar.show(string, 8000);
        }else if(number == 3){
            bar.setId("3");
            bar.show(string, 8000);
        }else{
            bar.setId("4");
            bar.show("No event added", 5000);
        }
    }

    /**
     * Logout loads the loginscreen
     * @throws IOException IOExeprion
     */
    public void logOut() throws IOException {
        System.out.println("LogOut");
        Stage s = (Stage) logOut.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/username.fxml"));
        Parent load = loader.load();
        Scene scene = new Scene(load);
        s.setScene(scene);
        TeacherCalendarController.instance = null;
    }
}
