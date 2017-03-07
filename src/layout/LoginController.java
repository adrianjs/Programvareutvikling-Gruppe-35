package layout;

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
import javafx.stage.Stage;

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
    Calendar cal = new Calendar();
    private Stage stage;

    //Login and set user in userclass.
    public void login() throws IOException {
        loginError.setText("");
        String username = loginField.getText();
        String password = passwordField.getText();

        user.setUsername(username);
        user.setPassword(password);

        if(validateLogin()){

            //Jumps to the Calendar window..
            stage = (Stage) openCalendar.getScene().getWindow();
            //Parent load = FXMLLoader.load(getClass().getResource("../resources/Calendar.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/calendar.fxml"));
            loader.setController(CalendarController.getInstance());
            Parent load = loader.load();

            Scene scene = new Scene(load);
            stage.setScene(scene);
            System.out.println("login successful");

        }else{
            loginError.setText("Wrong username or password");
            System.out.println("wrong username or password");
        }//Should check after user in database here. --> Error if there is no such user / Or of no such user exist it will be a new user created.
    }

    private boolean validateLogin(){
        try {
            Login login = new Login();
            Set<List> students = login.getStudent();
            for(List student : students){
                if(student.get(0).equals(user.getUsername())&&(student.get(1).equals(user.getPassword()))){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void newUser(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/makeuser.fxml"));
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
