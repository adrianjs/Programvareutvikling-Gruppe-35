package layout;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;

/**
 * Created by larsmade on 02.03.2017.
 */
public class WatchDayMonthTabController implements Initializable{
    CalendarController cal;
    int day;
    @FXML JFXListView listVeiw;
    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cal = CalendarController.getInstance();
        for (Map.Entry<Label, calendar.Cell> entry : cal.labelMappedCells.entrySet())
        {
            System.out.println(entry.getKey().getText());
            list.add(entry.getKey().getText());

        }
        if(list.size() == 0){
            list.add("No activities set this day");
        }
        listVeiw.setItems(list);
    }
}
