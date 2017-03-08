package layout;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 08.03.2017.
 */
public class TeacherCalendarController implements Initializable{

    @FXML JFXButton addEvent;
    @FXML JFXButton addStudass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private static TeacherCalendarController instance = null; //InstanceControl
    public static TeacherCalendarController getInstance() {
        if (instance == null) {
            instance = new TeacherCalendarController();
        }
        return instance;
    }
    public void addEvent(){
        System.out.println("Add event");
    }
    public void addStudass(){
        System.out.println("add studass");
    }

}
