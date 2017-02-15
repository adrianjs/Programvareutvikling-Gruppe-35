package layout;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Calendar extends Application {

	@Override
	public void start(Stage primStage) throws Exception {
		// TODO Auto-generated method stub
		Parent load = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
		primStage.setTitle("Educational Organizer");
		primStage.setScene(new Scene(load));
		primStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
