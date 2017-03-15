package algorithm;

import calendar.Cell;
import calendar.Activity;
import com.sun.deploy.util.StringUtils;
import database.Connect;
import layout.User;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Henning on 16.02.2017.
 * Lecture = 96
 * SchoolWork = 97
 * deadline = 98
 * exam = 99
 * hjemme eksamen = 95
 *
 *
 * TODO: This call will be the all mighty sorting algorithm for the calendar
 */
public class SuperSorter extends Connect {
    private User user = User.getInstance(); //This is the currently logged in user.
    private Set<Subject> subjects = new HashSet<>();
    private Set<Event> schedule = new HashSet<>();
    private Set<Cell> activities = new HashSet<>();
    private List<Object> prioritizedSchedule = new ArrayList<>();  //This contains both Events and Activities


    public void dataCollect() throws SQLException, ParseException {
        String subjectArrayString = "";
        for (String subject : user.getSubjects()) {
            subjectArrayString += "'" + subject + "',";
        }
        subjectArrayString = subjectArrayString.substring(0, subjectArrayString.length()-1);
        collectSubjects(subjectArrayString);
        collectEvents(subjectArrayString);
        collectActivities();

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
            schedule.add(new Event(
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

    public void collectActivities() throws SQLException, ParseException {
        //TODO: Get all activities which the student have entered
        ResultSet m_result_set = stmt.executeQuery("SELECT * FROM ACTIVITY WHERE studentEmail='"+user.getUsername()+"'");
        while(m_result_set.next()){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            Date startDate = sdf.parse(m_result_set.getString(3) + " " + String.valueOf(m_result_set.getInt(5)));
            Date endDate = sdf.parse(m_result_set.getString(3) + " " + String.valueOf(m_result_set.getInt(6)));
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

    public void addToTimeSlots(){
        addToDay();
        addToWeek();
        addToMonth();
    }

    public void addToDay(){
        //TODO: Sort Cells inside a single Day-object
    }

    public void addToWeek(){
        //TODO: Sort Days inside a Week-object
    }

    public void addToMonth(){
        //TODO: Sort Weeks inside a Month-object

    }



    public Set<Cell> prioritySort(Set<Cell> set){
        //TODO: Sort on prioirty
        //TODO: Implement Counting Sort or whatever
        Set<Cell> sortedSet = new HashSet<>();

        return null;
    }

    //TODO: Merge the two Sets of only Cells
    //TODO: Return the set
    //TODO: Implement an update option so that the algorithm does not have to run all over.
    //TODO: Save the results?
    //TODO: Delete backwards in time?



}
