package layout;

import javafx.fxml.FXMLLoader;

import javax.sound.midi.ControllerEventListener;
import javax.sound.sampled.Control;

/**
 * Created by larsmade on 24.02.2017.
 */
import layout.AddController;

/* --------------------------------------- This class was planned to controll the controlclasses if there is many ----*/
/* May not neeeded */
public class Controller{
    AddController add;
    AddSubjectController addSubject;

    public void setAddContoller(AddController controller){
        this.add = controller;
        System.out.println(add + "hop");

    }

    public void setSubjectController(AddSubjectController controller){
        this.addSubject = controller;

    }

}
