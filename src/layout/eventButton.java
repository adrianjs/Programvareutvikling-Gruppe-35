package layout;

import algorithm.Activity;
import algorithm.SuperSorter;
import controllers.CalendarController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import database.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ThreadLocalRandom;
import calendar.Cell;

import static java.awt.Color.red;

/**
 * Created by torresrl on 22/03/2017.
 */
public class eventButton {

    private String description;
    private String name;
    private String subjectCode;
    private Button event;


    //Setter opp hvis det er activitet
    public eventButton(String name, String description, Cell cell){
        this.name = name;
        this.description = description;
        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText(name+ "\n\n" + description);
        event.setMaxHeight(Double.MAX_VALUE);
        event.setMaxWidth(Double.MAX_VALUE);
        event.setStyle("-fx-background-color: #" + ranColor());


        //vis du tryker på et event kommer mer info opp:
        event.setOnAction( e -> {
            //TODO når du tyker kommer opp nytt fxml vindu
            Stage stage = new Stage();
            Text nameFiled = new Text();

            nameFiled.setText(name);
            nameFiled.setFont(Font.font("Verdana", 20));
            nameFiled.setLayoutX(10);
            nameFiled.setLayoutY(25);

            TextArea descriptionFiled = new TextArea();
            descriptionFiled.setMaxWidth(450);
            descriptionFiled.setPrefHeight(450);

            descriptionFiled.setWrapText(true);
            descriptionFiled.setEditable(false);
            descriptionFiled.setFont(Font.font("Verdana", 15));
            descriptionFiled.setText(description);

            Button deleteBtn = new Button();
            deleteBtn.setId("deleteBtn");
            deleteBtn.setText("Delete");
            deleteBtn.setPrefWidth(Double.MAX_VALUE);
            deleteBtn.setOnAction( es -> {
                database.Activity delAct = new database.Activity();
                delAct.delteActivity(cell.getID());
                CalendarController.getInstance().refresh();
                stage.close();


            });

            VBox root = new VBox(nameFiled, descriptionFiled,deleteBtn);
            stage.setTitle(name);
            Scene scene = new Scene(root, 450, 450);
            String css = this.getClass().getResource("/resources/css/eventButton.css").toExternalForm();
            scene.getStylesheets().add(css);

            //root.getChildren().addAll(nameFiled,descriptionFiled);
            stage.setScene(scene);
            stage.show();

        });
    }

// setter opp hvis det er event
    public eventButton(String name, String description, String subjecCode, Cell cell){
        this.name = name;
        this.description = description;
        this.subjectCode = subjecCode;
        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText(name+ "\n\n" + description);
        event.setMaxHeight(Double.MAX_VALUE);
        event.setMaxWidth(Double.MAX_VALUE);
        event.setStyle("-fx-background-color: #" +ranColor());

        //vis du tryker på et event kommer mer info opp:
        event.setOnAction( e -> {
            //TODO når du tyker kommer opp nytt fxml vindu
            Stage stage = new Stage();


            Text nameFiled = new Text();
            nameFiled.setText(name);
            nameFiled.setFont(Font.font("Verdana", 20));
            nameFiled.setLayoutX(10);
            nameFiled.setLayoutY(25);



            Text subjectFild = new Text();
            subjectFild.setFont(Font.font("Verdana", 15));
            subjectFild.setText("Subject Code: " + subjecCode);
            subjectFild.setLayoutX(10);
            subjectFild.setLayoutY(55);

            TextArea descriptionFiled = new TextArea();
            descriptionFiled.setMaxWidth(450);
            descriptionFiled.setPrefHeight(450);

            descriptionFiled.setWrapText(true);
            descriptionFiled.setEditable(false);
            descriptionFiled.setFont(Font.font("Verdana", 15));
            descriptionFiled.setText(description);

            Button deleteBtn = new Button();
            deleteBtn.setId("deleteBtn");
            deleteBtn.setText("Delete");
            deleteBtn.setPrefWidth(Double.MAX_VALUE);
            deleteBtn.setOnAction( es -> {
                database.Event delEv = new database.Event();
                delEv.deleteEvent(cell.getID(), User.getInstance().getUsername());
                CalendarController.getInstance().refresh();
                stage.close();

            });


            VBox root = new VBox(nameFiled,subjectFild, descriptionFiled, deleteBtn);
            stage.setTitle(name);
            Scene scene = new Scene(root, 450, 450);
            String css = this.getClass().getResource("/resources/css/eventButton.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setScene(scene);
            stage.show();

        });
    }

    //returns the button that wil be shown in the calender
    public Button getEvent(){
        return event;
    }

    public String getName() {return name;}

    public String getDescription() {return description;}

    private String ranColor(){
        String [] colors = {"F44336","E91E63","9C27B0","673AB7","3F51B5","2196F3","03A9F4","009688"};
        int randomNum = ThreadLocalRandom.current().nextInt(0,  7);
        return colors[randomNum];
    }












}
