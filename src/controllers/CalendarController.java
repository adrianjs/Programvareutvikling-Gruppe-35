package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import algorithm.Activity;
import algorithm.SuperSorter;
import calendar.*;
import calendar.Cell;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import controllers.add.AddActivityController;
import controllers.add.AddSubjectController;
import database.Connect;
import database.Fetcher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import layout.*;

public class CalendarController extends Connect implements Initializable{
	private Date chosenDate;
	private SuperSorter superSorter = new SuperSorter();

	@FXML GridPane day; //DAY PANE
	@FXML GridPane week; //WEEK PANE
	@FXML GridPane month; //MONTH PANE
	@FXML AnchorPane dayAnchor; //Anchorpane day

	//AI panes.
    HamburgerBackArrowBasicTransition tran;
    @FXML private JFXDrawer drawer;
    AnchorPane botto;
    @FXML JFXHamburger sliderButton; //Slide button
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
    int dayClicked = 0; //Day clicked on in MonthTab
    User user = User.getInstance();

	//*************** HENNINGS ULTRAFELT *****************//
	//DAY
	List<Label> dayTabLabels = new ArrayList<>(); // Hentes fra GUI
	List<calendar.Cell> cellsAtCurrentDate = new ArrayList<>(); // Skal fylles fra database

	Map<TimeInterval, Label> dayTabTimeSlots = new LinkedHashMap<>();
	public Map<Label, calendar.Cell> labelMappedCells = new LinkedHashMap<>(); //Ferdig mappet celler til labels

	//MONTH
	List<Label> monthLabels = new ArrayList<>();; // Hentes fra GUI
	List<AnchorPane> monthAnchorPanes = new ArrayList<>(); // Hentes fra GUI
	Map<Label, AnchorPane> dayMappedPane = new LinkedHashMap<>();// Lages i metode.
	Map<LocalDate, AnchorPane> dateMappedMonth = new LinkedHashMap<>(); //Lages i metode.

	//****************************************************//
    ArrayList<Label> eventLabels = new ArrayList<>(); //MonthTabEventLabels --> Used to remove text in labels in monthTab.

    //Mapping used in to weekTab.
    HashMap<Integer, List> weekLabelList = new HashMap<>(); //FROM GUI
    Map<LocalDate, HashMap<TimeInterval, Label>> weekDateLinkedToDay = new LinkedHashMap<>();
    ArrayList<LocalDate> weekCalendarList = new ArrayList<LocalDate>();//Dates this week.
    public Map<Label, calendar.Cell> weekLabelMappCell = new LinkedHashMap<>();

    //Methods starts here.

