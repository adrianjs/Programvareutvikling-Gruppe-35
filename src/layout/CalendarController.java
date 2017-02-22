package layout;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.GregorianCalendar;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import layout.*;

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


	//AI panes.
	@FXML
	SplitPane wholeField;
	@FXML
	AnchorPane leftSide;
	@FXML
	AnchorPane rightSide;

	//Slide button
	@FXML
	Button sliderButton;

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
	@FXML
	Label thisday;

	//Labels week-Pane

	//Labels month pane
	@FXML
	Label day1;
	@FXML
	AnchorPane monthBox1;

	@FXML
	Label day2;
	@FXML
	Label day3;
	@FXML
	Label day4;
	@FXML
	Label day5;
	@FXML
	Label day6;
	@FXML
	Label day7;
	@FXML
	Label day8;
	@FXML
	Label day9;
	@FXML
	Label day10;
	@FXML
	Label day11;
	@FXML
	Label day12;
	@FXML
	Label day13;
	@FXML
	Label day14;
	@FXML
	Label day15;
	@FXML
	Label day16;
	@FXML
	Label day17;
	@FXML
	Label day18;
	@FXML
	Label day19;
	@FXML
	Label day20;
	@FXML
	Label day21;
	@FXML
	Label day22;
	@FXML
	Label day23;
	@FXML
	Label day24;
	@FXML
	Label day25;
	@FXML
	Label day26;
	@FXML
	Label day27;
	@FXML
	Label day28;
	@FXML
	Label day29;
	@FXML
	Label day30;
	@FXML
	Label day31;
	@FXML
	Label day32;
	@FXML
	Label day33;
	@FXML
	Label day34;
	@FXML
	Label day35;

	//Date
	@FXML
	JFXDatePicker date;

	//To set a new scene.
	layout.Calendar cal = new layout.Calendar();

	//List to add the month labels.
	@FXML
	List<Label> labels = new ArrayList<>();
	//Counter to only add the labels to the labels list once.
	int teller = 0;

	//UserInformation
	User user = new User();


	//Methods starts here.

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
			// --> Do something here with the question.

		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//On action from add button
	public void add(){
		//Makes the new add scene.
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
	
	//On action form IL --> Currenly no IL button on fxml-file
	public void IL(){


		System.out.println("IL");
	}
	
	//ON action from BB --> Currently no BB butto on fxml-file.
	public void BB(){

		System.out.println("BB");
	}
	
	//Slides the Botto field for bigger table layout
	int yo = 0;
	public void slidePane(){
		
		//Make sidebar invisible when minimized. 
		if(yo == 0){
			
			time.setText("");
			botto1.setText("");
			botto2.setText("");
			//askBotto.setText("Open slide to ask (b)Otto a question");
			wholeField.setPrefWidth(50);
			leftSide.setMaxWidth(0);
			leftSide.setMinWidth(0);
			//askField.setVisible(false);
			sliderButton.setText(">");
			//sendInButton.setDisable(true);
			yo++;

		}else{
			
			//Make it visible again. 
			wholeField.setPrefWidth(300);
			leftSide.setMinWidth(250);
			time.setText("12.48");
			botto1.setText("Hello i am b(O)tto!");
			botto2.setText("How can i help you?");
			//askBotto.setText("Ask b(O)tto:");
			//askField.setVisible(true);
			sliderButton.setText("<");
			//sendInButton.setDisable(false);
			yo--;
		}
		
		
	}

	//Set new date from calendar
	public void setNewDate(){
		LocalDate dato = date.getValue();
		changeDate(dato);
	}



	//Started to make methods to change veiw when minicalendar is changed.
	public void changeDate(LocalDate dato){


		System.out.println("Date changed");



        String dato1 = dato.toString();
		thisday.setText(dato1);
        String[] split = dato1.split("-");
        String year = split[0];
		String month = split[1];
		String day = split[2];
		//Check if first element in month and day has 0 first, and then cut it out.
		int year1 = Integer.parseInt(year);
		int month1 = removeZero(month);
		int day1 = removeZero(day);

		if(teller == 0){
			addLabelsToList();
			teller++;
		}

		monthOrganizer(year1, month1-1, day1);

        //Make methods to get values to put in calendar. for the given day.
	}

	//Remove zero from string if it starts whith it.
	public int removeZero(String tall){

		if(tall.charAt(0) == '0'){
			return Character.getNumericValue(tall.charAt(1));
		}
		return Integer.parseInt(tall);
	}




	public void addLabelsToList(){
		labels.add(day1);
		labels.add(day2);
		labels.add(day3);
		labels.add(day4);
		labels.add(day5);
		labels.add(day6);
		labels.add(day7);
		labels.add(day8);
		labels.add(day9);
		labels.add(day10);
		labels.add(day11);
		labels.add(day12);
		labels.add(day13);
		labels.add(day14);
		labels.add(day15);
		labels.add(day16);
		labels.add(day17);
		labels.add(day18);
		labels.add(day19);
		labels.add(day20);
		labels.add(day21);
		labels.add(day22);
		labels.add(day23);
		labels.add(day24);
		labels.add(day25);
		labels.add(day26);
		labels.add(day27);
		labels.add(day28);
		labels.add(day29);
		labels.add(day30);
		labels.add(day31);
		labels.add(day32);
		labels.add(day33);
		labels.add(day34);
		labels.add(day35);


	}

	//Set labels on month part to the right month.
	public void monthOrganizer(int year, int month, int day){
		System.out.println(year + " " + month + " " + day);
		java.util.Calendar cal = new GregorianCalendar(year, month, day);
		//Calendar cal = Calendar.getInstance();
		int yea = cal.get(java.util.Calendar.YEAR);
		int monthe = cal.get(java.util.Calendar.MONTH);
		int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int weekOfYear = cal.get(java.util.Calendar.WEEK_OF_YEAR);
		int weekOfMonth = cal.get(java.util.Calendar.WEEK_OF_MONTH);

		//Gets the fist day at the week. --> Not done yet...
		int firstDay = 2;
		int lastDateOfMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

		System.out.println(firstDay + " første dag");
		System.out.println(lastDateOfMonth + " siste dag");

		//Setting the month dates to right place.
		int tall = 1;
		System.out.println(labels.size());
		for(int i = 0; i < labels.size(); i++) {
			if ((i >= firstDay) && (tall <= lastDateOfMonth)) {
				labels.get(i).setText(Integer.toString(tall));
				//System.out.println("yes");
				tall++;
				//System.out.println("no");
			} else {
				labels.get(i).setText("");
			}
		}
	}

	//GetLabelSoMonthOrganizerCanChangeiy
	public void StringGetLabel(String label){
		System.out.println("yo");
	}


	//Go to that given day when month is clicked.
	public void monthClicked(){

		System.out.println("MonthClicked");
	}

	//Set initalize date.
	int count = 0;
	public void setDate(){
		if(count == 0){
			Date input = new Date();
			LocalDate dato = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			changeDate(dato);
			count++;
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setLines();

	}

}
