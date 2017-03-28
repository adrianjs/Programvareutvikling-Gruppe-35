package layout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by torresrl on 22/03/2017.
 */
public class eventButton {

    private String description;
    private String name;
    private String subjectCode;
    private Button event;


    //Setter opp hvis det er activitet
    public eventButton(String name, String description){
        this.name = name;
        this.description = description;
        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText("Activity\n" +name+ "\n\nDeskription\n" + description);
        event.setMaxHeight(Double.MAX_VALUE);
        event.setMaxWidth(Double.MAX_VALUE);

        //vis du tryker på et event kommer mer info opp:
        event.setOnAction( e -> {
            //TODO når du tyker kommer opp nytt fxml vindu
            Stage stage = new Stage();
            Group root = new Group();
            stage.setTitle(name);
            Scene scene = new Scene(root, 450, 450);

            Text nameFiled = new Text();
            nameFiled.setText(name);
            nameFiled.setFont(Font.font("Verdana", 20));
            nameFiled.setLayoutX(10);
            nameFiled.setLayoutY(25);

            Text descriptionFiled = new Text();
            descriptionFiled.setFont(Font.font("Verdana", 15));
            descriptionFiled.setText(description);
            descriptionFiled.setLayoutX(10);
            descriptionFiled.setLayoutY(55);


            root.getChildren().addAll(nameFiled,descriptionFiled);
            stage.setScene(scene);
            stage.show();

        });
    }

// setter opp hvis det er event
    public eventButton(String name, String description, String subjecCode){
        this.name = name;
        this.description = description;
        this.subjectCode = subjecCode;
        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText("Activity\n" +name+ "\n\nDeskription\n" + description);
        event.setMaxHeight(Double.MAX_VALUE);
        event.setMaxWidth(Double.MAX_VALUE);

        //vis du tryker på et event kommer mer info opp:
        event.setOnAction( e -> {
            //TODO når du tyker kommer opp nytt fxml vindu
            Stage stage = new Stage();
            Group root = new Group();
            stage.setTitle(name);
            Scene scene = new Scene(root, 450, 450);

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

            Text descriptionFiled = new Text();
            descriptionFiled.setFont(Font.font("Verdana", 15));
            descriptionFiled.setText(description);
            descriptionFiled.setLayoutX(10);
            descriptionFiled.setLayoutY(70);


            root.getChildren().addAll(nameFiled,descriptionFiled);
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
