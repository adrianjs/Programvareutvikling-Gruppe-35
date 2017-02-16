package layout;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Calendar extends Application {

	Stage stage;
	
	//Launch the scene. 
	@Override
	public void start(Stage primStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primStage;
		Parent load = FXMLLoader.load(getClass().getResource("Calendar.fxml"));
		primStage.setTitle("Educational Organizer");
		primStage.setScene(new Scene(load));
		primStage.show();
		
	}
	//Changing scene.
	public void changeScene(String fxml, String name){
		System.out.println("hei");
		try {
			Parent load = FXMLLoader.load(getClass().getResource(fxml));
			Stage st = new Stage();
			st.setTitle(name);
			st.setScene(new Scene(load));
			
			st.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("funker ikke");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
