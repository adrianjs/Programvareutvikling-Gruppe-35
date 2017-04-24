package controllers;

import algorithm.Event;
import algorithm.SuperSorter;
import calendar.Cell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import database.Connect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import layout.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Henning on 04.04.2017.
 */
public class RestoreController implements Initializable{
    @FXML JFXButton restoreChosen;
    @FXML JFXButton restoreAll;
    @FXML JFXButton cancel;
    @FXML ScrollPane eventScrollPane;

    private Connect connect = Connect.getInstance();
    private SuperSorter superSorter = new SuperSorter();
    private List<Integer> eventIds = new ArrayList<>();
    private List<JFXCheckBox> checkBoxes = new ArrayList<>();
    private Set<Cell> events = new LinkedHashSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connect.stmt = connect.conn.createStatement();
            ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM NOTATTENDINGEVENT WHERE studentEmail='"+ User.getInstance().getUsername()+"'");
            while(m_result_set.next()){
                eventIds.add(m_result_set.getInt(1));
            }
            String values = "";
            for(Integer i : eventIds){
                values += "'" + i.toString() + "',";
            }
            values = values.substring(0, values.length()-1);
            m_result_set = connect.stmt.executeQuery("SELECT * FROM EVENT WHERE eventID IN ("+values+")");
            VBox vBox = new VBox();
            while(m_result_set.next()){
                events.add(new Event(m_result_set.getDate(3),
                        m_result_set.getDate(4),
                        m_result_set.getString(5),
                        m_result_set.getString(6),
                        m_result_set.getString(2),
                        m_result_set.getString(9),
                        m_result_set.getInt(8),
                        m_result_set.getBoolean(7),
                        m_result_set.getInt(10),
                        m_result_set.getString(11),
                        m_result_set.getInt(1),
                        m_result_set.getString(12)));
                JFXCheckBox check = new JFXCheckBox(m_result_set.getString(2) + "    " + m_result_set.getString(3) + "    " + m_result_set.getString(9));
                check.setId(String.valueOf(m_result_set.getInt(1)));
                checkBoxes.add(check);
                vBox.getChildren().add(check);
            }
            eventScrollPane.setContent(vBox);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void restoreChosen() throws SQLException, ParseException, IOException {
        List<CheckBox> selectedBoxes = new ArrayList<>();
        for(CheckBox box : checkBoxes){
            if(box.isSelected()){
                selectedBoxes.add(box);
            }
        }
        checkBoxes.removeAll(selectedBoxes);
        for(CheckBox box : selectedBoxes){
            for(Cell event : events){
                if(box.getId().equals(String.valueOf(event.getID()))){
                    superSorter.resetDroppedEvent(event);
                }
            }
        }

        Stage stage = (Stage) restoreAll.getScene().getWindow();
        stage.close();
        superSorter.run();
    }

    public void restoreAll() throws SQLException, ParseException, IOException {
        superSorter.resetDroppedEvents();
        Stage stage = (Stage) restoreAll.getScene().getWindow();
        stage.close();
        superSorter.run();
    }

    public void cancel(){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
