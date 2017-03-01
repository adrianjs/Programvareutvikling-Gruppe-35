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


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Parent load = FXMLLoader.load(getClass().getResource("../../resources/Username.fxml"));
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
