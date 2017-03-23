package algorithm;

import calendar.Cell;
import calendar.Day;
import calendar.Month;
import calendar.Week;
import controllers.CalendarController;
import database.Connect;
import layout.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
    private Month chosenMonth;

    public void dataCollect() throws SQLException, ParseException {
        //TODO: Fix so that you don't call DB unnecessary many times
        subjects.clear();
        events.clear();
        activities.clear();
        prioritizedSchedule.clear();
        scheduleWithoutCollision.clear();

        String subjectArrayString = "";
        for (String subject : user.getSubjects()) {
            subjectArrayString += "'" + subject + "',";
        }
        subjectArrayString = subjectArrayString.substring(0, subjectArrayString.length()-1);
        collectSubjects(subjectArrayString);
        collectEvents(subjectArrayString);
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
                        m_result_set.getString(2),
                        m_result_set.getDate(3),
                        m_result_set.getDate(4),
                        m_result_set.getString(5),
                        m_result_set.getString(6),
                        m_result_set.getInt(7),
                        m_result_set.getInt(8),
                        m_result_set.getString(9),
                        m_result_set.getInt(10),
                        m_result_set.getString(11)
                ));
            }
        }

    }

    public void collectActivities() throws SQLException, ParseException {
        //TODO: Get all activities which the student have entered
        System.out.println("KJØRER");
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM ACTIVITY WHERE studentEmail='"+user.getUsername()+"'");
        while(m_result_set.next()){
            //CHECKS IF THE EVENT IS MORE THAN ONE MONTH OLD
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            Date startDate = sdf.parse(m_result_set.getString(3) + " " + String.valueOf(m_result_set.getInt(5)));
            Date endDate = sdf.parse(m_result_set.getString(3) + " " + String.valueOf(m_result_set.getInt(6)));
            if(startDate.after((cal.getTime()))){
                activities.add(new Activity(
                        startDate,
                        endDate,
                        m_result_set.getString(2),
                        m_result_set.getString(9),
                        m_result_set.getInt(7),
                        m_result_set.getBoolean(4)
                ));
            }
        }
    }



    //TODO: DO WE NEED THIS?
//    public void addToTimeSlots(){
//        addToMonth();
//    }

//    public Day addToDay(){
//
//        return null;
//    }
//
//    public Week addToWeek(int month, Calendar cal, int weekStart){
//
//        cal.set(Calendar.DAY_OF_MONTH, 1);
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//
//        System.out.println(cal.getTime());
//
//        for(int i=0;i<daysInMonth;i++){
//            Day day = addToDay();
//        }
//
//        System.out.println(daysInMonth);
//        return week;
//    }
//
//    public void addToMonth(){
//
////        Date chosenDate = CalendarController.getInstance().getChosenDate();
//        Date chosenDate = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(chosenDate);
//        int month = cal.MONTH;  //0 indexed, ie january = 0
//        chosenMonth = new Month(month, new LinkedHashSet<>());
//        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        //There are 4 weeks in a month
//        for(int i=0;i<4;i++){
//            Week week = addToWeek(month, cal, i*);
//            chosenMonth.addWeekToWeeksInMonth(week);
//        }
//    }

    public Set<Cell> prioritySort(Set<Cell> set){
        //DONE: Sort on priority
        //DONE: Merge the two Sets of only Cells
        //DONE: Return the set
        ArrayList<Cell> listToSort = new ArrayList<>(set);
        Collections.sort(listToSort, new PriorityComparator());
        return new LinkedHashSet<>(listToSort);
    }

    //TODO: Implement an update option so that the algorithm does not have to run all over.
    //TODO: Save the results?

    public void handleCollisionsInTime(Set<Cell> prioritizedSet){

        for(Cell currentCell : prioritizedSet){
            boolean collision = false;

            for(Cell placedCell : scheduleWithoutCollision){
                if(new TimeComparator().compare(placedCell, currentCell)){
                    System.out.println("KOLLISJON!!!!!");
                    System.out.println(currentCell.getName());
                    System.out.println(placedCell.getName());
                    collision = true;
                }
            }
            if(!collision){
                scheduleWithoutCollision.add(currentCell);
            }else{
                //TODO: PROMPT WHICH CELL TO PRIORITIZE, DELETE THE OTHER FROM DB.
                //TODO: ADD PREFERRED CELL TO scheduleWithoutCollision.
            }
        }
        System.out.println("ORIGINAL SIZE: " + prioritizedSet.size());
        System.out.println("AFTER COLLISION HANDLING: " + scheduleWithoutCollision.size());
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

    public void run() throws SQLException, ParseException {
        System.out.println("DATA COLLECT");
        dataCollect();
        System.out.println("PRIORITY SORT");
        prioritySort(prioritizedSchedule);
        System.out.println("HANDLE COLLISION");
        handleCollisionsInTime(prioritizedSchedule);
        System.out.println("FINISHED");
    }
}
