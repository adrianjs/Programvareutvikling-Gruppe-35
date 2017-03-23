package database;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by torresrl on 08/03/2017.
 */
public class Teacher extends Connect{

    public Teacher(){
        super();
    }

    //----------------------------------------------------SUBJECT-------------------------------------------------------

    public void addSubject(String subjectCode, String evaluation, String description, String coordinatorEmail){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO SUBJECT VALUES('"+subjectCode+"', '"+evaluation+"'," +
                    " '"+description+"', '"+coordinatorEmail+"')");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public void addSubject(String subjectCode, String evaluation, String coordinatorEmail){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO SUBJECT VALUES('"+subjectCode+"', '"+evaluation+"'," +
                    " NULL, '"+coordinatorEmail+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Kommer ut i formen [fagkode, vurdering, beskrivelse]
     * @param coordinatorEmail
     * @return
     */
    public ArrayList<ArrayList<String>> getSubjects(String coordinatorEmail){
        ArrayList<ArrayList<String>> subjects = new ArrayList<ArrayList<String>>();
        try {
            ResultSet data = stmt.executeQuery("SELECT * FROM SUBJECT WHERE SUBJECT.coordinatorEmail = '"+coordinatorEmail+"'");
            while (data.next()){
                ArrayList<String> subject = new ArrayList<String>();
                subject.add(data.getString("subjectCode"));
                subject.add(data.getString("evaluation"));
                if(data.getString("description") != null) {
                    subject.add(data.getString("description"));
                } else {
                    subject.add("");
                }
                subjects.add(subject);
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return subjects;
    }


    //---------------------------------------------------EVENTS--------------------------------------------------------

    /**
     * Adding a new Lecture event to database.
     * @param name
     * @param startDate
     * @param startTime
     * @param endTime
     * @param repeating
     * @param description
     * @param subjectCode
     */
    public void addLecture(String name, LocalDate startDate, String startTime, String endTime, int repeating,
                           String description, String subjectCode){
        try {
            //Converting to sql values
            Time startTimeSql = Time.valueOf(startTime);
            Time endTimeSql = Time.valueOf(endTime);
            Date sqlStartDate = Date.valueOf(startDate);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, startTime, endTime, repeating, priority," +
                    " description, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+startTimeSql+"'," +
                    "'"+endTimeSql+"','"+repeating+"',96,'"+description+"', '"+subjectCode+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Adding a new schoolwork event to database.
     * @param name
     * @param startDate
     * @param endDate
     * @param startTime
     * @param endTime
     * @param repeating
     * @param description
     * @param houersOfWork
     * @param subjectCode
     */
    public void addSchoolWork(String name, LocalDate startDate, LocalDate endDate, String startTime, String endTime,
                              int repeating, String description, Double houersOfWork, String subjectCode){
        try{//Converting to sql values.
            Time startTimeSql = java.sql.Time.valueOf(startTime);
            Time endTimeSql = java.sql.Time.valueOf(endTime);
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime,repeating, priority," +
                    " description, houersOfWork, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+sqlEndDate+"'," +
                    "'"+startTimeSql+"','"+endTimeSql+"','"+repeating+"',97,'"+description+"','"+houersOfWork+"','"+subjectCode+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Adding new deadline event to the database.
     * @param name
     * @param date
     * @param time
     * @param description
     * @param subjectCode
     */
    public void addDeadLine(String name, LocalDate date, String time, String description, String subjectCode){
        try{//Converting to sql values.
            Time timeSql = java.sql.Time.valueOf(time);
            Date deadLineDate = Date.valueOf(date);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, endDate, endTime, priority, description, subjectCode)" +
                    " VALUES('"+name+"', '"+deadLineDate+"', '"+timeSql+"', 98, '"+description+"', '"+subjectCode+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Adding new exam event to database.
     * @param name
     * @param date
     * @param startTime
     * @param endTime
     * @param description
     * @param subjectCode
     */
    public void addExam(String name, LocalDate date, String startTime, String endTime,
                               String description, String subjectCode){
        try{//Converting to sql Values
            Time startTimeSql = java.sql.Time.valueOf(startTime);
            Time endTimeSql = java.sql.Time.valueOf(endTime);
            Date sqlDate = Date.valueOf(date);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, startTime, endTime, priority, description," +
                    " subjectCode) VALUES('"+name+"','"+sqlDate+"','"+startTimeSql+"','"+endTimeSql+"',99,'"+description+"'," +
                    "'"+subjectCode+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Adding new home exam to database.
     * @param name
     * @param startDate
     * @param endDate
     * @param startTime
     * @param endTime
     * @param description
     * @param houersOfWork
     * @param subjectCode
     */
    public void addHomeExam(String name, LocalDate startDate, LocalDate endDate, String startTime, String endTime,
                               String description, Double houersOfWork, String subjectCode){
        try{//converting to sql values.
            Time startTimeSql = java.sql.Time.valueOf(startTime);
            Time endTimeSql = java.sql.Time.valueOf(endTime);
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, priority," +
                    " description, houersOfWork, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+sqlEndDate+"'," +
                    "'"+startTimeSql+"','"+endTimeSql+"',95,'"+description+"','"+houersOfWork+"','"+subjectCode+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    // returnerer på formen [[eventID, name, startDate, endDate, reapiting, priority, description houersOfWork], [eventID, name, startDate, endDate, reapiting, priority, description houersOfWork] ]
    // verdier som ikke skla være med i forskjelige eventer kan stå som null (som string) eller 0 for int.
    public ArrayList<ArrayList<String>> getEvents(String subjectCode){
        ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();
        try {
            ResultSet data = stmt.executeQuery("SELECT * FROM EVENT WHERE EVENT.subjectCode = '"+subjectCode+"'");
            while (data.next()){
                ArrayList<String> event = new ArrayList<String>();
                event.add(Integer.toString(data.getInt("eventID")));
                event.add(data.getString("name"));
                event.add("" + data.getDate("startDate"));
                event.add("" + data.getDate("endDate"));
                event.add(Integer.toString(data.getInt("repeating")));
                event.add(Integer.toString(data.getInt("priority")));
                event.add(data.getString("description"));
                event.add(Integer.toString(data.getInt("houersOfWork")));
                events.add(event);

            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return events;
    }
}
