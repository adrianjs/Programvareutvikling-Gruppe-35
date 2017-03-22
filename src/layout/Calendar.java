package layout;


import java.io.IOException;

import controllers.add.AddActivityController;
import controllers.add.AddSubjectController;
import controllers.WatchDayMonthTabController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Calendar extends Application {

	Stage stage;
	
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

	//Changing scene.
	public AddActivityController changeToAdd(String fxml, String name){
		AddActivityController add = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Parent load = loader.load();
			add = loader.getController();
			setStage(load, name);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return add;
	}

	public AddSubjectController changeToAddSubject(String fxml, String name){
		AddSubjectController addSubject = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Parent load = loader.load();
			addSubject = loader.getController();
			setStage(load, name);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addSubject;
	}

	public WatchDayMonthTabController changeToWatchDay(String fxml, String name){
		WatchDayMonthTabController day = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Parent load = loader.load();
			day = loader.getController();
			setStage(load, name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day;
	}

	public void setStage(Parent load, String name){
        Stage newStage = new Stage();
        newStage.setTitle(name);
        newStage.setScene(new Scene(load));
		newStage.getIcons().add(new Image((getClass().getResourceAsStream("../resources/EO.png"))));
        newStage.show();

    }
}
