package layout;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by larsmade on 23.02.2017.
 */
public class AddSubjectController {

    @FXML TextField subject;
    @FXML JFXButton sendInn;

    public void addSubjectToCalendar(){
        String subjectName = subject.getText();
        //check up against database..
        //Then add it to calendar if it exist.
        System.out.println("yolo");
    }
}
