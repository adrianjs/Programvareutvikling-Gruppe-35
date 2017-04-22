package algorithm;

import calendar.Cell;
import com.jfoenix.controls.JFXDatePicker;
import controllers.CalendarController;
import database.Connect;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;
import layout.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Calendar;

/**
 * Created by Henning on 16.02.2017.
 * eksamen = 99
 * deadline = 98
 * skolearbeid = 97
 * lecture = 96
 * homeEksamen = 95
 */
public class SuperSorter extends Connect {
    private User user = User.getInstance(); //This is the currently logged in user.
    private Set<Subject> subjects = new HashSet<>();
    private Set<Cell> events = new HashSet<>();
    private Set<Cell> activities = new HashSet<>();
    private Set<Cell> prioritizedSchedule = new LinkedHashSet<>();  //This contains both Events and Activities
    private Set<Cell> scheduleWithoutCollision = new LinkedHashSet<>(); //This is both sorted and has collisions handled.
    private Set<Integer> droppedEvents = new LinkedHashSet<>(); //This should contain all the events the user does not want to attend.
    private Set<Cell> deadlines = new LinkedHashSet<>();

    public void run() throws SQLException, ParseException, IOException {
        System.out.println("DATA COLLECT");
        dataCollect();
        System.out.println("PRIORITY SORT");
        prioritizedSchedule = prioritySort(prioritizedSchedule);
        System.out.println("PICKING OUT DEADLINES");
        pickOutDeadlines(prioritizedSchedule);
        System.out.println("HANDLE COLLISION");
        handleCollisionsInTime(prioritizedSchedule);
        System.out.println("APPLY DEADLINES");
        applyDeadlines();
        System.out.println("FINISHED");
    }



    public void dataCollect() throws SQLException, ParseException {
        subjects.clear();
        events.clear();
        activities.clear();
        prioritizedSchedule.clear();
        scheduleWithoutCollision.clear();
        droppedEvents.clear();
        deadlines.clear();

        user.updateSubjects();

        String subjectArrayString = "";
        for (String subject : user.getSubjects()) {
            subjectArrayString += "'" + subject + "',";
        }
        if(!subjectArrayString.equals("")){
            subjectArrayString = subjectArrayString.substring(0, subjectArrayString.length()-1);
            collectSubjects(subjectArrayString);
            collectDroppedEvents();
            collectEvents(subjectArrayString);
        }
        collectActivities();
        prioritizedSchedule.addAll(events);
        prioritizedSchedule.addAll(activities);
    }

    public void collectSubjects(String subjectArrayString) throws SQLException {
        //DONE: Get all the subjects that the student attends
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM SUBJECT WHERE subjectCode IN ("+subjectArrayString+")");
        while(m_result_set.next()){
            subjects.add(new Subject(
                    m_result_set.getString(1),
                    m_result_set.getString(2),
                    m_result_set.getString(3),
                    m_result_set.getString(4)
            ));
        }
    }

    public void collectDroppedEvents() throws SQLException {
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM NOTATTENDINGEVENT WHERE studentEmail='"+User.getInstance().getUsername()+"'");
        while (m_result_set.next()){
            droppedEvents.add(m_result_set.getInt(1));
        }
    }

    public void collectEvents(String subjectArrayString) throws SQLException {
        //DONE: Get all the schedules from students subjects
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM EVENT WHERE subjectCode IN ("+subjectArrayString+")");
        while(m_result_set.next()){
            //CHECKS IF THE EVENT IS MORE THAN ONE MONTH OLD
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            if(new Date(m_result_set.getDate(3).getTime()).after((cal.getTime())) && !droppedEvents.contains(m_result_set.getInt(1))){
                events.add(new Event(
                        m_result_set.getDate(3),
                        m_result_set.getDate(4),
                        m_result_set.getString(5),
                        m_result_set.getString(6),
                        m_result_set.getString(2),
                        m_result_set.getString(9),
                        m_result_set.getInt(8),
                        m_result_set.getBoolean(7),
                        m_result_set.getInt(10),
                        m_result_set.getString(11),
                        m_result_set.getInt(1),
                        m_result_set.getString(12)
                ));
            }
        }

    }

