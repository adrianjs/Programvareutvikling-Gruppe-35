package controllers;

import calendar.Activity;
import calendar.Event;
import algorithm.SuperSorter;
import calendar.Cell;
import algorithm.TimeInterval;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import layout.User;
import layout.EventButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

//import layout.eventButtonWeek.activityButton;

public class CalendarController implements Initializable{

    private Date chosenDate = new Date();
	private Date thisDate = new Date();
	private SuperSorter superSorter = new SuperSorter();

	@FXML GridPane day; //DAY PANE
	@FXML GridPane week; //WEEK PANE
	@FXML GridPane month; //MONTH PANE
	@FXML AnchorPane dayAnchor; //Anchorpane day
	@FXML Label username;

	//AI panes.
	private AnchorPane botto;
    @FXML private JFXDrawer drawer;
	AnchorPane rightside;

    @FXML JFXButton askButton;
	@FXML JFXButton logout;
	@FXML JFXButton remove;


	//Labels for Day pane.
	@FXML Label thisday;

	//Labels week-Pane

	//Tabs
	@FXML Tab dayTab;
	@FXML Tab weekTab;
	@FXML Tab monthTab;
	@FXML JFXTabPane tabs;

    //Date
	@FXML JFXDatePicker date;

	//To set a new scene.
	public layout.Calendar cal = new layout.Calendar();

    // List of labels in day Tab...
    @FXML List<Label> timeToTime = new ArrayList<>();

    //CountVariables
    private int counter = 0;
    private int dayClicked = 0; //Day clicked on in MonthTab
    private User user = User.getInstance();

    //WeekTab style-variables
	private int dayOfMonth = 0;
	@FXML Label monday;
	@FXML Label tuesday;
	@FXML Label wednesday;
	@FXML Label thursday;
	@FXML Label friday;
	@FXML Label saturday;
	@FXML Label sunday;


    //------------------------Lister som brukes til å printe ut til Days----------------------------------------.
	private ArrayList<EventButton> oldDays = new ArrayList<>();


    //------------------------Lister som brukes til å printe ut til weeks----------------------------------------.
    public ArrayList<Cell> cells = new ArrayList<>();//liste over activitys som skal inn i kalenderen
    private ArrayList<EventButton> oldActivityButtons = new ArrayList<>();//liste over activitys som ligger i calenderen denne uken
    private ArrayList<LocalDate> activitysDate = new ArrayList<>(); //Liste over acktiitys som har blit printet inn i listen



    //*************** HENNINGS ULTRAFELT *****************//
	//DAY
	public List<Label> dayTabLabels = new ArrayList<>(); // Hentes fra GUI
	public List<Cell> cellsAtCurrentDate = new ArrayList<>(); // Skal fylles fra database

	private  Map<TimeInterval, Label> dayTabTimeSlots = new LinkedHashMap<>();
	public Map<Label, Cell> labelMappedCells = new LinkedHashMap<>(); //Ferdig mappet celler til labels

	//MONTH
	public List<Label> monthLabels = new ArrayList<>();; // Hentes fra GUI
	private List<AnchorPane> monthAnchorPanes = new ArrayList<>(); // Hentes fra GUI
	private Map<Label, AnchorPane> dayMappedPane = new LinkedHashMap<>();// Lages i metode.
	public Map<LocalDate, AnchorPane> dateMappedMonth = new LinkedHashMap<>(); //Lages i metode.
    private ArrayList<LocalDate> doubleDate = new ArrayList<>(); // holder telling på datoer som er 2 ganger;

	//****************************************************//
    private ArrayList<Label> eventLabels = new ArrayList<>(); //MonthTabEventLabels --> Used to remove text in labels in monthTab.

    //Mapping used in to weekTab.
    private HashMap<Integer, List> weekLabelList = new HashMap<>(); //FROM GUI
    private Map<LocalDate, HashMap<TimeInterval, Label>> weekDateLinkedToDay = new LinkedHashMap<>();
    public ArrayList<LocalDate> weekCalendarList = new ArrayList<LocalDate>();//Dates this week.
    public Map<Label, Cell> weekLabelMappCell = new LinkedHashMap<>();

    //Methods starts here.

	private static CalendarController instance = null; //InstanceControl singelton Pattern.
	public static CalendarController getInstance() {
		if (instance == null) {
			instance = new CalendarController();
		}
		return instance;
	}
	public Scene getScene(){
		return askButton.getScene();
	}

