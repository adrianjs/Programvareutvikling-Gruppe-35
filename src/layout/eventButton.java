package layout;

import calendar.Cell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import controllers.CalendarController;
import database.Event;
import database.Teacher;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by torresrl on 22/03/2017.
 */
public class eventButton {

    private String description;
    private String name;
    private String subjectCode;
    private String coordinator;
    Button event;
    String eventType = "";
    private int eventId = 0;


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
        event.setStyle("-fx-background-color: #" + cell.getColor());


        //vis du tryker på et event kommer mer info opp:
        event.setOnAction( e -> {


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


            Stage stage = new Stage();
            deleteBtn.setOnAction( es -> {
                database.Activity delAct = new database.Activity();
                delAct.deleteActivity(cell.getID());
                CalendarController.getInstance().refresh();
                stage.close();


            });

            VBox root = new VBox(nameFiled, descriptionFiled,deleteBtn);
            root.setPadding(new Insets(5,5,5,5));
            root.setMargin(nameFiled, new Insets(0,0,8,0));
            root.setMargin(descriptionFiled, new Insets(0,0,8,0));
            stage.setTitle(name);
            stage.getIcons().add(new javafx.scene.image.Image((getClass().getResourceAsStream("/resources/img/EO.png"))));
            Scene scene = new Scene(root, 450, 450);
            String css = this.getClass().getResource("/resources/css/eventButton.css").toExternalForm();
            scene.getStylesheets().add(css);

            //root.getChildren().addAll(nameFiled,descriptionFiled);
            stage.setScene(scene);
            stage.show();

        });
    }

// setter opp hvis det er event
    public eventButton(String name, String description, String subjecCode, Cell cell, int id){
        //TODO: Get event ID here.
        this.name = name;
        this.description = description;
        this.subjectCode = subjecCode;
        this.eventId = id;
        Teacher coor = new Teacher();
        this.coordinator = coor.getTeacher(subjecCode);
        coor.close();


        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText(name + "  -  " + subjecCode + "\n\n" + description);
        if(cell.getSlotPriority() == 98){
            event.setMaxWidth(Double.MAX_VALUE);
            event.setMaxHeight(Double.MIN_VALUE);

        } else {
            event.setMaxHeight(Double.MAX_VALUE);
            event.setMaxWidth(Double.MAX_VALUE);

        }

        if(cell.getSlotPriority() == 99){
            eventType = "Exam";
        } else if(cell.getSlotPriority() == 98) {
            eventType = "Deadline";
        } else if(cell.getSlotPriority() == 97) {
            eventType = "home work";
        } else if(cell.getSlotPriority() == 96) {
            eventType = "Lecture";
        } else if(cell.getSlotPriority() == 95) {
            eventType = "Home exam";
        }

        event.setStyle("-fx-background-color: #" +cell.getColor());

        //vis du tryker på et event kommer mer info opp:
        event.setOnAction( e -> {

            Stage stage = new Stage();


            Text nameFiled = new Text();
            nameFiled.setText(name);
            nameFiled.setFont(Font.font("Verdana", 20));
            nameFiled.setLayoutX(10);
            nameFiled.setLayoutY(25);

            Text type = new Text();
            type.setFont(Font.font("Verdana", 15));
            type.setText("Event type: " + eventType);
            type.setLayoutX(10);
            type.setLayoutY(55);

            Text subjectFild = new Text();
            subjectFild.setFont(Font.font("Verdana", 15));
            subjectFild.setText("Subject Code: " + subjecCode);
            subjectFild.setLayoutX(10);
            subjectFild.setLayoutY(55);

            Text coordinatorText = new Text();
            coordinatorText.setFont(Font.font("Verdana", 10));
            coordinatorText.setText("contact info: " + coordinator);

            TextArea descriptionFiled = new TextArea();
            descriptionFiled.setMaxWidth(450);
            descriptionFiled.setPrefHeight(450);

            descriptionFiled.setWrapText(true);
            descriptionFiled.setEditable(false);
            descriptionFiled.setFont(Font.font("Verdana", 15));
            descriptionFiled.setText(description);


            //FEEDBACKMULIGHET FOR SKOLEARBEID.
            VBox vbox = new VBox();
            vbox.setStyle("-fx-background-color: white;");
            vbox.setPadding(new Insets(8,8,8,8));

            //If event is schoolwork, student should be able to say how long they used on it.
            if(eventType.contains("home work")){
                Label lab = new Label();
                lab.setStyle("-fx-font-weight: bold;");
                Label errorLab = new Label();
                errorLab.setStyle("-fx-text-fill: red;");
                lab.setText("Hours spent on schoolwork:");
                JFXComboBox combo = new JFXComboBox((FXCollections.observableArrayList("1", "2", "3", "4", "5")));
                combo.setPrefWidth(Double.MAX_VALUE);
                combo.setStyle("-fx-background-color: #d0d3d8;");
                JFXButton feedback = new JFXButton();
                feedback.setId("feedback");
                feedback.setText("Send feedback");
                feedback.setStyle("-fx-background-color: #d0d3d8;");
                feedback.setPrefWidth(Double.MAX_VALUE);
                feedback.setOnAction(fd -> {
                    errorLab.setText("");
                    String hours;
                    try{
                        hours = combo.getValue().toString();
                        boolean work = new Event().schoolWorkFeedBack(eventId, User.getInstance().getUsername(), Integer.parseInt(hours));
                        if(!work){
                            errorLab.setText("Can only give feedback once");
                        }else{
                            errorLab.setText("Feedback sent");
                        }
                    }catch (Exception exe){
                        errorLab.setText("Must set a value befor you send in");
                    }
                });
                vbox.getChildren().addAll(lab, combo, feedback, errorLab);
                vbox.setMargin(lab, new Insets(0,0,8,0));
                vbox.setMargin(combo, new Insets(0,0,8,0));
                vbox.setMargin(feedback, new Insets(0,0,8,0));
            }


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

            VBox root;
            if (eventType.contains("home work")){
                root = new VBox(nameFiled,subjectFild,type,coordinatorText, descriptionFiled, vbox, deleteBtn);
            }
            else{
                root = new VBox(nameFiled,subjectFild,type,coordinatorText, descriptionFiled, deleteBtn);
            }
            root.setPadding(new Insets(5,5,5,5));
            root.setMargin(nameFiled, new Insets(0,0,8,0));
            root.setMargin(subjectFild, new Insets(0,0,8,0));
            root.setMargin(type, new Insets(0,0,8,0));
            root.setMargin(descriptionFiled, new Insets(0,0,8,0));
            stage.setTitle(name);
            Scene scene = new Scene(root, 450, 450);
            String css = this.getClass().getResource("/resources/css/eventButton.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.getIcons().add(new javafx.scene.image.Image((getClass().getResourceAsStream("/resources/img/EO.png"))));
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














}