    public void collectActivities() throws SQLException, ParseException {
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM ACTIVITY WHERE studentEmail='"+user.getUsername()+"'");
        while(m_result_set.next()){
            //CHECKS IF THE ACTIVITY IS MORE THAN ONE MONTH OLD
            //If it is, it's deleted from the DB
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            Date startDate = sdf.parse(m_result_set.getString(3) + " " + String.valueOf(m_result_set.getInt(5)));
            Date endDate = sdf.parse(m_result_set.getString(3) + " " + String.valueOf(m_result_set.getInt(6)));
            if(startDate.after((cal.getTime()))){
                activities.add(new Activity(
                        startDate,
                        endDate,
                        String.valueOf(m_result_set.getInt(5)),
                        String.valueOf(m_result_set.getInt(6)),
                        m_result_set.getString(2),
                        m_result_set.getString(9),
                        m_result_set.getInt(7),
                        m_result_set.getBoolean(4),
                        m_result_set.getInt(1),
                        m_result_set.getString(10)
                ));
            }else{
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM ACTIVITY WHERE activityID='"+m_result_set.getInt(1)+"'");
            }
        }
    }

    public Set<Cell> prioritySort(Set<Cell> set){
        //DONE: Sort on priority
        //DONE: Merge the two Sets of only Cells
        //DONE: Return the set
        ArrayList<Cell> listToSort = new ArrayList<>(set);
        Collections.sort(listToSort, new PriorityComparator());
        return new LinkedHashSet<>(listToSort);
    }

    public void pickOutDeadlines(Set<Cell> cells){
        Set<Cell> originalList = new LinkedHashSet<>(cells);
        for(Cell cell : originalList){
            if(cell.getSlotPriority() == 98){
                deadlines.add(cell);
                prioritizedSchedule.remove(cell);
            }
        }
    }

    public void applyDeadlines() {
        scheduleWithoutCollision.addAll(deadlines);
    }

