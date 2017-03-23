package layout;

import controllers.CalendarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by larsmade on 21.02.2017.
 */
public class Main extends Application{

    private Stage window;
    private Scene main;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        System.out.println(CalendarController.getInstance());
        Parent load = FXMLLoader.load(getClass().getResource("/resources/fxml/username.fxml"));
        primaryStage.setTitle("Educational Organizer");
        main = new Scene(load);
        primaryStage.setScene(main);
        primaryStage.getIcons().add(new Image((getClass().getResourceAsStream("/resources/img/EO.png"))));
        primaryStage.show();
        //The following code centers the window
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

    }
    public Stage getWindow(){
        return window;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
