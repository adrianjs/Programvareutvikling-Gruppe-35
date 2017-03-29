package algorithm;

import calendar.Cell;
import database.Connect;
import layout.*;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

/**
 * Created by Henning on 16.02.2017.
 *
 * TODO: This call will be the all mighty sorting algorithm for the calendar
 */
public class SuperSorter extends Connect {
    private User user = User.getInstance(); //This is the currently logged in user.
    private Set<Subject> subjects = new HashSet<>();
    private Set<Cell> events = new HashSet<>();
    private Set<Cell> activities = new HashSet<>();
    private Set<Cell> prioritizedSchedule = new LinkedHashSet<>();  //This contains both Events and Activities
    private Set<Cell> scheduleWithoutCollision = new LinkedHashSet<>(); //This is both sorted and has collisions handled.
    private Set<Cell> droppedEvents = new LinkedHashSet<>(); //This should contain all the events the user does not want to attend.

    //TODO: Implement an update option so that the algorithm does not have to run all over.
    public void run() throws SQLException, ParseException {
        System.out.println("DATA COLLECT");
        dataCollect();
        System.out.println("PRIORITY SORT");
        prioritySort(prioritizedSchedule);
        System.out.println("HANDLE COLLISION");
        //handleCollisionsInTime(prioritizedSchedule);
        System.out.println("FINISHED");
    }

    public void dataCollect() throws SQLException, ParseException {
        subjects.clear();
        events.clear();
        activities.clear();
        prioritizedSchedule.clear();
        scheduleWithoutCollision.clear();

        String subjectArrayString = "";
        for (String subject : user.getSubjects()) {
            subjectArrayString += "'" + subject + "',";
        }
        if(!subjectArrayString.equals("")){
            subjectArrayString = subjectArrayString.substring(0, subjectArrayString.length()-1);
            collectSubjects(subjectArrayString);
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

    public void collectEvents(String subjectArrayString) throws SQLException {
        //DONE: Get all the schedules from students subjects
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM EVENT WHERE subjectCode IN ("+subjectArrayString+")");
        while(m_result_set.next()){
            //CHECKS IF THE EVENT IS MORE THAN ONE MONTH OLD
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            if(new Date(m_result_set.getDate(3).getTime()).after((cal.getTime()))){
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
                        m_result_set.getString(11)
                ));
            }
        }

    }

    public void collectActivities() throws SQLException, ParseException {
        //TODO: Get all activities which the student have entered
//        System.out.println("KJØRER");
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
                        m_result_set.getBoolean(4)
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

    //TODO: Save the results?

    public void handleCollisionsInTime(Set<Cell> prioritizedSet) throws SQLException {
        for(Cell currentCell : prioritizedSet){
            boolean collision = false;
            Cell collisionCell = null;
            for(Cell placedCell : scheduleWithoutCollision){
                if(new TimeComparator().compare(placedCell, currentCell)){
//                    System.out.println("KOLLISJON!!!!!");
//                    System.out.println(currentCell.getName());
//                    System.out.println(placedCell.getName());
                    collision = true;
                    collisionCell = placedCell;
                }
            }
            if(!collision){
                scheduleWithoutCollision.add(currentCell);
            }else{
                JOptionPane.showMessageDialog(null, "Oops! There was a collision in your schedule" +
                        " between " + currentCell.getName() + " and " + collisionCell.getName() + "!", "Collision!", JOptionPane.INFORMATION_MESSAGE);
                if(currentCell.getSlotPriority() == collisionCell.getSlotPriority()){
                    handleSamePriority(currentCell, collisionCell);
                } else if(currentCell.getSlotPriority() > collisionCell.getSlotPriority()){
                    scheduleWithoutCollision.remove(collisionCell);
                    scheduleWithoutCollision.add(currentCell);
                    if(collisionCell.getType().equals("activity")){
                        handleUnprioritizedActivity(collisionCell);
                    }else{
                        handleUnprioritezedEvent(collisionCell);
                    }
                }
                else{
                    if(collisionCell.getType().equals("activity")){
                        handleUnprioritizedActivity(currentCell);
                    }else{
                        handleUnprioritezedEvent(currentCell);
                    }
                }

            }
        }
        System.out.println("ORIGINAL SIZE: " + prioritizedSet.size());
        System.out.println("AFTER COLLISION HANDLING: " + scheduleWithoutCollision.size());
    }

    /**
     * This method prompts the user manually if a collision of two cells also
     * has the same priority.
     * The user gets to choose manually.
     */
    public void handleSamePriority(Cell currentCell, Cell collisionCell) throws SQLException {
        int choice = JOptionPane.showOptionDialog(null, //Component parentComponent
                "Choose which event you want to prioritize!\n" +
                        "They both happen at: " + currentCell.getStartDate(), //Object message,
                "Collision in the schedule!", //String title
                JOptionPane.YES_NO_OPTION, //int optionType
                JOptionPane.QUESTION_MESSAGE, //int messageType
                null, //Icon icon,
                new String[]{currentCell.getName(), collisionCell.getName()}, //Object[] options,
                currentCell.getName());//Object initialValue
        if(choice == 0 ){
            //currentCell was chosen
            scheduleWithoutCollision.remove(collisionCell);
            scheduleWithoutCollision.add(currentCell);
            if(collisionCell.getType().equals("activity")){
                handleUnprioritizedActivity(collisionCell);
                //deleteActivity(collisionCell);
            }else{
                handleUnprioritezedEvent(collisionCell);
            }
        }else{
            //collisionCell/placedCell was chosen
            if(collisionCell.getType().equals("activity")){
                handleUnprioritizedActivity(currentCell);
            }else{
                handleUnprioritezedEvent(currentCell);
            }
        }
    }

    public void handleUnprioritizedActivity(Cell activity) throws SQLException {
       int choice = JOptionPane.showOptionDialog(null,
                "Do you want to delete " + activity.getName() + " from your shcedule," +
                        "\ndo you want Educational Organizer to find somewhere to put it, \nor would you" +
                        " like to give it a new time manually?", "Oops! Something is colliding!", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[]{"Re-schedule (auto)", "Re-schedule (manual)", "Delete"},
                "Delete");
        System.out.println("CHOICE: " + choice);
        if(choice == 0){
            //TODO: RE-SCHEDULE AUTO
            System.out.println("RE-SCHEDULE AUTO");
        }else if(choice == 1){
            //TODO: RE-SCHEDULE MANUAL
            System.out.println("RE-SCHEDULE MANUAL");
        }else{
            System.out.println("DELETING " + activity.getName() + " FROM DB");
            deleteActivity(activity);
        }
    }

    public void handleUnprioritezedEvent(Cell event){

    }

    public void rescheduleAuto(Cell activity){
        //TODO: Write code that automatically changes the time of an activity
        //TODO: Change the times inside object, then push changes to DB
    }

    public void rescheduleManual(Cell activity){
        //TODO: Prompt the user for new times to fill in to the activity
        //TODO: Remember to push changes to DB
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
}
