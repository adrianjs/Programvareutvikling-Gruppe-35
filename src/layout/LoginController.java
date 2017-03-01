package layout;

import database.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by larsmade on 21.02.2017.
 */
public class LoginController implements Initializable {

    @FXML
    TextField loginField;

    @FXML
    Button openCalendar;


    User user = new User();
    Calendar cal = new Calendar();

    private Stage stage;



    //Login and set user in userclass.
    public void login() throws IOException {
        String username = loginField.getText();
        user.setUsername(username);
        //TODO: Make valid login
        if(validateLogin()){
            //Jumps to the Calendar window..
            stage = (Stage) openCalendar.getScene().getWindow();
            Parent load = FXMLLoader.load(getClass().getResource("../resources/Calendar.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            System.out.println("login");
        }else{
            System.out.println("FEIL BRUKERNAVN");
        }


        //Should check after user in database here. --> Error if there is no such user / Or of no such user exist it will be a new user created.


    }

    private boolean validateLogin() {
        try {
            Login login = new Login();
            Set<List> students = login.getStudent();
            for(List student : students){
                if(student.get(0).equals(user.getUsername())){
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