	private static CalendarController instance = null; //InstanceControl
	public static CalendarController getInstance() {
		if (instance == null) {
			instance = new CalendarController();
		}
		return instance;
	}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Init");
		setLines();
        setDate();
        setupDayTab();
        setBottoField(); //SlideFieldBotto
		try {
			getWeekTabCells();
			mapMonthTab();
            superSorter.dataCollect();
            insertCells();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void setLines(){ //Set lines for day week and month.
		day.setGridLinesVisible(true);
		week.setGridLinesVisible(true);
		month.setGridLinesVisible(true);
	}
    //On action from remove button
	public void remove() throws IOException {
        //TODO: Make remove button work
		//TODO: Remove this superSorter-thing
		Set<Cell> temp = superSorter.prioritySort(superSorter.getPrioritizedSchedule());
		for (Cell c:temp) {
			System.out.println(c.getSlotPriority());
		}
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

	/**
	 * Set window back to loginScreen.
	 * @throws Exception
	 */
	public void logOut() throws Exception {
		System.out.println("LogOut");
		Stage s = (Stage) logout.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/username.fxml"));
		Parent load = loader.load();
		Scene scene = new Scene(load);
		s.setScene(scene);
		CalendarController.instance = null; //Set instance to null so you can log in again as an other user.
		System.out.println("logout successful");
	}

    /**
     * Sets the ask.fxml to the botto anchorpane
     */
    public void setBottoField(){
        try {
            botto = FXMLLoader.load(getClass().getResource("../resources/ask.fxml"));
            tran = new HamburgerBackArrowBasicTransition(sliderButton);
            tran.setRate(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Open and close the Bot field, and set value to hamburger.
	 */
    public void slidePane(){
        drawer.setSidePane(botto);
        tran.setRate(tran.getRate()*-1);
        tran.play();
        if(drawer.isShown()){
            askButton.setText("Open Botto");
            drawer.close();
        }else{
            drawer.open();
            askButton.setText("Close Botto");
        }
    }
    //On action from add button
    public void add(){
        AddActivityController a = cal.changeToAdd("../resources/add.fxml", "ADD"); //Get the instance of the add controller.
        System.out.println("add");
    }

	/**
	 * Set new date when date in datapicker is changed.
	 */
	public void setNewDate(){
        LocalDate dato = date.getValue();
        setNewDate2(dato);
	}

	/**
	 *
	 * @param dato Takes in the localdate from the setNewDate Method.
	 */
	public void setNewDate2(LocalDate dato){
        changeDate(dato);
        chosenDate = Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant());
        setupDayTab();
        mapMonthTab();
        insertCells();
    }

	//Started to make methods to change veiw when minicalendar is changed.
	/**
	 * The new date set from datePicker adjusted to fit monthOrganizer
	 * @param dato date from setNewDate2.
	 */
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
		if(teller == 0){//Only needed to do this once per instance.
			addLabelsToList();
			teller++;
		}
        monthOrganizer(year1, month1-1, day1);
	}

	/**
	 * Remove 0 if it is first in the string.
	 * @param tall
	 * @return
	 */
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

	/**
	 * Fix the veiw of the monthTab so the dates is on the right day in relation to wich month it is.
	 * @param year
	 * @param month
	 * @param day
	 */
    public void monthOrganizer(int year, int month, int day){ //Set labels on month part to the right month.
		System.out.println(year + " " + month + " " + day);
		java.util.Calendar cal = new GregorianCalendar(year, month, day);
        java.util.Calendar cal1 = new GregorianCalendar(year, month, 1);
		int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);
		int weekOfYear = cal.get(java.util.Calendar.WEEK_OF_YEAR);
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
		int tall = 1;
		for(int i = 0; i < monthLabels.size(); i++) {
			if ((i >= firstDay) && (tall <= lastDateOfMonth)) {
				monthLabels.get(i).setText(Integer.toString(tall));
                tall++;
            } else {
				monthLabels.get(i).setText("");
            }
		}
		tall = 1;
	}


    /**
     * Makes a list of dates in week, so every day in the chosen week has a date connected to it.(LocalDate)
     * Then we kan put values to labels like it is done in the daytab.
     * @param week
     */
	public void weekOrganizer(int year, int month, int day, int week, int dayofWeek){
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
		//System.out.println(weekCalendarList.toString() + "WEEKCALENDARLIST");
		clearWeekTimeSlots();
        mapWeekLabelsToTimeIntervals();

	}

	//GetLabelSoMonthOrganizerCanChangeiy
	public void StringGetLabel(String label){
		System.out.println("yo");
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
                System.out.println(date);
                date = date.plusDays(1);
                dateMappedMonth.put(date, monthAnchorPanes.get(i));

            }
        }
    }