	public void changeScene(String s1, String s2) throws IOException {
		cal.changeScene(s1, s2);
	}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		username.setText(User.getInstance().getUsername().split("@")[0]);
		setLines();
        setDate();
		try {
			superSorter.run();
			setupDayTab();
			getWeekTabCells();
			mapMonthTab();
			setBottoField(); //SlideFieldBotto
			setNewDate2(thisDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		date.setValue(chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

	}

    public void refresh(){
		try {
			superSorter.run();
			timeLayout();
			setupDayTab();
			getWeekTabCells();
			mapMonthTab();
			insertMonthCells();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * On action from next button in calendar.fxml, jumps to next day/week/month.
	 */
	public void next(){
		LocalDate locDate = date.getValue();
		if(dayTab.isSelected()){
			locDate = locDate.plusDays(1);
		}else if(weekTab.isSelected()){
    		locDate = locDate.plusWeeks(1);
		}else{
			locDate = locDate.plusMonths(1);
		}
		setNewDate2(locDate);
		date.setValue(locDate);
		refresh();
	}


	/**
	 * On action from previous button in calendar.fxml, jumps to previos day/week/month
	 */
	public void previous(){
		LocalDate locDate = date.getValue();
		if(dayTab.isSelected()){
			locDate = locDate.minusDays(1);
		}else if(weekTab.isSelected()){
			locDate = locDate.minusWeeks(1);
		}else{
			locDate = locDate.minusMonths(1);
		}
		setNewDate2(locDate);
		date.setValue(locDate);
		refresh();
	}

    /**
     * Set new lines to gridpanes.
     */
	public void setLines(){ //Set lines for day week and month.
		day.setGridLinesVisible(true);
		week.setGridLinesVisible(true);
		month.setGridLinesVisible(true);
	}

	/**
	 * On action from restore button. Loads the restore.fxml
	 * @throws IOException if the fxml fails to load.
	 */
	public void restore() throws IOException {
		cal.changeScene("/resources/fxml/restore.fxml", "Restore dropped events");
	}

    /**
     * OnAction from addsubject-button, opens the addsubject.fxml
     * @throws IOException if fxml fails to load.
     */
	public void addSubject() throws IOException {
		cal.changeScene("/resources/fxml/addSubject.fxml", "Manage subjects");
	}

	/**
	 * Set window back to loginScreen, switches to username.fxml.
	 * @throws Exception if fxml fails to load.
	 */
	public void logOut() throws Exception {
		Stage s = (Stage) logout.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/username.fxml"));
		Parent load = loader.load();
		Scene scene = new Scene(load);
		s.setScene(scene);
		CalendarController.instance = null; //Sets instance to null so new user can log in.
	}

	/**
	 * Used by other classes(add.fxml) to set the chosen date in datepicker fied.
	 * @return this date.
	 */
	public Date getChosenDate(){
		return chosenDate;
	}

    /**
     * Sets the ask.fxml to the botto anchorpane, makes a new hamburger.
     */
    private void setBottoField(){
        try {
            botto = FXMLLoader.load(getClass().getResource("/resources/fxml/ask.fxml"));
		} catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Open and close the botto, and set value to hamburger. OnAction from botto-Button.
	 */
    public void slidePane(){
    	botto.setStyle("-fx-border-color: #75bc1b; -fx-border-width: 5px");
		drawer.setSidePane(botto);
		if(drawer.isShown()){
            askButton.setText("Open Bot");
            drawer.close();
        }else{
            drawer.open();
            askButton.setText("Close Bot");
        }
    }

    /**
     * OnAction from addButton, opens add.fxml from Calendar.class.
     * @throws IOException if add.fxml fail to load.
     */
    public void add() throws IOException {
        cal.changeScene("/resources/fxml/add.fxml", "Add Activity");
    }

	/**
	 * Set new date when date in datapicker is changed.
	 */
	public void setNewDate(){
        LocalDate locDate = date.getValue();
        setNewDate2(locDate);
        refresh();
	}

	/**
	 *Set chosendate to the right date, and sets the calendar to the right position.
	 * @param locDate Takes in the localdate from the setNewDate Method.
	 */
	private void setNewDate2(LocalDate locDate){
        System.out.println(locDate);
        chosenDate = Date.from(locDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        date.setValue(locDate);
        changeDate(locDate);
    }

	/**
	 * Set changes the color of today so you know which day it is.
	 */
    public void timeLayout(){
		Date date = new Date();
        LocalDate ldate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int dayOfWeek = ldate.getDayOfWeek().getValue();
        dayOfMonth = ldate.getDayOfMonth();
		monday.setStyle("-fx-background-color: white;");
        tuesday.setStyle("-fx-background-color: white;");
        wednesday.setStyle("-fx-background-color: white;");
        thursday.setStyle("-fx-background-color: white;");
        friday.setStyle("-fx-background-color: white;");
        saturday.setStyle("-fx-background-color: white;");
        sunday.setStyle("-fx-background-color: white;");

		if(weekCalendarList.contains(ldate)){
            if (dayOfWeek == 1){
                monday.setStyle("-fx-background-color: red;" +
						"-fx-text-fill: white;");
            }else if(dayOfWeek == 2){
                tuesday.setStyle("-fx-background-color: red;" +
						"-fx-text-fill: white;");
            }else if(dayOfWeek == 3){
                wednesday.setStyle("-fx-background-color: red;" +
						"-fx-text-fill: white;");
            }else if(dayOfWeek == 4){
                thursday.setStyle("-fx-background-color: red;" +
                        "-fx-text-fill: white;");
            }else if(dayOfWeek == 5){
                friday.setStyle("-fx-background-color: red;" +
						"-fx-text-fill: white;");
            }else if(dayOfWeek == 6){
                saturday.setStyle("-fx-background-color: red;" +
						"-fx-text-fill: white;");
            }else if(dayOfWeek == 7){
                sunday.setStyle("-fx-background-color: red;" +
						"-fx-text-fill: white;");
            }
        }
    }

	/**
	 * The new date set from datePicker adjusted to fit monthOrganizer
	 * @param locDate date from setNewDate2.
	 */
	private void changeDate(LocalDate locDate){
		String choseDate = locDate.toString();
		thisday.setText(choseDate);
        String[] split = choseDate.split("-");
        String year = split[0];
		String month = split[1];
		String day = split[2];
		//Check if first element in month and day has 0 first, and then cut it out.
		int year1 = Integer.parseInt(year);
		int month1 = removeZero(month);
		int day1 = removeZero(day);
		if(counter == 0){//Only needed to do this once per instance.
			addLabelsToList();
			counter++;
		}
        monthOrganizer(year1, month1-1, day1);
	}

	/**
	 * Remove 0 if it is first in the string.
	 * @param number String formed as a number
	 * @return converted string to int.
	 */
	public int removeZero(String number){
        if(number.charAt(0) == '0'){
			return Character.getNumericValue(number.charAt(1));
		}
		return Integer.parseInt(number);
	}



    /**
     * Add labels in monthTab to list.
     */
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

	/**
	 * Fix the veiw of the monthTab so the dates is on the right day in relation to wich month it is.
	 * @param year year integer
	 * @param month month integer
	 * @param day day integer
	 */
    private void monthOrganizer(int year, int month, int day){ //Set labels on month part to the right month.
		java.util.Calendar cal = new GregorianCalendar(year, month, day);
        java.util.Calendar cal1 = new GregorianCalendar(year, month, 1);
        int weekOfMonth = cal.get(cal.WEEK_OF_MONTH);
		int dayOfWeek = cal.get(cal.DAY_OF_WEEK) -2;
		if(dayOfWeek == -1){
		    dayOfWeek = 6;
        }
		//This works for the months i have checked --> First day;
        int firstDay = cal1.get(cal1.DAY_OF_WEEK) -2;
        if(firstDay == -1){
            firstDay = 6;
        }
        int lastDateOfMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        weekOrganizer(year, month+1, day, weekOfMonth, dayOfWeek);
        //Setting the month dates to right place.
		int number = 1;
		for(int i = 0; i < monthLabels.size(); i++) {
			if ((i >= firstDay) && (number <= lastDateOfMonth)) {
				monthLabels.get(i).setText(Integer.toString(number));
				monthLabels.get(i).setStyle("-fx-background-color: white;" +
				"-fx-underline: true;");
				LocalDate ldate = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int chosenMonthValue = ldate.getMonthValue();
				LocalDate ldateThis = thisDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int thisMontValue = ldateThis.getMonthValue();

				if(number == dayOfMonth && chosenMonthValue == thisMontValue){
				    monthLabels.get(i).setStyle("-fx-background-color: red;" +
							"-fx-text-fill: white;" +
							" -fx-border-radius: 40 40 40 40;" +
							"-fx-background-radius: 40 40 40 40;" +
							"-fx-underline: true;");
				    monthLabels.get(i).setPadding(new Insets(2,4,2,4));
                }
                number++;
            } else {
				monthLabels.get(i).setText("");
            }
		}
	}

    /**
     * Makes a list of dates in week, so every day in the chosen week has a date connected to it.(LocalDate)
     * Then we kan put values to labels like it is done in the daytab
     * @param year year integer
     * @param month month integer
     * @param day day integer
     * @param week week integer
     * @param dayofWeek dayofweek integer
     */
	private void weekOrganizer(int year, int month, int day, int week, int dayofWeek){
		LocalDate date;
	    weekCalendarList.clear();
        date = LocalDate.of(year, month, day);
        if(dayofWeek == 0){
            for(int i = day; i < day+7; i++){
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }else if(dayofWeek == 1){
            date = date.minusDays(1);
            for(int i = day; i < day+7; i++) {
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }else if(dayofWeek == 2){
            date = date.minusDays(2);
            for(int i = day; i < day+7; i++) {
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }else if(dayofWeek == 3){
            date = date.minusDays(3);
            for(int i = day; i < day+7; i++) {
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }else if(dayofWeek == 4){
            date = date.minusDays(4);
            for(int i = day; i < day+7; i++) {
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }else if(dayofWeek == 5){
            date = date.minusDays(5);
            for(int i = day; i < day+7; i++) {
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }else if(dayofWeek == 6){
            date = date.minusDays(6);
            for(int i = day; i < day+7; i++) {
                weekCalendarList.add(date);
                date = date.plusDays(1);
            }
        }
		clearWeekTimeSlots();
        mapWeekLabelsToTimeIntervals();
    }

    /**
	 * Mappes anchorpanes in monthtab to å localdate corresponding to the right label.
	 */
	public void mapMonthTab(){
        dayMappedPane.clear();
        dateMappedMonth.clear();
        LocalDate date = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int dayOfMonth = date.getDayOfMonth();
        date = date.minusDays(dayOfMonth);
        for(int i = 0; i < monthLabels.size(); i++){
            dayMappedPane.put(monthLabels.get(i), monthAnchorPanes.get(i));
            if(monthLabels.get(i).getText().length() != 0){
                date = date.plusDays(1);
                dateMappedMonth.put(date, monthAnchorPanes.get(i));
            }
        }
    }

    //Go to that given day when month is clicked.

    /**
     * Go to the given day when month gridpane is clicked. Loads activities from this day in
     * @param tall integer clicked.
     */
	public void monthClicked(int tall){
	    dayClicked = tall; //So the value can be used in WatchDayMonthTabController.
        String day = monthLabels.get(tall-1).getText();
        try{
			int dayInt = Integer.parseInt(day);
			LocalDate date = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = date.getYear();
			int month = date.getMonthValue();
			LocalDate date1 = LocalDate.of(year, month, dayInt);
			tabs.getSelectionModel().select(dayTab);
			setNewDate2(date1);
			refresh();


		}catch (Exception e){
			System.out.println("");
		}

    }

    /**
     * Sets the date when application starts.
     */
	public void setDate(){
        Date input = new Date();
        LocalDate dato = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date.setValue(dato);
        changeDate(dato);
        chosenDate = Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant());
		timeLayout();
    }

	/**
     * Clear all timeslots in dayPane
     */
    public void clearTimeSlots(){

    	for(Label label : timeToTime){
    	    label.setText("");
        }
        dayTabLabels.clear();
        cellsAtCurrentDate.clear();
        dayTabTimeSlots.clear();
        labelMappedCells.clear();

	}

	/**
	 * Clear a given timeslot in dayPane.
	 * @param slot numbered slot.
	 */


	public void clearTimeSlot(int slot){
        int count = 0;
        for(Label label : timeToTime){
            if(count == slot){
                label.setText("");
            }
            count++;
        }
    }

    /**
     * Sets up the daytab.
     */
    private void setupDayTab(){
		//System.out.println("Setup day tab");
		clearTimeSlots();
        //addTimeToTimeToList();
        try {
			//System.out.println("get cells");
			getCells();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        insertCells();
        enterCells();
    }

    /**
     * Gets cells from the database
     * @throws ParseException if it fail to get cells from database.
     */
    private void getCells() throws ParseException {
	    //TODO: endre liste når supersort er ferdig
            cells = new ArrayList<>(superSorter.getScheduleWithoutCollision());
			cellsAtCurrentDate = new ArrayList<>(superSorter.getScheduleWithoutCollision());
	}

	/**
	 * Set hour of day to calendar object
	 * @param date date of given day
	 * @param hour hour of given day.
	 * @return Date with time object.
	 */
    private Date setHour(Date date, int hour){
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);
		cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	/**
	 * Insert cells in day, week, and month tab.
	 */
	public void insertCells(){
		insertDayCells();
		insertWeekCells();
		insertMonthCells();
	}

	/**
	 * Insert cells at dayTab.
	 */
	private void insertDayCells(){
		for (EventButton oldDay : oldDays){
			day.getChildren().remove(oldDay.getEvent());
		}
		oldDays.clear();
		if(chosenDate != null ){
		LocalDate dateActivity;
		LocalDate chosenDateL = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		for (Cell cell : cells) {
			if (cell.getStartDate().getClass() == java.sql.Date.class) {
				dateActivity = new Date(cell.getStartDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			} else {
				dateActivity = cell.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}

			if (dateActivity.equals(chosenDateL)) {
				EventButton eb;
				if(cell.getClass() == Activity.class) {
					eb = new EventButton(cell.getName(), cell.getDescription(), cell);
					day.add(eb.getEvent(), 1, Integer.parseInt(cell.getStartTime()) - 7,
							1, Integer.parseInt(cell.getEndTime()) - Integer.parseInt(cell.getStartTime()));
				}else if(cell.getSlotPriority() == 98){
					Event eventCell = (Event) cell;
					eb = new EventButton(cell.getName(), cell.getDescription(),eventCell.getSubjectCode(), cell, cell.getID());
					day.add(eb.getEvent(), 1, Integer.parseInt(cell.getStartTime()) - 7,
							1, 2);
				} else {
					Event eventCell = (Event) cell;
					eb = new EventButton(cell.getName(), cell.getDescription(),eventCell.getSubjectCode(), cell, cell.getID());
					day.add(eb.getEvent(), 1, Integer.parseInt(cell.getStartTime()) - 7,
							1, Integer.parseInt(cell.getEndTime()) - Integer.parseInt(cell.getStartTime()));
				}

				oldDays.add(eb);
			}
		}

		}
	}

	/**
	 * Insert cells at weekTab.
	 */
	private void insertWeekCells(){
		//System.out.println("SETTING NEW CELLS");

		for(EventButton ab : oldActivityButtons){
			week.getChildren().remove(ab.getEvent());
		}
		oldActivityButtons.clear();

			int day = 1; //hvilken av dagslistene det skal skrives til.
			for (LocalDate lDate : weekCalendarList) {//Går igjennom datoer denne uken.

                for(Cell cell : cells){
					LocalDate dateActivity;
                	if(cell.getStartDate().getClass() == java.sql.Date.class){
						dateActivity = new Date(cell.getStartDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					} else {
						dateActivity = cell.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					}
					if(lDate.equals(dateActivity)){
					    //legger til activity i week calenderen
						EventButton event;
						if(cell.getClass() == Activity.class) {
							event = new EventButton(cell.getName(), cell.getDescription(), cell);
							week.add(event.getEvent(), day, Integer.parseInt(cell.getStartTime()) - 7,
									1, Integer.parseInt(cell.getEndTime()) - Integer.parseInt(cell.getStartTime()));
						} else if(cell.getSlotPriority() == 98){

							Event eventCell = (Event) cell;
							event = new EventButton(cell.getName(), cell.getDescription(), eventCell.getSubjectCode(), cell, cell.getID());
							week.add(event.getEvent(), day, Integer.parseInt(cell.getStartTime()) - 7,1,2);

						}

						else {
							Event eventCell = (Event) cell;
							event = new EventButton(cell.getName(), cell.getDescription(), eventCell.getSubjectCode(), cell, cell.getID());
							week.add(event.getEvent(), day, Integer.parseInt(cell.getStartTime()) - 7,
									1, Integer.parseInt(cell.getEndTime()) - (Integer.parseInt(cell.getStartTime())));
						}

						//legge evnte i en liste slik vi vettt vilken som er i calanderen denne uken
						oldActivityButtons.add(event);

					}
				}
				day ++;
				if(day == 8){
					day=1;
				}
			}
	}

	/**
	 * Insert cells in month tab.
	 */
	private void insertMonthCells(){
		removeMonthActivityLabel();
		System.out.println("Month");
		ArrayList<LocalDate> usedDate = new ArrayList<>();

		for (Cell cell : cellsAtCurrentDate) {
			LocalDate date;
			if (cell.getStartDate().getClass() == java.sql.Date.class) {
				date = new Date(cell.getStartDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			} else {
				date = cell.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
			for (Map.Entry<LocalDate, AnchorPane> entry : dateMappedMonth.entrySet()) {
				if (entry.getKey().equals(date)) {
					//IF There is something with high priority --> Can change this.
					if (usedDate.contains(entry.getKey()) || doubleDate.contains(entry.getKey())) {
					    try{
                            Label oldLabel = (Label) entry.getValue().getChildren().get(1);
                            entry.getValue().getChildren().remove(1);
                            String[] checkLength = oldLabel.toString().split("\n");
                            Label lab = new Label();
                            String oldLabelToString = "\n";
                            if (checkLength.length <= 3) {
                                String cellName = cell.getName();
                                if (cellName.length() > 15) {
                                    cellName = cellName.substring(0, 15) + "...";
                                }
                                oldLabelToString = oldLabel.getText() + "\n" + cellName;
                            } else {
                                for (int i = 1; i < 3; i++) {
                                    String length = checkLength[i];
                                    oldLabelToString += length + "\n";
                                }
                                oldLabelToString += "Click for more...";
                            }
                            lab.setText(oldLabelToString);
                            lab.setStyle("-fx-text-fill: #75bc1b;" +
                                    "-fx-font-size: 14;");
                            lab.setPadding(new Insets(0, 0, 0, 5));
                            entry.getValue().getChildren().addAll(lab);
                            lab.setId("deletable");
                            eventLabels.add(lab);
                            if (!doubleDate.contains(entry.getKey())) {
                                doubleDate.add(entry.getKey());
                            }
                        }catch (Exception e){
                            newLabel(cell, entry, usedDate);
                        }

					} else {
						newLabel(cell, entry, usedDate);
					}
				}
			}
		}
	}

    /**
     * Creates a new label
     * @param cell cell from insert monthcells method
     * @param entry entry from insert monthcells method
     * @param usedDate useddate from insert monthcells method
     */
	private void newLabel(Cell cell, Map.Entry<LocalDate, AnchorPane> entry, ArrayList<LocalDate> usedDate){
        Label lab = new Label();
        String cellName = cell.getName();
        if(cellName.length() > 15){
            cellName = cellName.substring(0,15) + "...";
        }
        lab.setText(" " + '\n' + cellName);
        lab.setStyle("-fx-text-fill: #75bc1b;" +
                "-fx-font-size: 14;");
        lab.setPadding(new Insets(0, 3, 0, 3));
        entry.getValue().getChildren().addAll(lab);
        lab.setId("deletable");
        eventLabels.add(lab);
        usedDate.add(entry.getKey());
    }

	/**
	 * Help-Method for inserting mapped cells.
	 * @param map Map
	 * @param cell Cell
	 * @param stretch how long the event is
	 * @param mapTo Map
	 * @return stretch.
	 */
	/*
	public boolean insertCellHelper(Map.Entry<TimeInterval, Label> map, Cell cell, boolean stretch, Map<Label, Cell> mapTo){
        if(map.getKey().getStartTime().equals(cell.getStartDate())){
            Label lab = map.getValue(); //First
            //lab.setUnderline(true);
            lab.setStyle("-fx-font-weight: bold;");
            mapTo.put(map.getValue(), cell);
            if(map.getKey().getEndTime().before(cell.getEndDate())){
                return true;
            }
        }
        return stretch;
    }*/

    /**
	 * Removes the labels in the month anchorpanes, where id is set to deletable, prevents labels to lay on top on each other.
	 */
	private void removeMonthActivityLabel(){
        for (Label l: eventLabels) {
			l.setText("");
			if(l.getId().contains("deletable")){
                AnchorPane par = (AnchorPane) l.getParent();
                try{
                    par.getChildren().remove(l);
                }catch(Exception e){
                    assert true;
                }
            }
        }
	}

    /**
     * Set cells to labels.
	 */
    private void enterCells(){
		for (Map.Entry<Label, Cell> entry : labelMappedCells.entrySet())
		{
			writeToLabel(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Writes to label
	 * @param label label
	 * @param cell cell.
	 */
	private void writeToLabel(Label label, Cell cell){
		//TODO: Make a nice way to write cell info to label
		label.setText(cell.getName());
	}

	//****Lars Lager Stuff til weekTab********************************************************************************
    /**
     * Get all week tab cells, an add them to a hash
     */
	private void getWeekTabCells() throws ParseException {
        List<LocalDate> dates = weekCalendarList;
        ObservableList<Node> list = week.getChildren(); //Henter alle childs fra weekTab.
        List<Label> label1 = new ArrayList<>();
		List<Label> label2 = new ArrayList<>();
		List<Label> label3 = new ArrayList<>();
		List<Label> label4 = new ArrayList<>();
		List<Label> label5 = new ArrayList<>();
		List<Label> label6 = new ArrayList<>();
		List<Label> label7 = new ArrayList<>(); //Legger Lablene for hver uke i en egen labelList
		for (Node node : list){
            try{
                String id = node.getId();
				Label l = (Label) node;
                String t = l.getText();
				if(t.equals("1")){
				    label1.add((Label) node);
                }
				else if(t.equals("2")){
					label2.add((Label) node);
				}
				else if(t.equals("3")){
					label3.add((Label) node);
				}
				else if(t.equals("4")){
					label4.add((Label) node);
				}
				else if(t.equals("5")){
					label5.add((Label) node);
				}
				else if(t.equals("6")){
					label6.add((Label) node);
				}
				else if(t.equals("7")) {
					label7.add((Label) node);
				}
            }catch(Exception e){}
        }
		weekLabelList.put(1, label1);
		weekLabelList.put(2, label2);
		weekLabelList.put(3, label3);
		weekLabelList.put(4, label4);
		weekLabelList.put(5, label5);
		weekLabelList.put(6, label6);
		weekLabelList.put(7, label7); //Legger Labellistene i en egen ukes hashMap.
		clearWeekTimeSlots();
		mapWeekLabelsToTimeIntervals();
    }

    /**
     * Clears All Labels in weekTab.
     */
    private void clearWeekTimeSlots(){
		for (Map.Entry<Integer, List> list : weekLabelList.entrySet()){
            for (Object label : list.getValue()) {
                Label l = (Label) label;
                l.setText("");
            }
        }
	}



    /**
     * Maps Labels in weekTab to right timeInterVal for every day in chosen week.
     */
	public void mapWeekLabelsToTimeIntervals(){
		weekDateLinkedToDay.clear(); //Må mappes for hver gang.
        HashMap<TimeInterval, Label> day1 = new LinkedHashMap<>();
        HashMap<TimeInterval, Label> day2 = new LinkedHashMap<>();
        HashMap<TimeInterval, Label> day3 = new LinkedHashMap<>();
        HashMap<TimeInterval, Label> day4 = new LinkedHashMap<>();
        HashMap<TimeInterval, Label> day5 = new LinkedHashMap<>();
        HashMap<TimeInterval, Label> day6 = new LinkedHashMap<>();
        HashMap<TimeInterval, Label> day7 = new LinkedHashMap<>(); //Tidsinterval meppes til riktig labels.
        int count = 0;
        int hour = 8;
        for(Map.Entry<Integer, List> list : weekLabelList.entrySet()){ //Går igjennom weekLabel list, <Tall for dag, Liste for labels>
            hour = 8;
            LocalDate localDate = weekCalendarList.get(count);
            for(Object label : list.getValue()){ //Går igjennom labelsene
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date start = setHour(date, hour);
                Date end = setHour(date, hour+1);
                if(count == 0){
                    day1.put(new TimeInterval(start, end), (Label) label); //Mapper en label til et tidsintervall, bestemt av count.
                }
                else if(count == 1){
                    day2.put(new TimeInterval(start, end), (Label) label);
                }
                else if(count == 2){
                    day3.put(new TimeInterval(start, end), (Label) label);
                }
                else if(count == 3){
                    day4.put(new TimeInterval(start, end), (Label) label);
                }
                else if(count == 4){
                    day5.put(new TimeInterval(start, end), (Label) label);
                }else if(count == 5){
                    day6.put(new TimeInterval(start, end), (Label) label);
                }
                else if(count == 6){
                    day7.put(new TimeInterval(start, end), (Label) label);
                }
                hour++;
            }
            weekDateLinkedToDay.put(weekCalendarList.get(0), day1); //Mapper til En hasmap <LocalDate, HashMap<Tidsinterval, Labels>
            weekDateLinkedToDay.put(weekCalendarList.get(1), day2); // Skal brukes for å sette ACTIVITY verdier til weekTab.
            weekDateLinkedToDay.put(weekCalendarList.get(2), day3);
            weekDateLinkedToDay.put(weekCalendarList.get(3), day4);
            weekDateLinkedToDay.put(weekCalendarList.get(4), day5);
            weekDateLinkedToDay.put(weekCalendarList.get(5), day6);
            weekDateLinkedToDay.put(weekCalendarList.get(6), day7);
            count++;
        }
    }


    //When days in monthsTab is clicked.
    @FXML void handleCalendarClick1(){
        monthClicked(1);
    }
    @FXML void handleCalendarClick2(){ monthClicked(2); }
    @FXML void handleCalendarClick3(){
        monthClicked(3);
    }
    @FXML void handleCalendarClick4(){
        monthClicked(4);
    }
    @FXML void handleCalendarClick5(){
        monthClicked(5);
    }
    @FXML void handleCalendarClick6(){
        monthClicked(6);
    }
    @FXML void handleCalendarClick7(){
        monthClicked(7);
    }
    @FXML void handleCalendarClick8(){
        monthClicked(8);
    }
    @FXML void handleCalendarClick9(){
        monthClicked(9);
    }
    @FXML void handleCalendarClick10(){
        monthClicked(10);
    }
    @FXML void handleCalendarClick11(){
        monthClicked(11);
    }
    @FXML void handleCalendarClick12(){
        monthClicked(12);
    }
    @FXML void handleCalendarClick13(){
        monthClicked(13);
    }
    @FXML void handleCalendarClick14(){
        monthClicked(14);
    }
    @FXML void handleCalendarClick15(){
        monthClicked(15);
    }
    @FXML void handleCalendarClick16(){
        monthClicked(16);
    }
    @FXML void handleCalendarClick17(){
        monthClicked(17);
    }
    @FXML void handleCalendarClick18(){
        monthClicked(18);
    }
    @FXML void handleCalendarClick19(){
        monthClicked(19);
    }
    @FXML void handleCalendarClick20(){
        monthClicked(20);
    }
    @FXML void handleCalendarClick21(){
        monthClicked(21);
    }
    @FXML void handleCalendarClick22(){
        monthClicked(22);
    }
    @FXML void handleCalendarClick23(){
        monthClicked(23);
    }
    @FXML void handleCalendarClick24(){
        monthClicked(24);
    }
    @FXML void handleCalendarClick25(){
        monthClicked(25);
    }
    @FXML void handleCalendarClick26(){
        monthClicked(26);
    }
    @FXML void handleCalendarClick27(){
        monthClicked(27);
    }
    @FXML void handleCalendarClick28(){
        monthClicked(28);
    }
    @FXML void handleCalendarClick29(){
        monthClicked(29);
    }
    @FXML void handleCalendarClick30(){
        monthClicked(30);
    }
    @FXML void handleCalendarClick31(){
        monthClicked(31);
    }
    @FXML void handleCalendarClick32(){
        monthClicked(32);
    }
    @FXML void handleCalendarClick33(){
        monthClicked(33);
    }
    @FXML void handleCalendarClick34(){
        monthClicked(34);
    }
    @FXML void handleCalendarClick35(){
        monthClicked(35);
    }
    @FXML void handleCalendarClick36(){
        monthClicked(36);
    }
    @FXML void handleCalendarClick37(){
        monthClicked(37);
    }

}
