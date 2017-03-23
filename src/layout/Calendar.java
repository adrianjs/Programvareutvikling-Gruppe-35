package layout;


import java.io.IOException;

import controllers.add.AddActivityController;
import controllers.add.AddSubjectController;
import controllers.WatchDayMonthTabController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Calendar extends Application {

	Stage stage;
	FXMLLoader loader;
	Parent load;

	
	//Launch the scene. 
	@Override
	public void start(Stage primStage) throws Exception {
		// TODO Auto-generated method stub
		this.stage = primStage;
		/*Parent load = FXMLLoader.load(getClass().getResource("calendar.fxml"));
		primStage.setTitle("Educational Organizer");
		primStage.setScene(new Scene(load));
		primStage.show();*/
		
	}
    public static void main(String[] args) {
        launch(args);
    }

	/**
	 * Changes the scene
	 * @param fxml
	 * @param name
	 * @throws IOException
	 */
	public void changeScene(String fxml, String name) throws IOException {
		loading(fxml);
		setStage(load, name);
	}

	/**
	 * Loads the new FXML Document
	 * @param fxml
	 * @throws IOException
	 */
	public void loading(String fxml) throws IOException {
		loader = new FXMLLoader(getClass().getResource(fxml));
		load = loader.load();
	}

	public void setStage(Parent load, String name){
		Stage newStage = new Stage();
		newStage.setTitle(name);
		newStage.setScene(new Scene(load));
		newStage.getIcons().add(new Image((getClass().getResourceAsStream("../resources/EO.png"))));
		newStage.show();
		//The following code centers the window
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		newStage.setX((primScreenBounds.getWidth() - newStage.getWidth()) / 2);
		newStage.setY((primScreenBounds.getHeight() - newStage.getHeight()) / 2);
	}

//*************************RETURNS CONTROLLER TO CALENDARCONTROLLER ******* NOT IN USE ATM///
	/*public AddActivityController changeToAdd(String fxml, String name){
		AddActivityController add = null;
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//			Parent load = loader.load();
			loading(fxml);
			add = loader.getController();
			setStage(load, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return add;

	}

	public AddSubjectController changeToAddSubject(String fxml, String name){
		AddSubjectController addSubject = null;
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//			Parent load = loader.load();
			loading(fxml);
			addSubject = loader.getController();
			setStage(load, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addSubject;
	}

	public WatchDayMonthTabController changeToWatchDay(String fxml, String name){
		WatchDayMonthTabController day = null;
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//			Parent load = loader.load();
			loading(fxml);
			day = loader.getController();
			setStage(load, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return day;
	}
*/

}
