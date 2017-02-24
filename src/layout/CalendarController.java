package layout;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import calendar.*;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Cell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import layout.*;

public class CalendarController implements Initializable{
	private Date chosenDate;

	@FXML GridPane day; //DAY PANE
	@FXML GridPane week; //WEEK PANE
	@FXML GridPane month; //MONTH PANE
	@FXML TextField askField;//Ask field
	@FXML AnchorPane dayAnchor; //Anchorpane day

	//AI panes.
	@FXML SplitPane wholeField;
	@FXML AnchorPane leftSide;
	@FXML AnchorPane rightSide;

	@FXML Button sliderButton; //Slide button

	//Labels for leftPane --> BOTTOFIELD
	@FXML Label botto1;
	@FXML Label botto2;
	@FXML Label time;
	@FXML Label askBotto;
	@FXML Button sendInButton;

	//Labels for Day pane.
	@FXML Label eightToNine;
	@FXML Label nineToTen;
	@FXML Label tenToEleven;
	@FXML Label elevenToTwelve;
	@FXML Label twelveToThirteen;
	@FXML Label thirteenToFourteen;
	@FXML Label fourteenTiFifthteen;
	@FXML Label fifthteenToSixteen;
	@FXML Label sixteenToSeventeen;
	@FXML Label seventeenToEighteen;
	@FXML Label eighteenToNineTeen;
	@FXML Label nineteenToTwenty;
	@FXML Label thisday;

	//Labels week-Pane

	//Labels month pane
	@FXML Label day1;
	@FXML AnchorPane monthBox1;

	@FXML Label day2;
	@FXML Label day3;
	@FXML Label day4;
	@FXML Label day5;
	@FXML Label day6;
	@FXML Label day7;
	@FXML Label day8;
	@FXML Label day9;
	@FXML Label day10;
	@FXML Label day11;
	@FXML Label day12;
	@FXML Label day13;
	@FXML Label day14;
	@FXML Label day15;
	@FXML Label day16;
	@FXML Label day17;
	@FXML Label day18;
	@FXML Label day19;
	@FXML Label day20;
	@FXML Label day21;
	@FXML Label day22;
	@FXML Label day23;
	@FXML Label day24;
	@FXML Label day25;
	@FXML Label day26;
	@FXML Label day27;
	@FXML Label day28;
	@FXML Label day29;
	@FXML Label day30;
	@FXML Label day31;
	@FXML Label day32;
	@FXML Label day33;
	@FXML Label day34;
	@FXML Label day35;

	//Tabs
	@FXML Tab dayTab;
	@FXML Tab weekTab;
	@FXML Tab monthTab;


	//Date
	@FXML JFXDatePicker date;

	//To set a new scene.
	layout.Calendar cal = new layout.Calendar();

	//List to add the month labels.
	@FXML List<Label> labels = new ArrayList<>();

	//Counter to only add the labels to the labels list once.

    // List of labels in day Tab...
    @FXML List<Label> timeToTime = new ArrayList<>();

    //CountVariables
    int teller = 0;
    int slideCount = 0;
    User user = new User();

	//*************** HENNINGS ULTRAFELT *****************//
	List<Label> dayTabLabels = new ArrayList<>(); // Hentes fra GUI
	List<calendar.Cell> cellsAtCurrentDate = new ArrayList<>(); // Skal fylles fra database

	Map<TimeInterval, Label> dayTabTimeSlots = new LinkedHashMap<>();
	Map<Label, calendar.Cell> labelMappedCells = new LinkedHashMap<>(); //Ferdig mappet celler til labels

	//****************************************************//
    //Methods starts here.

	public void setLines(){ //Set lines for day week and month.
		day.setGridLinesVisible(true);
		week.setGridLinesVisible(true);
		month.setGridLinesVisible(true);
	}
	
    public void askBotto(){ //Takes in the question from botto
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
        //TODO: Make remove button work
		System.out.println("remove");
	}
	
	//On action from addsubject button
	public void addSubject(){
		cal.changeScene("addSubject.fxml", "Add subject");
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
    public void slidePane(){
		
		//Make sidebar invisible when minimized. 
		if(slideCount == 0){
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
			slideCount++;

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
			slideCount--;
		}
		
		
	}

	//Set new date from calendar
	public void setNewDate(){
		LocalDate dato = date.getValue();
		changeDate(dato);
		chosenDate = Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant());

	}



