package controllers.add;

import com.jfoenix.controls.JFXTextField;
import controllers.TeacherCalendarController;
import database.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import layout.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 17.04.2017.
 * Controllerclass for the addTeachingSubjectController.fxml
 */
public class addTeachingSubjectController implements Initializable{

    @FXML JFXTextField subjectCode;
    @FXML JFXTextField evaluation;
    @FXML JFXTextField desc;
    @FXML Label errorLabel;

    String code = "";
    String eva = "";
    String description = "";
    private TeacherCalendarController teach;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teach = TeacherCalendarController.getInstance();
    }

    /**
     * On action from Add button in fxml file.
     */
    public void addSubject(){
        errorLabel.setText("");
        if(checkSubjectCode() && checkEvaluation() && checkDescription() ){
            new Teacher().addSubject(code, eva, description ,User.getInstance().getUsername());
            teach.snack(2, "The subject: "+ code + " is added to the database");
            teach.cancel();

        }
    }

    /**
     * Check if something is typed in subjectcode-field.
     * @return true/false
     */
    private boolean checkSubjectCode(){
        code = subjectCode.getText();
        if(code.length() == 0){
            errorLabel.setText("Must set something to Subjectcode field");
            return false;
        }
        return true;
    }

    /**
     * Check if something is typed in evaluation-field
     * @return true/false
     */
    private boolean checkEvaluation(){
        eva = evaluation.getText();
        if(eva.length() == 0){
            errorLabel.setText("Must set something to evaluation field");
            return false;
        }
        return true;
    }

    /**
     * Check if something is typed in description-field.
     * @return true/false
     */
    private boolean checkDescription(){
        description = desc.getText();
        if(description.length() == 0){
            errorLabel.setText("Must set something to description field");
            return false;
        }
        return true;
    }

    /**
     * On action from cancel button in fxml.
     */
    public void cancel(){
        teach.cancel();
        teach.snack(3, "No subject added");
    }
}