    public void handleCollisionsInTime(Set<Cell> prioritizedSchedule) throws SQLException, IOException, ParseException {
        for(Cell currentCell : prioritizedSchedule){
            boolean collision = false;
            Cell collisionCell = null;
            for(Cell placedCell : scheduleWithoutCollision){
                if(new TimeComparator().compare(placedCell, currentCell)){
                    System.out.println("KOLLISJON!");
                    System.out.println(currentCell.getName());
                    System.out.println(placedCell.getName());
                    collision = true;
                    collisionCell = placedCell;
                }
            }
            if(!collision){
                scheduleWithoutCollision.add(currentCell);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Oops! There was a collision in you schedule between " +
                        stringFormatterForCell(currentCell) + " and " + stringFormatterForCell(collisionCell) + "!");
                alert.setTitle("Collision");
//                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(600, 220);
                alert.setHeaderText("(b)Otto has discovered a collision!");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image((getClass().getResourceAsStream("/img/EO.png"))));
                alert.showAndWait();

                if(currentCell.getSlotPriority() == collisionCell.getSlotPriority()){
                    handleSamePriority(currentCell, collisionCell);
                } else if(currentCell.getSlotPriority() > collisionCell.getSlotPriority()){
                    scheduleWithoutCollision.remove(collisionCell);
                    scheduleWithoutCollision.add(currentCell);
                    if(collisionCell.getType().equals("activity")){
                        handleUnprioritizedActivity(collisionCell);
                    }else{
                        handleUnprioritizedEvent(collisionCell);
                    }
                }
                else{
                    if(currentCell.getType().equals("activity")){
                        handleUnprioritizedActivity(currentCell);
                    }else{
                        handleUnprioritizedEvent(currentCell);
                    }
                }
            }
        }
        System.out.println("ORIGINAL SIZE: " + prioritizedSchedule.size());
        System.out.println("AFTER COLLISION HANDLING: " + scheduleWithoutCollision.size());
    }

    public String stringFormatterForCell(Cell cell){
        String output;
        if(cell instanceof Activity){
            output = cell.getName();
        }else{
            output = cell.getName() + " - " + ((Event) cell).getSubjectCode();
        }
        return output;
    }

    /**
     * This method prompts the user manually if a collision of two cells also
     * has the same priority.
     * The user gets to choose manually.
     */
    public void handleSamePriority(Cell currentCell, Cell collisionCell) throws SQLException, IOException, ParseException {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setPrefSize(600, 220);
        dialog.setTitle("Handle priority");
        dialog.setHeaderText("Choose which event/activity you want to prioritize!");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image((getClass().getResourceAsStream("/img/EO.png"))));
        dialog.setContentText("They both happen on : " + currentCell.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() +
                "\n" + currentCell.getName() + " starts at : " + currentCell.getStartTime() + " and ends at : " + currentCell.getEndTime() +
                "\n" + collisionCell.getName() + " starts at : " + collisionCell.getStartTime() + " and ends at : " + collisionCell.getEndTime());
        dialog.getDialogPane().getButtonTypes().setAll(
                new ButtonType(currentCell.getName() +"-"+ ((Event) currentCell).getSubjectCode(), ButtonBar.ButtonData.OK_DONE),
                new ButtonType(collisionCell.getName() +"-"+ ((Event) collisionCell).getSubjectCode(), ButtonBar.ButtonData.CANCEL_CLOSE)
        );

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
            //currentCell was chosen
            scheduleWithoutCollision.remove(collisionCell);
            scheduleWithoutCollision.add(currentCell);
            if(collisionCell.getType().equals("activity")){
                handleUnprioritizedActivity(collisionCell);
                deleteActivity(collisionCell);
            }else{
                handleUnprioritizedEvent(collisionCell);
            }
        }else{
            //collisionCell/placedCell was chosen
            if(collisionCell.getType().equals("activity")){
                handleUnprioritizedActivity(currentCell);
            }else{
                handleUnprioritizedEvent(currentCell);
            }
        }
    }

    public void handleUnprioritizedActivity(Cell activity) throws SQLException, IOException, ParseException {
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setPrefSize(600, 220);
        dialog.setTitle("Choose an option");
        dialog.setHeaderText("How do you want to handle " + activity.getName() + "?");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image((getClass().getResourceAsStream("/img/EO.png"))));
        dialog.setContentText("Do you:\n1. Want to delete " + activity.getName() + " from your schedule?" +
                "\n2. Want b(Otto) to find somewhere to put it? \n3. Want to give it a new time manually?");
        dialog.getDialogPane().getButtonTypes().setAll(
                new ButtonType("Reschedule (auto)", ButtonBar.ButtonData.OK_DONE),
                new ButtonType("Reschedule (manual)", ButtonBar.ButtonData.CANCEL_CLOSE),
                new ButtonType("Delete", ButtonBar.ButtonData.APPLY)
        );

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
            //Reschedule auto
            rescheduleAuto(activity);

        }else if(result.get().getButtonData().equals(ButtonBar.ButtonData.CANCEL_CLOSE)){
            //Reschedule manual
            rescheduleManual(activity);

        }else{
            //Delete
            deleteActivity(activity);
        }
    }

    public void handleUnprioritizedEvent(Cell event) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO NOTATTENDINGEVENT(eventId, studentEmail) VALUES('"+event.getID()+"', '"+User.getInstance().getUsername()+"')");
    }

    public void rescheduleAuto(Cell activity){
        //TODO: Write code that automatically changes the time of an activity
        //TODO: Change the times inside object, then push changes to DB
        System.out.println("RE-SCHEDULE AUTO");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Coming soon...");
        alert.showAndWait();
    }

    public void findNewTime(Cell activity) throws SQLException {
        Date originalStartDate = activity.getStartDate();
        Date originalEndDate = activity.getEndDate();
        String originalStartTime = activity.getStartTime();
        String originalEndTime = activity.getEndTime();

        Date today = new Date();
        Date currentDate = activity.getStartDate();
        Date lastPossible;
        if(activity.getType().equals("event") && activity.getSlotPriority()==97){
            lastPossible = getClosestLecture(activity);
        }else {lastPossible = getLastPossibleDate();}

        Integer startTime = Integer.parseInt(activity.getStartTime());
        Integer endTime = Integer.parseInt(activity.getEndTime());
        Integer diff = endTime - startTime;
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.set(Calendar.HOUR_OF_DAY, startTime);
        boolean validTime = false;
        boolean direction = false;

        //Display animation
        while(!validTime && !direction){
            //Change time, Dont set after 20 or before 08, backwards first
            if(startTime >=9){
                startTime--;
                endTime--;
            } else{
                endTime = 20;
                startTime = endTime - diff;
                cal.set(Calendar.DAY_OF_MONTH, -1);

            }
            cal.set(Calendar.HOUR_OF_DAY, startTime);
            activity.setStartTime(startTime.toString());
            activity.setEndTime(endTime.toString());
            activity.setStartDate(cal.getTime());
            cal.set(Calendar.HOUR_OF_DAY, endTime);
            activity.setEndDate(cal.getTime());

            validTime = detectCollision(activity);

            //If reached today, then turn.
            if(activity.getStartDate().before(today)){
                //Turns the direction to go
                direction = true;
            }
        }

        activity.setStartDate(originalStartDate);
        activity.setEndDate(originalEndDate);
        activity.setStartTime(originalStartTime);
        activity.setEndTime(originalEndTime);

        while(!validTime && direction){
            if(endTime<=19){
                startTime++;
                endTime++;
            }
            else{
                startTime = 8;
                endTime = startTime + diff;
            }
            validTime = detectCollision(activity);
        }

        //If no new time (might happen, only option is to delete)
        if(activity.getStartDate().after(lastPossible)){
            //TODO: Prompt with that it will be deleted
            //deleteActivity(activity);
        }
        //New time found. Remove animation.
        //changeTime();  Fill out correctly


    }

    public Date getLastPossibleDate() {
        //One month ahead
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public void changeTimeLocally(Cell activity){

    }

    public Date getClosestLecture(Cell schoolWork){
        Date date = new Date();
        //Get from database, first lecture, where ID > schoolworkID, in same subject
        //Get starttime and endtime, and date
        Calendar cal = Calendar.getInstance();

        date = cal.getTime();
        return date;

    }

    public boolean detectCollision(Cell activity){

        return true;
    }

    public void rescheduleManual(Cell activity) throws SQLException, IOException, ParseException {
        setupDialog(activity);
    }

    public void resetDroppedEvents() throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM NOTATTENDINGEVENT WHERE studentEmail='"+user.getUsername()+"'");
        droppedEvents.clear();
    }

    public void resetDroppedEvent(Cell event) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM NOTATTENDINGEVENT WHERE studentEmail='"+user.getUsername()+"' AND eventId='"+event.getID()+"'");
        droppedEvents.remove(event);
    }

    public void setupDialog(Cell activity) throws SQLException, IOException, ParseException {
        Dialog dialog = new Dialog();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new javafx.scene.image.Image((getClass().getResourceAsStream("/img/EO.png"))));
        javafx.stage.Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> dialog.close());

        JFXDatePicker datePicker = new JFXDatePicker();
        JFXDatePicker startTimePicker = new JFXDatePicker();
        startTimePicker.setShowTime(true);
        JFXDatePicker endTimePicker = new JFXDatePicker();
        endTimePicker.setShowTime(true);

        datePicker.setValue(activity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        startTimePicker.setTime(activity.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
        endTimePicker.setTime(activity.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());

        HBox hBox1 = new HBox(new Label("Enter a new date:           "), datePicker);
        hBox1.setAlignment(Pos.CENTER);
        HBox hBox2 = new HBox(new Label("Enter a new start time:   "), startTimePicker);
        hBox2.setAlignment(Pos.CENTER);
        HBox hBox3 = new HBox(new Label("Enter a new end date:    "), endTimePicker);
        hBox3.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(new Label("Select New Times for your Activity"), hBox1, hBox2, hBox3);
        ((Stage) dialog.getDialogPane().getScene().getWindow()).setMaxWidth(400);
        dialog.getDialogPane().setContent(vBox);
        dialog.getDialogPane().getButtonTypes().setAll(new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
        dialog.setTitle("Choose a new time");
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)){
            System.out.println("OK IS PRESSED!");
            changeTime(activity, datePicker.getValue(), startTimePicker.getTime(), endTimePicker.getTime());
            CalendarController.getInstance().refresh();
        }
    }

    private void changeTime(Cell activity, LocalDate date, LocalTime time1, LocalTime time2) throws SQLException {
        Cell newActivity = activity;
        Date startDate;
        System.out.println("LOCAL TIMES: " + time1 +  " " + time2);
        String startTime = time1.toString();
        String endTime = time2.toString();

        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        startDate = cal.getTime();

        deleteActivity(activity);

        addActivity(newActivity.getName(),
                new java.sql.Date(startDate.getTime()),
                newActivity.isRepeating(),
                newActivity.getSlotPriority(),
                Integer.valueOf(startTime.substring(0,2)),
                Integer.valueOf(endTime.substring(0,2)),
                user.getUsername(),
                newActivity.getDescription());
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public Set<Cell> getEvents() {
        return events;
    }

    public Set<Cell> getActivities() {
        return activities;
    }

    public Set<Cell> getPrioritizedSchedule() {
        return prioritizedSchedule;
    }

    public Set<Cell> getScheduleWithoutCollision() {
        return scheduleWithoutCollision;
    }

    public Set<Cell> getDeadlines(){ return deadlines; }
}
