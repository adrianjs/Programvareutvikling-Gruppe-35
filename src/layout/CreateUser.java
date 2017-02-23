package layout;

import database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    @FXML private TextField study;
    @FXML private TextField department;

    @FXML private RadioButton stud;
    @FXML private RadioButton teach;

    @FXML private Group fieldStud;
    @FXML private Group fieldTeach;

    @FXML private Group yearGroup;
    @FXML private RadioButton firsty;
    @FXML private RadioButton secondy;
    @FXML private RadioButton thirdy;
    @FXML private RadioButton fourthy;
    @FXML private RadioButton fifthy;


    public void initialize(){
        description.setOpacity(0);
        fieldStud.setOpacity(0);
        fieldTeach.setOpacity(0);
        yearGroup.setOpacity(0);

        cancel.setOnAction(event -> {
            System.exit(0);
        });

        stud.setOnAction(event -> {
            //Show stud related fields
            fieldStud.setOpacity(1);
            yearGroup.setOpacity(1);
            //Hide teacher related fields
            fieldTeach.setOpacity(0);
            description.setOpacity(0);
        });

        teach.setOnAction(event -> {
            //Show stud related fields
            fieldStud.setOpacity(0);
            yearGroup.setOpacity(0);
            //Hide teacher related fields
            fieldTeach.setOpacity(1);
            description.setOpacity(1);

        });

        submit.setOnAction(event -> {
            Connect connecter = new Connect();
            System.out.println(stud.isSelected());
            System.out.println(teach.isSelected());
            if(stud.isSelected()){
                connecter.addStudent(email.getCharacters().toString(),
                        firstName.getCharacters().toString(),
                        lastName.getCharacters().toString(),
                        study.getCharacters().toString(),
                        getSchoolYear(),
                        password.getCharacters().toString()
                );
            }else if(teach.isSelected()) {
                //TODO: Arm this to push to TEACHER not STUDENT
                connecter.addTeacher(email.getCharacters().toString(),
                        firstName.getCharacters().toString(),
                        lastName.getCharacters().toString(),
                        department.getCharacters().toString(),
                        description.getText(),
                        password.getCharacters().toString()
                );
            }else{
                System.out.println("You have to choose student or teacher!");
            }
        });
    }

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
}
