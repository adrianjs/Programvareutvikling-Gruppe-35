package layout;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 08.03.2017.
 */
public class TeacherCalendarController implements Initializable{

    @FXML JFXButton addEvent;
    @FXML JFXButton addStudass;
    @FXML JFXDrawer drawer;
    @FXML JFXDrawer drawer2;
    AnchorPane add;
    AnchorPane studassAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAddField();
    }

    private static TeacherCalendarController instance = null; //InstanceControl
    public static TeacherCalendarController getInstance() {
        if (instance == null) {
            instance = new TeacherCalendarController();
        }
        return instance;
    }

    public void addEvent() {
        System.out.println("Add event");
        //Slides the Botto field for bigger table layout
        addPane(1);
    }

    /**
     * Set fxml documents to JFXdrawers.
     */
    public void setAddField(){
        try {
            add = FXMLLoader.load(getClass().getResource("../resources/addEvent.fxml"));
            studassAdd = FXMLLoader.load(getClass().getResource("../resources/addStudass.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open and closes drawers(Fxml documtents).
     * @param number get number from on buttons onaction methods
     */
    public void addPane(int number){
        if(number == 1){
            drawer2.close();
            drawer.setSidePane(add);
            add.toFront();
            if(drawer.isShown()){

                drawer.close();
            }else{
                drawer.open();
                drawer.toFront();
            }
        }
        if(number == 2){
            drawer2.setSidePane(studassAdd);
            drawer.close();
            if(drawer2.isShown()){
                drawer2.close();
            }else{
                drawer2.open();
                drawer2.toFront();
            }
        }
    }

    public void addStudass(){
        System.out.println("add studass");
        addPane(2);

    }

}
