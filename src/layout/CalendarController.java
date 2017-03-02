package layout;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.GregorianCalendar;

import calendar.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import database.Fetcher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

	@FXML JFXButton sliderButton; //Slide button

	//Labels for leftPane --> BOTTOFIELD
	@FXML Label botto1;
	@FXML Label botto2;
	@FXML Label time;
	@FXML Label askBotto;
	@FXML Button sendInButton;

	//Labels for Day pane.
	@FXML Label thisday;

	//Labels week-Pane

	//Tabs
	@FXML Tab dayTab;
	@FXML Tab weekTab;
	@FXML Tab monthTab;


	//Date
	@FXML JFXDatePicker date;

	//To set a new scene.
	layout.Calendar cal = new layout.Calendar();

	//List to add the month labels.


	//Counter to only add the labels to the labels list once.

    // List of labels in day Tab...
    @FXML List<Label> timeToTime = new ArrayList<>();

    //CountVariables
    int teller = 0;
    int slideCount = 0;
    User user = new User();

	//*************** HENNINGS ULTRAFELT *****************//
	//DAY
	List<Label> dayTabLabels = new ArrayList<>(); // Hentes fra GUI
	List<calendar.Cell> cellsAtCurrentDate = new ArrayList<>(); // Skal fylles fra database

	Map<TimeInterval, Label> dayTabTimeSlots = new LinkedHashMap<>();
	Map<Label, calendar.Cell> labelMappedCells = new LinkedHashMap<>(); //Ferdig mappet celler til labels

	//MONTH
	List<Label> monthLabels = new ArrayList<>();; // Hentes fra GUI
	List<AnchorPane> monthAnchorPanes = new ArrayList<>(); // Hentes fra GUI

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
		AddController a = cal.changeToAdd("../resources/add.fxml", "ADD"); //Get the instance of the add controller.
        //TODO: make getMethods in addcontrollerclass so usercells can be set directly from addconroller.
		System.out.println("add");

	}
	
	//On action from remove button
	public void remove(){
        //TODO: Make remove button work
		System.out.println("remove");
	}
	
	//On action from addsubject button
	public void addSubject(){
		AddSubjectController a = cal.changeToAddSubject("../resources/addSubject.fxml", "Add subject");
	}
	
	//On action form IL --> Currenly no IL button on fxml-file
	public void IL(){ //Button does not exist atm.
		System.out.println("IL");
	}
	
	//ON action from BB --> Currently no BB butto on fxml-file.
	public void BB(){//Button does not exist atm.
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
		setupDayTab();
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
		int hour = 8;
		for(Label label : dayTabLabels){
			Date start = setHour(chosenDate, hour);
			Date end = setHour(chosenDate, hour+1);
			dayTabTimeSlots.put(new TimeInterval(start, end), label);
			hour++;
		}
	}

    //Add labels in month tab to list..
	public void addLabelsToList(){
		ObservableList<Node> children = month.getChildren();
		for(Node child : children){
			if(child.getClass().equals(AnchorPane.class)){
				//Works as long as the day number is the first child of the AnchorPane
				AnchorPane ap = (AnchorPane) child;
				monthAnchorPanes.add(ap);
				monthLabels.add((Label) ap.getChildren().get(0));
			}
		}
    }

    public void monthOrganizer(int year, int month, int day){ //Set labels on month part to the right month.
		System.out.println(year + " " + month + " " + day);
		java.util.Calendar cal = new GregorianCalendar(year, month, day);
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
		System.out.println(monthLabels.size());
		for(int i = 0; i < monthLabels.size(); i++) {
			if ((i >= firstDay) && (tall <= lastDateOfMonth)) {
				monthLabels.get(i).setText(Integer.toString(tall));
                tall++;
            } else {
				monthLabels.get(i).setText("");
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
        dayTabLabels.clear();
        cellsAtCurrentDate.clear();
        dayTabTimeSlots.clear();
        labelMappedCells.clear();

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
        setLines();
		setDate();
		setupDayTab();
	}

	public void setupDayTab(){
        clearTimeSlots();
        addTimeToTimeToList();
        try {
            getCells();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        insertCells();
        enterCells();
    }

    private void getCells() throws ParseException {
	    //TODO: Get cells from database
        try {
            Fetcher fetch = new Fetcher("SELECT * FROM ACTIVITY");
            Set<List> activities = fetch.getUserRelatedResults(10); //If 9 columns, input 10 (#columns + 1)
            for(List activity : activities){
                SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
                //TODO: This should not be put in cellsAtCurrentDate, but for test purposes it stays
                cellsAtCurrentDate.add(new UserCell(
                        setHour(sdfm.parse((String) activity.get(2)), Integer.parseInt((String) activity.get(4))),
                        setHour(sdfm.parse((String) activity.get(2)), Integer.parseInt((String) activity.get(5))),
                        (String) activity.get(1),
                        (String) activity.get(8),
                        Integer.parseInt((String) activity.get(6)),
                        Boolean.parseBoolean((String) activity.get(3))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Date setHour(Date date, int hour){
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public void insertCells(){
    	//TODO: Handle which tab you are on
		if(dayTab.isSelected()){
			boolean stretch = false;
			for (calendar.Cell cell : cellsAtCurrentDate){
                System.out.println("NEW ENTRY");
                for (Map.Entry<TimeInterval, Label> entry : dayTabTimeSlots.entrySet())
				{
					if(stretch){
						labelMappedCells.put(entry.getValue(), cell);
						stretch = false;
                        if(entry.getKey().getEndTime().before(cell.getEndDate())){
                            stretch = true;
                        }
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