    //Go to that given day when month is clicked.
	public void monthClicked(int tall){
	    dayClicked = tall; //So the value can be used in WatchDayMonthTabController.
        String day = monthLabels.get(tall-1).getText();
        try{
			int dayInt = Integer.parseInt(day);
			LocalDate date = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			//System.out.println(date.toString());
			int year = date.getYear();
			int month = date.getMonthValue();
			//System.out.println(dayInt +"day" + month + "month" + year + "year");
			LocalDate date1 = LocalDate.of(year, month, dayInt);
			setNewDate2(date1);
			if(day.length() != 0){
				WatchDayMonthTabController dayVeiw = cal.changeToWatchDay("../resources/watchDay.fxml", date1.toString());
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

	//Set initalize date.
	public void setDate(){
        Date input = new Date();
        LocalDate dato = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        changeDate(dato);
        chosenDate = Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant());
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

        ArrayList<Activity> activities = new ArrayList<Activity>();
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

    public void setupDayTab(){
		System.out.println("Setup day tab");
		clearTimeSlots();
        addTimeToTimeToList();
        try {
			System.out.println("get cells");
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
			System.out.println("new fetcher");
			Fetcher fetch = new Fetcher("SELECT * FROM ACTIVITY");
            Set<List> activities = fetch.getUserRelatedResults(10); //If 9 columns, input 10 (#columns + 1)
            for(List activity : activities){
                //System.out.println(activity + " ACTIVITY");
                SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
                //TODO: This should not be put in cellsAtCurrentDate, but for test purposes it stays
                cellsAtCurrentDate.add(new Activity(
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
		insertDayCells();
		insertWeekCells();
		insertMonthCells();
	}

	public void insertDayCells(){
		boolean stretch = false;
		for (calendar.Cell cell : cellsAtCurrentDate){
			System.out.println("NEW ENTRY");
			for (Map.Entry<TimeInterval, Label> entry : dayTabTimeSlots.entrySet())
			{
				if(stretch){
					Label lab = entry.getValue();
					if(!lab.getText().contains("Continiue")){
						lab.setText("Continiue --> " + entry.getValue().getText());
						lab.setStyle("-fx-text-fill: royalblue ;");
					}
					labelMappedCells.put(lab, cell);
					//lab.setText("");
					stretch = false;
					if(entry.getKey().getEndTime().before(cell.getEndDate())){
						stretch = true;
					}
				}
                stretch = insertCellHelper(entry, cell, stretch, labelMappedCells);
			}
		}
	}
	public void insertWeekCells(){
		System.out.println("SETTING NEW CELLS");
		weekLabelMappCell.clear();
		boolean stretch = false;
		for (calendar.Cell cell : cellsAtCurrentDate){ //Går igjennom alle cells for denne USER
			Date input = cell.getStartDate();
			LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //CELL opererer med dateObjekter --> Må gjøres om til localdate
			int day = 0; //hvilken av dagslistene det skal skrives til.
			for (LocalDate lDate : weekCalendarList) { //Går igjennom datoer denne uken.
				if(date.equals(lDate)){ //Om en av cellene matcher med en av datoene i weekCalendarlist.
					for (Map.Entry<LocalDate, HashMap<TimeInterval, Label>> list: weekDateLinkedToDay.entrySet()) { //Går igjennom For å finne en av datoene som matcher nøkkel
						// for så å gå i gjennom verdiene i den hashmappen som er linket til nøkkel.
						if(list.getKey() == lDate){
							HashMap<TimeInterval, Label> liste = list.getValue(); //Setter da en ny hashmap til hashmappet som har rett nøkkel.
							for (Map.Entry<TimeInterval, Label> timeintervalMapLabel : liste.entrySet()) { //Gjør så det samme som DayTab.. samme effekt på alle dager den uken.
								if(stretch){
									Label lab = timeintervalMapLabel.getValue(); //Continue
									lab.setStyle("-fx-text-fill: royalblue ;");
									weekLabelMappCell.put(timeintervalMapLabel.getValue(), cell);
									stretch = false;
									if(timeintervalMapLabel.getKey().getEndTime().before(cell.getEndDate())){
										stretch = true;
									}
								}
								stretch = insertCellHelper(timeintervalMapLabel, cell, stretch, weekLabelMappCell);
							}
						}
					}
                }
				day++;
			}
			printWeekCells(weekLabelMappCell); //Printer herfta direkte.
		}
	}


	public void insertMonthCells(){
		removeMonthActivityLabel();
		System.out.println("Month");
		for (calendar.Cell cell : cellsAtCurrentDate){
			Date startDate = cell.getStartDate();
			LocalDate date = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			for(Map.Entry<LocalDate, AnchorPane> entry : dateMappedMonth.entrySet()){
				if(entry.getKey().equals(date)){
					//IF There is something with high priority --> Can change this.
					Label lab = new Label();
					lab.setText(" " + '\n' +  " Event/Activity");
					lab.setStyle("-fx-text-fill: green;");
					entry.getValue().getChildren().addAll(lab);
					eventLabels.add(lab);
                }
			}
		}
	}

    /**
     * Week and Day insertCells uses the same method.
     * @param map
     * @param cell
     * @param stretch
     * @param mapTo
     * @return
     */
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
    }



	/**
	 * Removes Removes the month activity Labels so you new ones can be set.
	 */
	public void removeMonthActivityLabel(){
		for (Label l: eventLabels) {
			l.setText("");
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
		System.out.println("Write to labels");
		System.out.println(User.getInstance().getUsername());
		label.setText(cell.getName());
		System.out.println(cell.getName());
	}


	//****Lars Lager Stuff til weekTab********************************************************************************
    /**
     * Get all week tab cells, an add them to a hash
     */
	public void getWeekTabCells() throws ParseException {
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
    public void clearWeekTimeSlots(){
		for (Map.Entry<Integer, List> list : weekLabelList.entrySet()){
            for (Object label : list.getValue()) {
                Label l = (Label) label;
                l.setText("");
            }
        }
	}

    /**
     * Skriver ut til Labels på ukesTab.
     * @param mappedCells En liste med alle mappede celler i denne uken.
     */
    public void printWeekCells(Map<Label, calendar.Cell> mappedCells){
        //System.out.println("Print Week Cells");
        for (Map.Entry<Label, Cell> l : mappedCells.entrySet()) {
            l.getKey().setText(l.getValue().getName());
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
    @FXML private void handleCalendarClick1(){
        monthClicked(1);
    }
    @FXML private void handleCalendarClick2(){ monthClicked(2); }
    @FXML private void handleCalendarClick3(){
        monthClicked(3);
    }
    @FXML private void handleCalendarClick4(){
        monthClicked(4);
    }
    @FXML private void handleCalendarClick5(){
        monthClicked(5);
    }
    @FXML private void handleCalendarClick6(){
        monthClicked(6);
    }
    @FXML private void handleCalendarClick7(){
        monthClicked(7);
    }
    @FXML private void handleCalendarClick8(){
        monthClicked(8);
    }
    @FXML private void handleCalendarClick9(){
        monthClicked(9);
    }
    @FXML private void handleCalendarClick10(){
        monthClicked(10);
    }
    @FXML private void handleCalendarClick11(){
        monthClicked(11);
    }
    @FXML private void handleCalendarClick12(){
        monthClicked(12);
    }
    @FXML private void handleCalendarClick13(){
        monthClicked(13);
    }
    @FXML private void handleCalendarClick14(){
        monthClicked(14);
    }
    @FXML private void handleCalendarClick15(){
        monthClicked(15);
    }
    @FXML private void handleCalendarClick16(){
        monthClicked(16);
    }
    @FXML private void handleCalendarClick17(){
        monthClicked(17);
    }
    @FXML private void handleCalendarClick18(){
        monthClicked(18);
    }
    @FXML private void handleCalendarClick19(){
        monthClicked(19);
    }
    @FXML private void handleCalendarClick20(){
        monthClicked(20);
    }
    @FXML private void handleCalendarClick21(){
        monthClicked(21);
    }
    @FXML private void handleCalendarClick22(){
        monthClicked(22);
    }
    @FXML private void handleCalendarClick23(){
        monthClicked(23);
    }
    @FXML private void handleCalendarClick24(){
        monthClicked(24);
    }
    @FXML private void handleCalendarClick25(){
        monthClicked(25);
    }
    @FXML private void handleCalendarClick26(){
        monthClicked(26);
    }
    @FXML private void handleCalendarClick27(){
        monthClicked(27);
    }
    @FXML private void handleCalendarClick28(){
        monthClicked(28);
    }
    @FXML private void handleCalendarClick29(){
        monthClicked(29);
    }
    @FXML private void handleCalendarClick30(){
        monthClicked(30);
    }
    @FXML private void handleCalendarClick31(){
        monthClicked(31);
    }
    @FXML private void handleCalendarClick32(){
        monthClicked(32);
    }
    @FXML private void handleCalendarClick33(){
        monthClicked(33);
    }
    @FXML private void handleCalendarClick34(){
        monthClicked(34);
    }
    @FXML private void handleCalendarClick35(){
        monthClicked(35);
    }
    @FXML private void handleCalendarClick36(){
        monthClicked(36);
    }
    @FXML private void handleCalendarClick37(){
        monthClicked(37);
    }
}
