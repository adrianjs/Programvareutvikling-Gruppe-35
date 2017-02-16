package layout;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
	@FXML
	VBox leftPane;

	//Labels for leftPane --> BOTTOFIELD
	@FXML
	Label botto1;
	@FXML
	Label botto2;
	@FXML
	Label time;
	@FXML
	Label askBotto;
	@FXML
	Button sendInButton;


	//Labels for Day pane.
	@FXML
	Label eightToNine;
	@FXML
	Label nineToTen;
	@FXML
	Label tenToEleven;
	@FXML
	Label elevenToTwelve;
	@FXML
	Label twelveToThirteen;
	@FXML
	Label thirteenToFourteen;
	@FXML
	Label fourteenTiFifthteen;
	@FXML
	Label fifthteenToSixteen;
	@FXML
	Label sixteenToSeventeen;
	@FXML
	Label seventeenToEighteen;
	@FXML
	Label eighteenToNineTeen;
	@FXML
	Label nineteenToTwenty;

	//Date
	@FXML
	DatePicker date;

	Calendar cal = new Calendar();

	
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
		cal.changeScene("Add.fxml", "ADD");
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
	
	//Slides the Botto field for bigger table layout
	int yo = 0;
	public void slidePane(){
		
		//Make sidebar invisible when minimized. 
		if(yo == 0){
			
			time.setText("");
			botto1.setText("");
			botto2.setText("");
			askBotto.setText("");
			leftPane.setPrefWidth(10);
			askField.setVisible(false);
			//sendInButton.setDisable(true);
			yo++;
		}else{
			
			//Make it visible again. 
			leftPane.setPrefWidth(265);
			time.setText("12.48");
			botto1.setText("Hello i am b(O)tto!");
			botto2.setText("How can i help you?");
			askBotto.setText("Ask b(O)tto:");
			askField.setVisible(true);
			//sendInButton.setDisable(false);
			yo--;
		}
		
		
	}


	//Started to make methods to change veiw when minicalendar is changed.
	public void changeDate(){
		System.out.println("Date changed");
        LocalDate dato = date.getValue();
        System.out.println(dato);

        //Make methods to get values to put in calendar. for the given day.
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setLines();
	}
}
