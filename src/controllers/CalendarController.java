package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.GregorianCalendar;

import algorithm.Activity;
import algorithm.Event;
import algorithm.SuperSorter;
import calendar.*;
import calendar.Cell;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import database.Connect;
import database.Fetcher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import layout.*;
//import layout.eventButtonWeek.activityButton;
import scraping.GetAllSubjectsNTNU;
import scraping.SileniumDriver;
import layout.eventButton;

public class CalendarController extends Connect implements Initializable{

    private Date chosenDate;
	private SuperSorter superSorter = new SuperSorter();

	@FXML private GridPane day; //DAY PANE
	@FXML private GridPane week; //WEEK PANE
	@FXML private GridPane month; //MONTH PANE
	@FXML private AnchorPane dayAnchor; //Anchorpane day

	//AI panes.
    private HamburgerBackArrowBasicTransition tran;
    @FXML private JFXDrawer drawer;
    private AnchorPane botto;
    @FXML private JFXHamburger sliderButton; //Slide button
    AnchorPane rightside;

    @FXML private JFXButton askButton;
	@FXML private JFXButton logout;
	@FXML private JFXButton remove;

	//Labels for Day pane.
	@FXML private Label thisday;

	//Labels week-Pane

	//Tabs
	@FXML private Tab dayTab;
	@FXML private Tab weekTab;
	@FXML private Tab monthTab;

    //Date
	@FXML private JFXDatePicker date;

	//To set a new scene.
	private layout.Calendar cal = new layout.Calendar();

    // List of labels in day Tab...
    @FXML private List<Label> timeToTime = new ArrayList<>();

    //CountVariables
    private int teller = 0;
    private int dayClicked = 0; //Day clicked on in MonthTab
    private User user = User.getInstance();


    //------------------------Lister som brukes til å printe ut til weeks.
    private ArrayList<Cell> cells = new ArrayList<>();//liste over activitys som skal inn i kalenderen
    private ArrayList<eventButton> oldActivityButtons = new ArrayList<>();//liste over activitys som ligger i calenderen denne uken
    private ArrayList<LocalDate> activitysDate = new ArrayList<>(); //Liste over acktiitys som har blit printet inn i listen

    //*************** HENNINGS ULTRAFELT *****************//
	//DAY
	private List<Label> dayTabLabels = new ArrayList<>(); // Hentes fra GUI
	private List<calendar.Cell> cellsAtCurrentDate = new ArrayList<>(); // Skal fylles fra database

	private Map<TimeInterval, Label> dayTabTimeSlots = new LinkedHashMap<>();
	public Map<Label, calendar.Cell> labelMappedCells = new LinkedHashMap<>(); //Ferdig mappet celler til labels

	//MONTH
	private List<Label> monthLabels = new ArrayList<>();; // Hentes fra GUI
	private List<AnchorPane> monthAnchorPanes = new ArrayList<>(); // Hentes fra GUI
	private Map<Label, AnchorPane> dayMappedPane = new LinkedHashMap<>();// Lages i metode.
	private Map<LocalDate, AnchorPane> dateMappedMonth = new LinkedHashMap<>(); //Lages i metode.
    private ArrayList<LocalDate> doubleDate = new ArrayList<>(); // holder telling på datoer som er 2 ganger;

	//****************************************************//
    private ArrayList<Label> eventLabels = new ArrayList<>(); //MonthTabEventLabels --> Used to remove text in labels in monthTab.

    //Mapping used in to weekTab.
    private HashMap<Integer, List> weekLabelList = new HashMap<>(); //FROM GUI
    private Map<LocalDate, HashMap<TimeInterval, Label>> weekDateLinkedToDay = new LinkedHashMap<>();
    private ArrayList<LocalDate> weekCalendarList = new ArrayList<LocalDate>();//Dates this week.
    public Map<Label, calendar.Cell> weekLabelMappCell = new LinkedHashMap<>();

    //Methods starts here.

