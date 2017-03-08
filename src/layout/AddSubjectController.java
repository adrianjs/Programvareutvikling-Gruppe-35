package layout;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.skins.JFXTextFieldSkin;
import com.sun.deploy.util.StringUtils;
import database.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by larsmade on 23.02.2017.
 */
public class AddSubjectController extends Connect implements Initializable{

    @FXML TextField subject;
    @FXML JFXButton sendInn;
    @FXML JFXComboBox subjectPicker;

    private ResultSet m_ResultSet;
    private ObservableList<String> subjects = FXCollections.observableArrayList();


    public void addSubjectToCalendar() throws SQLException {
        //check up against database..
        //Then add it to calendar if it exist.
        ResultSet stud_sub_resultset = stmt.executeQuery("SELECT * FROM STUDENT WHERE email='"+ User.getInstance().getUsername() +"'");
        stud_sub_resultset.next();
        String subjectString = stud_sub_resultset.getString(7); //Works as long as column 7 is the students subjects

        Set<String> setOfSubjects = new HashSet<>(); //Make a HashSet to deal with duplicates
        List<String> items = Arrays.asList(subjectString.split("\\s*,\\s*"));
        setOfSubjects.addAll(items);
        setOfSubjects.add((String) subjectPicker.getValue()); //Adding new chosen subject
        System.out.println(setOfSubjects);
        subjectString = StringUtils.join(setOfSubjects, ",");
        System.out.println(subjectString);
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        try {
            m_ResultSet = stmt.executeQuery("SELECT * FROM SUBJECT");
            while(m_ResultSet.next()){
                System.out.println(m_ResultSet.getString(1));
                subjects.add(m_ResultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        subjectPicker.setItems(subjects.sorted());
        TextFields.bindAutoCompletion(subjectPicker.getEditor(), subjectPicker.getItems());
    }
}