	//Started to make methods to change veiw when minicalendar is changed.
	public void changeDate(LocalDate dato){

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

	//Add labels tin day tab to list
    public void addTimeToTimeToList(){
		ObservableList<Node> children = day.getChildren();
		for(Node label : children){
			if((label.getId() != null) && (!label.getId().equals(thisday.getId()))){
				timeToTime.add((Label) label);
				dayTabLabels.add((Label) label);
			}
		}

		//TODO: Map the Labels to corresponding TimeIntervals
		int hour = 8;
		for(Label label : dayTabLabels){
			Date start = setHour(chosenDate, hour);
			Date end = setHour(chosenDate, hour+1);
			dayTabTimeSlots.put(new TimeInterval(start, end), label);
			hour++;
		}
		System.out.println(dayTabTimeSlots.values());
		System.out.println(dayTabTimeSlots.keySet());

	}

    //Add labels in month tab to list..
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

    public void monthOrganizer(int year, int month, int day){ //Set labels on month part to the right month.
		System.out.println(year + " " + month + " " + day);
		java.util.Calendar cal = new GregorianCalendar(year, month, day);
		//Calendar cal = Calendar.getInstance();
		int yea = cal.get(java.util.Calendar.YEAR);
		int monthe = cal.get(java.util.Calendar.MONTH);
		int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int weekOfYear = cal.get(java.util.Calendar.WEEK_OF_YEAR);
		int weekOfMonth = cal.get(java.util.Calendar.WEEK_OF_MONTH);


		int firstDay = 2; //Gets the fist day at the week. --> Not done yet...
		int lastDateOfMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        //System.out.println(firstDay + " første dag");
		//System.out.println(lastDateOfMonth + " siste dag");
        //Setting the month dates to right place.
		int tall = 1;
		System.out.println(labels.size());
		for(int i = 0; i < labels.size(); i++) {
			if ((i >= firstDay) && (tall <= lastDateOfMonth)) {
				labels.get(i).setText(Integer.toString(tall));
                tall++;
            } else {
				labels.get(i).setText("");
			}
		}
	}

	//GetLabelSoMonthOrganizerCanChangeiy
	public void StringGetLabel(String label){
		System.out.println("yo");
	}

	//When days in months are clicked, these methods will do the magic,.
	@FXML
	private void handleCalendarClick1(){
		monthClicked(1);
	}
	@FXML
	private void handleCalendarClick2(){
		monthClicked(2);
	}


	//Go to that given day when month is clicked.
	public void monthClicked(int tall){


		//String id = pane.getId();
		//System.out.println(tall);
		System.out.println("MonthClicked day-number" + tall);
		//System.out.println(id);
	}

	//Set initalize date.
	int count = 0;
	public void setDate(){
		if(count == 0){
			Date input = new Date();
			LocalDate dato = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			changeDate(dato);
			chosenDate = Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant());
			count++;
		}
    }
    //Is between.
	public boolean isBetween(Date endTime, Date startTime, Date timeToCheckStart, Date timeToCheckEnd){
        if((endTime.before(timeToCheckStart))&&(startTime.after(timeToCheckEnd))){
			return true;
		}
		return false;
	}

	//GetUsercells --> Sjekke tiden på den, i tillegg til å sjekke
    public void userCellOrganize(){
	    //Get usercells from database..

        ArrayList<UserCell> userCells = new ArrayList<UserCell>();
    }

    //Clear all timeslots dayPane.
    public void clearTimeSlots(){

    	for(Label label : timeToTime){
    	    label.setText("");
        }
	}

	//Clear given timeslot For dayPane.
	public void clearTimeSlot(int slot){
        int count = 0;
        for(Label label : timeToTime){
            if(count == slot){
                label.setText("");
            }
            count++;
        }

    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
        setLines();
		setDate();
		addTimeToTimeToList();
		clearTimeSlots();
		cellsAtCurrentDate.add(new UserCell(new Date(setHour(chosenDate, 8).getTime()), new Date(setHour(chosenDate, 10).getTime()),
				"Trening", "Jeg skal trene på SIT", 5, false
		));
		cellsAtCurrentDate.add(new UserCell(new Date(setHour(chosenDate, 15).getTime()), new Date(setHour(chosenDate, 16).getTime()),
				"Trening", "Jeg skal trene på SIT", 5, false
		));
		insertCells();
		enterCells();

	}

	public Date setHour(Date date, int hour){
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public void insertCells(){
    	//TODO: Handle which tab you are on
		System.out.println(dayTabTimeSlots.keySet());
		if(dayTab.isSelected()){
			boolean stretch = false;
			for (calendar.Cell cell : cellsAtCurrentDate){
				for (Map.Entry<TimeInterval, Label> entry : dayTabTimeSlots.entrySet())
				{
					if(stretch){
						System.out.println("Skal komme hit 1 gang");
						labelMappedCells.put(entry.getValue(), cell);
						stretch = false;
					}
					if(entry.getKey().getStartTime().equals(cell.getStartDate())){
						labelMappedCells.put(entry.getValue(), cell);
						if(entry.getKey().getEndTime().before(cell.getEndDate())){
							stretch = true;
						}
					}
				}
			}
		}else if(weekTab.isSelected()){
				System.out.println("Week");
		}else{ //Month is selected
				System.out.println("Month");
		}

	}

	public void enterCells(){
		for (Map.Entry<Label, calendar.Cell> entry : labelMappedCells.entrySet())
		{
			writeToLabel(entry.getKey(), entry.getValue());
		}
	}

	public void writeToLabel(Label label, calendar.Cell cell){
		//TODO: Make a nice way to write cell info to label
		label.setText(cell.getName());
	}
}