	private static CalendarController instance = null; //InstanceControl singelton Pattern.
	public static CalendarController getInstance() {
		if (instance == null) {
			instance = new CalendarController();
		}
		return instance;
	}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
		setLines();
        setDate();
		try {
			superSorter.run();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setupDayTab();
        setBottoField(); //SlideFieldBotto
		try {
			getWeekTabCells();
			mapMonthTab();
            insertCells();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void refresh(){
		setLines();
		setDate();
		try {
			superSorter.run();
			setupDayTab();
			setBottoField(); //SlideFieldBotto
			getWeekTabCells();
			mapMonthTab();
			insertCells();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
     * OnAction from removebutton.
     * @throws IOException
     * @throws SQLException
     * @throws ParseException
     */
	public void remove() throws IOException, SQLException, ParseException, InterruptedException {
        //TODO: Make remove button work
		//sc.setFirstDateOfDay(1);
    }

    /**
     * OnAction from addsubject-button.
     * @throws IOException
     */
	public void addSubject() throws IOException {

		//AddSubjectController a = cal.changeToAddSubject("../resources/addSubject.fxml", "Add subject");
		cal.changeScene("/resources/fxml/addSubject.fxml", "Add subject");
	}

	/**
	 * Set window back to loginScreen.
	 * @throws Exception
	 */
	public void logOut() throws Exception {
		Stage s = (Stage) logout.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/username.fxml"));
		Parent load = loader.load();
		Scene scene = new Scene(load);
		s.setScene(scene);
		CalendarController.instance = null; //Set instance to null so you can log in again as an other user.
	}

    /**
     * Sets the ask.fxml to the botto anchorpane
     */
    private void setBottoField(){
        try {
            botto = FXMLLoader.load(getClass().getResource("/resources/fxml/ask.fxml"));
            tran = new HamburgerBackArrowBasicTransition(sliderButton);
            tran.setRate(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Open and close the Bot field, and set value to hamburger. OnAction from botto-Button.
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

    /**
     * OnAction from addButton, opens add.fxml from Calendar.class.
     * @throws IOException
     */
    public void add() throws IOException {
        cal.changeScene("/resources/fxml/add.fxml", "ADD");
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
	private void setNewDate2(LocalDate dato){
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
	private void changeDate(LocalDate dato){
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
	private int removeZero(String tall){
        if(tall.charAt(0) == '0'){
			return Character.getNumericValue(tall.charAt(1));
		}
		return Integer.parseInt(tall);
	}

	//Add labels tin day tab to list

    /**
     * Add labels in dayTab to list.
     */
    private void addTimeToTimeToList(){
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

    /**
     * Add labels in monthTab to list.
     */
	private void addLabelsToList(){
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
		System.out.println(year + " " + month + " " + day);
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
		int tall = 1;
		for(int i = 0; i < monthLabels.size(); i++) {
			if ((i >= firstDay) && (tall <= lastDateOfMonth)) {
				monthLabels.get(i).setText(Integer.toString(tall));
                tall++;
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
	private void mapMonthTab(){
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

    /**
     * Go to the given day when month gridpane is clicked. Loads activities from this day in
     * @param tall integer clicked.
     */
	private void monthClicked(int tall){
	    dayClicked = tall; //So the value can be used in WatchDayMonthTabController.
        String day = monthLabels.get(tall-1).getText();
        try{
			int dayInt = Integer.parseInt(day);
			LocalDate date = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = date.getYear();
			int month = date.getMonthValue();
			LocalDate date1 = LocalDate.of(year, month, dayInt);
			setNewDate2(date1);
			if(day.length() != 0){
                cal.changeScene("/resources/fxml/watchDay.fxml", date1.toString());
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}

    /**
     * Sets the date when application starts.
     */
	private void setDate(){
        Date input = new Date();
        LocalDate dato = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        changeDate(dato);
        chosenDate = Date.from(dato.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    //Clear all timeslots dayPane.

    /**
     * Clear all timeslots in dayPane
     */
    private void clearTimeSlots(){

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

    /**
     * Sets up the daytab.
     */
    private void setupDayTab(){
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

    /**
     * Gets cells from the database
     * @throws ParseException
     */
    private void getCells() throws ParseException {
	    //TODO: endre liste når supersort er ferdig
            cells = new ArrayList<>(superSorter.getPrioritizedSchedule());
			cellsAtCurrentDate = new ArrayList<>(superSorter.getPrioritizedSchedule());

    }

    private Date setHour(Date date, int hour){
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

	private void insertDayCells(){
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


	private void insertWeekCells(){
		System.out.println("SETTING NEW CELLS");

		for(eventButton ab : oldActivityButtons){
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
						eventButton event;
						if(cell.getClass() == Activity.class) {
							event = new eventButton(cell.getName(), cell.getDescription(), cell);
							week.add(event.getEvent(), day, Integer.parseInt(cell.getStartTime()) - 7,
									1, Integer.parseInt(cell.getEndTime()) - Integer.parseInt(cell.getStartTime()));
						} else {
							Event eventCell = (Event) cell;
							event = new eventButton(cell.getName(), cell.getDescription(), eventCell.getSubjectCode(), cell);
							week.add(event.getEvent(), day, Integer.parseInt(cell.getStartTime()) - 7,
									1, Integer.parseInt(cell.getEndTime()) - Integer.parseInt(cell.getStartTime()));
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


	private void insertMonthCells(){
		removeMonthActivityLabel();
		System.out.println("Month");
		ArrayList<LocalDate> usedDate = new ArrayList<>();

		for (calendar.Cell cell : cellsAtCurrentDate){
			LocalDate date;
			if(cell.getStartDate().getClass() == java.sql.Date.class){
				date = new Date(cell.getStartDate().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			} else {
				date = cell.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}


			for(Map.Entry<LocalDate, AnchorPane> entry : dateMappedMonth.entrySet()){
				if(entry.getKey().equals(date)){
					//IF There is something with high priority --> Can change this.

					if(usedDate.contains(entry.getKey()) || doubleDate.contains(entry.getKey())){
                        Label oldLabel = (Label) entry.getValue().getChildren().get(1);
                        entry.getValue().getChildren().remove(1);

                        String[] checkLength = oldLabel.toString().split("\n");
                        Label lab = new Label();
                        String oldLabelToString = "\n";
                        if(checkLength.length <= 3){
                            oldLabelToString = oldLabel.getText() + "\n" + cell.getName();
                        } else {
                            for(int i = 1; i < 3; i ++){
								oldLabelToString += checkLength[i] + "\n";

                            }
							oldLabelToString += "Click to get more...";
                        }



                        lab.setText(oldLabelToString);
                        lab.setStyle("-fx-text-fill: green;");
                        entry.getValue().getChildren().addAll(lab);
                        eventLabels.add(lab);

						if(!doubleDate.contains(entry.getKey())){
                            doubleDate.add(entry.getKey());
                        }


					} else {
						Label lab = new Label();
						lab.setText(" " + '\n' + cell.getName());
						lab.setStyle("-fx-text-fill: green;");
						entry.getValue().getChildren().addAll(lab);
						eventLabels.add(lab);
						usedDate.add(entry.getKey());


                    }




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
	private void removeMonthActivityLabel(){
		for (Label l: eventLabels) {
			l.setText("");
		}
	}

    private void enterCells(){
		for (Map.Entry<Label, calendar.Cell> entry : labelMappedCells.entrySet())
		{
			writeToLabel(entry.getKey(), entry.getValue());
		}
	}

	private void writeToLabel(Label label, calendar.Cell cell){
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
