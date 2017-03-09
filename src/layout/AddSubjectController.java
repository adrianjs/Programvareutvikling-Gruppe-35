package layout;

import com.jfoenix.controls.JFXButton;
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
import org.controlsfx.control.textfield.TextFields;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by larsmade on 23.02.2017.
 */
public class AddSubjectController extends Connect implements Initializable{

    @FXML TextField subject;
    @FXML JFXButton sendInn;
    @FXML JFXComboBox subjectPicker;
    @FXML AnchorPane anchorPane;

    private ResultSet m_ResultSet;
    private ObservableList<String> subjects = FXCollections.observableArrayList();


    public void addSubjectToCalendar() throws SQLException {
        //TODO: Handle invalid subjects
        //check up against database..
        //Then add it to calendar if it exist.
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
        if(setOfSubjects.contains(subjectPicker.getValue())){
            JFXSnackbar bar = new JFXSnackbar(anchorPane);
            bar.enqueue(new JFXSnackbar.SnackbarEvent(subjectPicker.getValue() + " is already one of your subjects!"));
        }else{
            if(subjectPicker.getItems().contains(subjectPicker.getValue())){
                setOfSubjects.add((String) subjectPicker.getValue()); //Adding new chosen subject
                subjectString = StringUtils.join(setOfSubjects, ",");
                updateStudentSubjects(subjectString);
                JFXSnackbar bar = new JFXSnackbar(anchorPane);
                bar.enqueue(new JFXSnackbar.SnackbarEvent(subjectPicker.getValue() + " was added to your subjects!"));
            }else{
                JFXSnackbar bar = new JFXSnackbar(anchorPane);
                bar.enqueue(new JFXSnackbar.SnackbarEvent(subjectPicker.getValue() + " is not a valid subject!"));
            }
        }
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
        //TODO: Get this to look pretty or at least the same
        TextFields.bindAutoCompletion(subjectPicker.getEditor(), subjectPicker.getItems());

    }
}
