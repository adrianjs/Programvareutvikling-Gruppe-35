package controllers.add;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.sun.deploy.util.StringUtils;
import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import layout.User;
import org.controlsfx.control.textfield.TextFields;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by larsmade on 23.02.2017.
 */
public class AddSubjectController extends Connect implements Initializable{

    @FXML TextField subject;
    @FXML JFXComboBox subjectPicker;
    @FXML AnchorPane anchorPane;

    private ResultSet m_ResultSet;
    private ObservableList<String> subjects = FXCollections.observableArrayList();

    /**
     * Inputs a new valid subject to the current users subjects.
     * Connects to the STUDENT database (DB) and fetches the "subjects" column from the currently logged in user.
     * Parses from a String to a HashSet (to avoid duplicates).
     * Tries to add the subject that the user wants.
     * Validates it against the valid subjects from SUBJECTS DB.
     * Parses the HashSet back to a String and pushes it up to the DB.
     * The JFXSnackbar gives feedback to the user.
     * @throws SQLException
     */

    public void addSubjectToCalendar() throws SQLException {
        ResultSet stud_sub_resultset = stmt.executeQuery("SELECT * FROM STUDENT WHERE email='"+ User.getInstance().getUsername() +"'");
        stud_sub_resultset.next();
        String subjectString = stud_sub_resultset.getString(7); //Works as long as column 7 is the students subjects
        Set<String> setOfSubjects;
        if(subjectString.isEmpty()){
            setOfSubjects = new HashSet<>();
        }else{
            setOfSubjects = new HashSet<>(Arrays.asList(subjectString
                    .split(","))); //Make a HashSet to deal with duplicates
        }
        if(setOfSubjects.contains(subject.getCharacters().toString())){
            JFXSnackbar bar = new JFXSnackbar(anchorPane);
            bar.enqueue(new JFXSnackbar.SnackbarEvent(subject.getCharacters().toString() + " is already one of your subjects!"));
        }else{
            if(subjectPicker.getItems().contains(subject.getCharacters().toString())){
                setOfSubjects.add((String) subject.getCharacters().toString()); //Adding new chosen subject
                subjectString = StringUtils.join(setOfSubjects, ",");
                updateStudentSubjects(subjectString);
                JFXSnackbar bar = new JFXSnackbar(anchorPane);
                bar.enqueue(new JFXSnackbar.SnackbarEvent(subject.getCharacters().toString() + " was added to your subjects!"));
            }else{
                JFXSnackbar bar = new JFXSnackbar(anchorPane);
                bar.enqueue(new JFXSnackbar.SnackbarEvent(subject.getCharacters().toString() + " is not a valid subject!"));
            }
        }
    }

    /**
     * Sets up the ComboBox with subjects the user can pick.
     * Calls the database (DB) and gets all the subjects available from SUBJECT.
     * Binds the subjects to the TextField, so that the user can write
     * and get autocompletion.
     * @param location standard inheritance
     * @param resources standard inheritance
     */
    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        try {
            m_ResultSet = stmt.executeQuery("SELECT * FROM SUBJECT");
            while(m_ResultSet.next()){
                subjects.add(m_ResultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        subjectPicker.setItems(subjects.sorted());
        TextFields.bindAutoCompletion(subject, subjectPicker.getItems());
        subjectPicker.setOnAction(event -> {
            subject.setText(subjectPicker.getValue().toString());
        });

    }
}
