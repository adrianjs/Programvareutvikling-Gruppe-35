package layout;

import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 08.03.2017.
 */
public class AddEventController implements Initializable{

    final ToggleGroup group = new ToggleGroup();
    @FXML JFXRadioButton classRadio;
    @FXML JFXRadioButton schoolWork;
    @FXML JFXRadioButton deadline;
    @FXML JFXRadioButton exam;

    @FXML Group lectureGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setToggle();
    }
    public void setToggle(){
        classRadio.setToggleGroup(group);
        schoolWork.setToggleGroup(group);
        deadline.setToggleGroup(group);
        exam.setToggleGroup(group);
    }


    public void checkSelectedRadioButtons(){
        if(classRadio.isSelected()){
            System.out.println("Class");
            lectureGroup.setVisible(true);
        }
        if(schoolWork.isSelected()){
            lectureGroup.setVisible(false);
            System.out.println("Schoolwork");
        }
        if(deadline.isSelected()){
            lectureGroup.setVisible(false);
            System.out.println("deadline");
        }
        if(exam.isSelected()){
            lectureGroup.setVisible(false);
            System.out.println("Exam");
        }

    }

}
