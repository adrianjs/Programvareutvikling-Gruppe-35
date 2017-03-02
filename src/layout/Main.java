package layout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by larsmade on 21.02.2017.
 */
public class Main extends Application{

    Stage window;
    Scene main;
    CalendarController cal = new CalendarController();

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        System.out.println(CalendarController.getInstance());
        Parent load = FXMLLoader.load(getClass().getResource("../resources/Username.fxml"));


        Parent load = FXMLLoader.load(getClass().getResource("../resources/username.fxml"));
        primaryStage.setTitle("Educational Organizer");
        main = new Scene(load);
        primaryStage.setScene(main);
        primaryStage.show();

    }
    public Stage getWindow(){
        return window;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
