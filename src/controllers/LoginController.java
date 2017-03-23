package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by larsmade on 21.02.2017.
 */
public class LoginController implements Initializable {

    @FXML JFXTextField loginField;
    @FXML JFXPasswordField passwordField;
    @FXML JFXButton openCalendar;
    @FXML Label loginError;

    User user = User.getInstance();
    layout.Calendar cal = new layout.Calendar();
    private Stage stage;
    CalendarController calController = CalendarController.getInstance();

    //Login and set user in userclass.
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
            System.out.println("Teacher Login");
        }


        String user = validateLogin();

        System.out.println(user);
        if((user == "TEACHER")||(user == "STUDENT")){

            //Jumps to the Calendar window..
            stage = (Stage) openCalendar.getScene().getWindow();
            //Parent load = FXMLLoader.load(getClass().getResource("../resources/Calendar.fxml"));

            if(user == "STUDENT") {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/calendar.fxml"));
                loader.setController(CalendarController.getInstance());
                Parent load = loader.load();
                Scene scene = new Scene(load);
                stage.setScene(scene);
                stage.getIcons().add(new Image((getClass().getResourceAsStream("../resources/img/EO.png"))));


                System.out.println("login successful");
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/teacherCalendar.fxml"));
                loader.setController(TeacherCalendarController.getInstance());
                Parent load = loader.load();
                Scene scene = new Scene(load);
                stage.setScene(scene);
                stage.getIcons().add(new Image((getClass().getResourceAsStream("../resources/img/EO.png"))));
                System.out.println("login successful");
            }
        }else{
            loginError.setText("Wrong username or password");
            System.out.println("wrong username or password");
        }//Should check after user in database here. --> Error if there is no such user / Or of no such user exist it will be a new user created.
    }

    private String validateLogin(){
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

    public void newUser(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/makeuser.fxml"));
            Parent root = loader.load();
            Stage st = new Stage();
            st.setTitle("Make User");
            CreateUser controller = loader.getController();
            controller.setDialogStage(st);
            st.setScene(new Scene(root));
            st.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("funker ikke");
            e.printStackTrace();
        }
        System.out.println("press");

    }

    public User getUser(){
        return this.user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginField.setText("larsmade@stud.ntnu.no");
        passwordField.setText("123");
    }
}
