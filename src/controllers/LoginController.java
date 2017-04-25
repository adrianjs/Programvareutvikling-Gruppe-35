package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import database.Login;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by larsmade on 21.02.2017.
 * Controller class for username.fxml.
 */
public class LoginController implements Initializable {

    @FXML JFXTextField loginField;
    @FXML JFXPasswordField passwordField;
    @FXML JFXButton openCalendar;
    @FXML Label loginError;
    @FXML AnchorPane snackBarPane;

    public User user = User.getInstance();
    private layout.Calendar cal = new layout.Calendar();
    Stage stage;
    CalendarController calController = CalendarController.getInstance();
    JFXSnackbar jfxSnackbar;
    FXMLLoader loader;
    String test = "denne stringen er til testing";

    //Login and set user in userclass.

    /**
     * Log in, and set new user to userClass.
     * @throws IOException inputexception
     * @throws SQLException sql exception
     */
    public void login() throws IOException, SQLException {
        loginError.setText("");
        String username = loginField.getText();
        String password = passwordField.getText();
        user.setUsername(username);
        user.setPassword(password);
        try{
            user.updateSubjects();
        }catch(Exception e){
            //If you login as teacher you cant update subjects.
        }
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        String user = validateLogin();
        System.out.println("user: "+user);
        if((user == "TEACHER")||(user == "STUDENT")){
            //Jumps to the Calendar window..
                stage = (Stage) openCalendar.getScene().getWindow();

            //Parent load = FXMLLoader.load(getClass().getResource("../resources/Calendar.fxml"));
            if(user == "STUDENT") {
                loader = new FXMLLoader(getClass().getResource("/resources/fxml/calendar.fxml"));
                loader.setController(CalendarController.getInstance());
                Parent load = loader.load();
                Scene scene = new Scene(load);
                stage.setScene(scene);
                stage.getIcons().add(new Image((getClass().getResourceAsStream("/resources/img/EO.png"))));
                System.out.println("login successful");
                CalendarController calCtrl = CalendarController.getInstance();
//                //TODO: SE PÅ DENNE fikset nå??
                calCtrl.refresh();
                test = "student";
                stage.setOnCloseRequest(event -> {
                    System.exit(0);
                });
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/teacherCalendar.fxml"));
                loader.setController(TeacherCalendarController.getInstance());
                Parent load = loader.load();
                Scene scene = new Scene(load);
                stage.setScene(scene);
                stage.getIcons().add(new Image((getClass().getResourceAsStream("/resources/img/EO.png"))));
                test = "teacher";
                System.out.println("login successful");
                stage.setOnCloseRequest(event -> {
                    System.exit(0);
                });
            }
        }else{
            loginError.setText("Wrong username or password");
        }//Should check after user in database here. --> Error if there is no such user / Or of no such user exist it will be a new user created.
    }

    /**
     * Validates login
     * @return Wich user that try to log in.
     */
    public String validateLogin(){
        try {
            Login login = new Login();
            Set<List> students = login.getStudent();
            Set<List> courseCoordinators = login.getCourseCoordinator();
            for(List student : students){
                if(student.get(0).equals(user.getUsername())&&(student.get(1).equals(user.getPassword()))){
                    return "STUDENT";
                }
            }
            for(List courseCoordinator : courseCoordinators) {
                if (courseCoordinator.get(0).equals(user.getUsername()) && (courseCoordinator.get(1).equals(user.getPassword()))) {
                    return "TEACHER";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Loads the new user window
     */
    public void newUser(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/makeuser.fxml"));
            Parent root = loader.load();
            Stage st = new Stage();
            st.setTitle("Make User");
            CreateUser controller = loader.getController();
            st.getIcons().add(new Image((getClass().getResourceAsStream("/img/EO.png"))));
            //System.out.println("Controller: " + controller);
            controller.setDialogStage(st);
            st.setScene(new Scene(root));
            st.showAndWait();
            if(controller.getSuccess()){
                showNewUserSnackbar();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Snackbar if new user is created.
     */
    public void showNewUserSnackbar(){
        jfxSnackbar = new JFXSnackbar(snackBarPane);
        jfxSnackbar.enqueue(new JFXSnackbar.SnackbarEvent("Your new user is now in the system! Try to login!"));
        jfxSnackbar.setId("forTesting");
    }

    /**
     *
     * @return Return user
     */
    public User getUser(){
        return this.user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
