package layout;

import database.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;

/**
 * Created by Henning on 22.02.2017.
 */
public class CreateUser {
    @FXML private TextArea description;
    @FXML private Button submit;
    @FXML private Button cancel;
    @FXML private TextField email;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField password;
    @FXML private RadioButton stud;
    @FXML private RadioButton teach;



    public void initialize(){
        cancel.setOnAction(event -> {
            System.exit(0);
        });

        submit.setOnAction(event -> {
            //TODO: Make a texfield for field of school and year of school
            Connect conneter = new Connect();
            conneter.addStudent(email.getCharacters().toString(),
                    firstName.getCharacters().toString(),
                    lastName.getCharacters().toString(),
                    "Informatikk",
                    2,
                    password.getCharacters().toString()
                    );
        });
    }
}
