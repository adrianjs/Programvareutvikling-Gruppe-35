package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import layout.FeedbackElement;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by torresrl on 24/04/2017.
 */
public class FeedbackControllerTest {
    FeedbackController con;

    @Before
    public void setup() {

        new JFXPanel();
        con = new FeedbackController();
        con.table = new TableView<FeedbackElement>();

        con.date      = new TableColumn<FeedbackElement, String>();
        con.estHour   = new TableColumn<FeedbackElement, String>();
        con.feedBack  = new TableColumn<FeedbackElement, String>();
        con.desc      = new TableColumn<FeedbackElement, String>();

        con.subjectsDropDown = new JFXComboBox();
        con.checkButton = new JFXButton();

        con.events = new ArrayList<ArrayList<String>>();


        con.initialize(null,null);

    }

    @Test
    public void testCheck() throws SQLException{
        con.check();
        assertEquals(0, con.table.getItems().size());
    }





}
