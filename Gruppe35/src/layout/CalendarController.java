package layout;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class CalendarController implements Initializable{
	
	
	//DAY PANE
	@FXML
	GridPane day;
	
	//WEEK PANE
	@FXML 
	GridPane week;
	
	//MONTH PANE
	@FXML 
	GridPane month;

	
	//Ask field
	@FXML
	TextField askField;
	
	//Anchorpane day
	@FXML
	AnchorPane dayAnchor;
	
	
	//Set lines for day week and month. 
	public void setLines(){
		day.setGridLinesVisible(true);
		week.setGridLinesVisible(true);
		month.setGridLinesVisible(true);
		
	}
	
	//Takes in the question from botto
	public void askBotto(){
		System.out.println("askBotto");
		String question = null;
		
		try{
			question = askField.getText();
			System.out.println(question);
			//Do something here with the question. 
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//On action from add button
	public void add(){
		System.out.println("add");
	}
	
	//On action from remove button
	public void remove(){
		
		System.out.println("remove");
	}
	
	//On action from O button
	public void O(){
		System.out.println("O");
	}
	
	//On action form IL
	public void IL(){
		System.out.println("IL");
	}
	
	//ON action from BB
	public void BB(){
		System.out.println("BB");
	}
	
	public void fitToParent(){
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setLines();
		System.out.println("yo ");
	}
}
