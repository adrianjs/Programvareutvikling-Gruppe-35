package layout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by Henning on 22.02.2017.
 */
public class CreateUser {
    public Stage dialogStage;

    @FXML private JFXTextArea description;

    @FXML private Button submit;
    @FXML private Button cancel;

    @FXML private JFXTextField email;
    @FXML private JFXTextField firstName;
    @FXML private JFXTextField lastName;
    @FXML private JFXPasswordField password;
    @FXML private JFXTextField study;
    @FXML private JFXTextField department;

    @FXML private RadioButton stud;
    @FXML private RadioButton teach;

    @FXML private Group fieldStud;
    @FXML private Group fieldTeach;

    @FXML private Group yearGroup;
    @FXML private JFXRadioButton firsty;
    @FXML private JFXRadioButton secondy;
    @FXML private JFXRadioButton thirdy;
    @FXML private JFXRadioButton fourthy;
    @FXML private JFXRadioButton fifthy;

    private ToggleGroup studTeacher = new ToggleGroup();


    public void initialize(){
        stud.setToggleGroup(studTeacher);
        teach.setToggleGroup(studTeacher);

        description.setVisible(false);
        fieldStud.setVisible(false);
        fieldTeach.setVisible(false);
        yearGroup.setVisible(false);

        cancel.setOnAction(event -> {
            dialogStage.close();
        });

        stud.setOnAction(event -> {
            //Show stud related fields
            fieldStud.setVisible(true);
            yearGroup.setVisible(true);
            //Hide teacher related fields
            fieldTeach.setVisible(false);
            description.setVisible(false);
        });

        teach.setOnAction(event -> {
            //Hide stud related fields
            fieldStud.setVisible(false);
            yearGroup.setVisible(false);
            //Show teacher related fields
            fieldTeach.setVisible(true);
            description.setVisible(true);

        });
        submit.setOnAction(event -> {
            if (isInputValid()) {
                Connect connecter = new Connect();
                System.out.println(stud.isSelected());
                System.out.println(teach.isSelected());
                if (stud.isSelected()) {
                    connecter.addStudent(email.getCharacters().toString(),
                            firstName.getCharacters().toString(),
                            lastName.getCharacters().toString(),
                            study.getCharacters().toString(),
                            getSchoolYear(),
                            password.getCharacters().toString()
                    );
                } else if (teach.isSelected()) {
                    connecter.addTeacher(email.getCharacters().toString(),
                            firstName.getCharacters().toString(),
                            lastName.getCharacters().toString(),
                            department.getCharacters().toString(),
                            description.getText(),
                            password.getCharacters().toString()
                    );
                } else {
                    System.out.println("You have to choose student or teacher!");
                }
            }
        });

    }


    public void setDialogStage(Stage dialogStage){ this.dialogStage = dialogStage; }

    public int getSchoolYear(){
        if(firsty.isSelected()){
            return 1;
        }else if(secondy.isSelected()){
            return 2;
        }else if(thirdy.isSelected()){
            return 3;
        }else if(fourthy.isSelected()){
            return 4;
        }else if(fifthy.isScaleShape()){
            return 5;
        }else{
            return 0;
        }
    }

    public boolean isInputValid(){
        String errorMessage = "";

        if (email.getText() == null || email.getText().length() == 0){
            errorMessage += "Email must be filled in.\n";
        } else if(email.getText().length() > 55){
            errorMessage += "Email length must be less than 55 characters.\n";
        }
        if (firstName.getText() == null || firstName.getText().length() == 0){
            errorMessage += "First name must be filled in.\n";
        } else if (firstName.getText().length() > 55){
            errorMessage += "First name must be less than 55 characters.\n";
        }
        if (lastName.getText() == null || lastName.getText().length() == 0){
            errorMessage += "Last name must be filled in.\n";
        } else if (lastName.getText().length() > 55){
            errorMessage += "Last name must be less than 55 characters.\n";
        }
        if (password.getText() == null || password.getText().length() == 0){
            errorMessage += "Password must be filled in.\n";
        } else if (password.getText().length() > 55){
            errorMessage += "Passwork must be less than 55 characters.\n";
        }
        if(fieldStud.isVisible()) {
            if (study.getText() == null || study.getText().length() == 0) {
                errorMessage += "Field of study must be filled in.\n";
            } else if (study.getText().length() > 55) {
                errorMessage += "Field of study must be less than 55 characters.\n";
            }
            if (!firsty.isSelected() && !secondy.isSelected() && !thirdy.isSelected() &&
                    !fourthy.isSelected() && !fifthy.isSelected()) {
                errorMessage += "You must select a year.\n";
            }
        } else {
            if(department.getText() == null || department.getText().length() == 0){
                errorMessage += "Department must be filled in.\n";
            } else if (department.getText().length() > 100){
                errorMessage += "Department must be less than 100 characters.\n";
            }
        }

        if (errorMessage.length() == 0){
            System.out.println("new user created");
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please fix these errors:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
