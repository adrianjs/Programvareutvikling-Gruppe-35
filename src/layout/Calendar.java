package layout;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Calendar extends Application {

	private Stage stage;
	private FXMLLoader loader;
	private Parent load;

	//Launch the scene.
	@Override
	public void start(Stage primStage) throws Exception {
		this.stage = primStage;
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

	/**
	 * Sets the new stage and show it.
	 * @param load
	 * @param name
	 */
	public void setStage(Parent load, String name){
		Stage newStage = new Stage();
		newStage.setTitle(name);
		newStage.setScene(new Scene(load));
		newStage.getIcons().add(new Image((getClass().getResourceAsStream("/img/EO.png"))));
		newStage.show();
		//The following code centers the window
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		newStage.setX((primScreenBounds.getWidth() - newStage.getWidth()) / 2);
		newStage.setY((primScreenBounds.getHeight() - newStage.getHeight()) / 2);
	}
}
