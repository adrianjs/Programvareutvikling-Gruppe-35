package layout;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by larsmade on 02.03.2017.
 */
public class WatchDayMonthTabController implements Initializable{


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("watch this day");
        //System.out.println(dayTabLabels.size() + "storrelse");
        //System.out.println(chosenDate + "dato");
        System.out.println(CalendarController.getInstance());



    }

}
