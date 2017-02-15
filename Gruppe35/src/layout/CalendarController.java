package layout;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class CalendarController implements Initializable{
	
	
	@FXML
	GridPane pane1;
	
	
	public void setLines(){
		pane1.setGridLinesVisible(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setLines();
	}

}
