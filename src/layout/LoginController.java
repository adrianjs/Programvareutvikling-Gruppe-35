package layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 21.02.2017.
 */
public class LoginController implements Initializable {

    @FXML
    TextField loginField;

    @FXML
    Button openCalendar;

    User user = new User();

    private Stage stage;



    //Login and set user in userclass.
    public void login() throws IOException {

        //Jumps to the Calendar window..
        stage = (Stage) openCalendar.getScene().getWindow();
        Parent load = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
        Scene scene = new Scene(load);
        stage.setScene(scene);
        System.out.println("login");

        //Schould check after user in database here. --> Error if there is no such user / Or of no such user exist it will be a new user created.
        String username = loginField.getText();
        user.setUsername(username);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}