package layout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import database.Connect;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Henning on 22.02.2017.
 */
public class CreateUser {
    public Stage dialogStage;
    private boolean success = false;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @FXML JFXTextArea description;

    @FXML private Button submit;
    @FXML private Button cancel;

    @FXML JFXTextField email;
    @FXML JFXTextField firstName;
    @FXML JFXTextField lastName;
    @FXML JFXPasswordField password;
    @FXML JFXTextField study;
    @FXML JFXTextField department;

    @FXML RadioButton stud;
    @FXML RadioButton teach;

    @FXML Group fieldStud;
    @FXML Group fieldTeach;

    @FXML Group yearGroup;
    @FXML JFXRadioButton firsty;
    @FXML JFXRadioButton secondy;
    @FXML JFXRadioButton thirdy;
    @FXML JFXRadioButton fourthy;
    @FXML JFXRadioButton fifthy;

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
                success = true;
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
        }else if(fifthy.isSelected()){
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
        if(!validateEmailFormat(email.getText())){
            errorMessage += "Email is on wrong format.\n";
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
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/img/EO.png").toString()));
            alert.showAndWait();
            return false;
        }
    }

    public boolean getSuccess(){
        return success;
    }

    public boolean validateEmailFormat(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
