package database;
import algorithm.Subject;
import com.gargoylesoftware.htmlunit.javascript.host.intl.DateTimeFormat;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by torresrl on 08/03/2017.
 */
public class Teacher{

    private Connect connect = Connect.getInstance();

    //sjekke slik at reapiting vil vare mellom disse datoene
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    private String endSpringString = "01/05/2017";
    private String startSpringString = "01/01/2017";
    private String endFallString = "01/12/2017";
    private String startFallString = "01/08/2017";
    private LocalDate endSpring = LocalDate.parse(endSpringString, formatter);
    private LocalDate startSpring = LocalDate.parse(startSpringString, formatter);
    private LocalDate endFall = LocalDate.parse(endFallString, formatter);
    private LocalDate startFall = LocalDate.parse(startFallString, formatter);


    public Teacher(){
        super();
    }

    String color;

    public void addCourseCoordinator(String email, String firstName, String lastName, String department, String password, String description ){
        try {
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO COURSECOORDINATOR VALUES('"+email+"', '"+firstName+"'," +
                    " '"+lastName+"', '"+department+"', '"+password+"','"+description+"')");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    //----------------------------------------------------SUBJECT-------------------------------------------------------

    public boolean addSubject(String subjectCode, String evaluation, String description, String coordinatorEmail){
        try {
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO SUBJECT VALUES('"+subjectCode+"', '"+evaluation+"'," +
                    " '"+description+"', '"+coordinatorEmail+"')");
        }catch (SQLException se){
            se.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Kommer ut i formen [fagkode, vurdering, beskrivelse]
     * @param coordinatorEmail
     * @return
     */
    //THIS IS USED! BOUND TO BUTTON IN FXML
    public ArrayList<ArrayList<String>> getSubjects(String coordinatorEmail){
        ArrayList<ArrayList<String>> subjects = new ArrayList<ArrayList<String>>();
        try {
            ResultSet data = connect.stmt.executeQuery("SELECT * FROM SUBJECT WHERE SUBJECT.coordinatorEmail = '"+coordinatorEmail+"'");
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
            Integer startTimeSql = Integer.valueOf(startTime.substring(0, 2));
            Integer endTimeSql = Integer.valueOf(endTime.substring(0, 2));
            color = "FF9200";
            if(!endTime.substring(2).equals(":00:00")){
                endTimeSql++;
            }
            if(repeating == 0) {
                Date sqlStartDate = Date.valueOf(startDate);
                connect.stmt = connect.conn.createStatement();
                connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                        " description, subjectCode, color) VALUES('" + name + "','" + sqlStartDate + "','" + sqlStartDate + "','" + startTimeSql + "'," +
                        "'" + endTimeSql + "','" + repeating + "',96,'" + description + "', '" + subjectCode + "', '" + color + "')");
            } else if (startDate.getMonthValue() > 0 && startDate.getMonthValue() < 5){
                Date sqlStartDate;
                int counter = 0;
                while (startDate.getMonthValue() > 0 && startDate.getMonthValue() < 5){
                    sqlStartDate = Date.valueOf(startDate);
                    connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                            " description, subjectCode, color) VALUES('" + name + "','" + sqlStartDate + "','" + sqlStartDate + "','" + startTimeSql + "'," +
                            "'" + endTimeSql + "','" + repeating + "',96,'" + description + "', '" + subjectCode + "', '" + color + "')");
                    startDate = startDate.plusWeeks(1);
                }
            } else if (startDate.getMonthValue() >= 5 && startDate.getMonthValue() < 12){
                Date sqlStartDate;
                int counter = 0;
                while (startDate.getMonthValue() >= 5 && startDate.getMonthValue() < 12){
                    sqlStartDate = Date.valueOf(startDate);
                    connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                            " description, subjectCode, color) VALUES('" + name + "','" + sqlStartDate + "','" + sqlStartDate + "','" + startTimeSql + "'," +
                            "'" + endTimeSql + "','" + repeating + "',96,'" + description + "', '" + subjectCode + "', '" + color + "')");
                    startDate = startDate.plusWeeks(1);
                }
            }
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
            Integer startTimeSql = Integer.valueOf(startTime.substring(0,2));
            Integer endTimeSql = Integer.valueOf(endTime.substring(0,2));
            color = "260591";
            connect.stmt = connect.conn.createStatement();
            if(repeating == 0) {
                Date sqlStartDate = Date.valueOf(startDate);
                Date sqlEndDate = Date.valueOf(endDate);
                connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                        " description, houersOfWork, subjectCode, color) VALUES('"+name+"','"+sqlStartDate+"','"+sqlStartDate+"','"+startTimeSql+"'," +
                        "'"+endTimeSql+"','"+repeating+"',97,'"+description+"', '"+houersOfWork+"', '"+subjectCode+"', '"+color+"')");
            } else if(startDate.getMonthValue() > 0 && startDate.getMonthValue() < 5){
                Date sqlStartDate;
                Date sqlEndDate;

                while(startDate.getMonthValue() > 0 && startDate.getMonthValue() < 5) {

                    sqlStartDate = Date.valueOf(startDate);
                    sqlEndDate = Date.valueOf(endDate);
                    connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                            " description, houersOfWork, subjectCode, color) VALUES('"+name+"','"+sqlStartDate+"','"+sqlStartDate+"','"+startTimeSql+"'," +
                            "'"+endTimeSql+"','"+repeating+"',97,'"+description+"', '"+houersOfWork+"', '"+subjectCode+"', '"+color+"')");
                    startDate = startDate.plusWeeks(1);
                    endDate = endDate.plusWeeks(1);
                }

            } else if(startDate.getMonthValue() >= 5 && startDate.getMonthValue() < 12){
                Date sqlStartDate;
                Date sqlEndDate;

                while(startDate.getMonthValue() >= 5 && startDate.getMonthValue() < 12) {
                    sqlStartDate = Date.valueOf(startDate);
                    sqlEndDate = Date.valueOf(endDate);
                    connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                            " description, houersOfWork, subjectCode, color) VALUES('"+name+"','"+sqlStartDate+"','"+sqlStartDate+"','"+startTimeSql+"'," +
                            "'"+endTimeSql+"','"+repeating+"',97,'"+description+"', '"+houersOfWork+"', '"+subjectCode+"', '"+color+"')");
                    startDate = startDate.plusWeeks(1);
                    endDate = endDate.plusWeeks(1);
                }

            }
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
            Integer timeSql = Integer.valueOf(time);
            Date deadLineDate = Date.valueOf(date);
            color = "FF0000";
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                    " description, subjectCode, color) VALUES('"+name+"','"+deadLineDate+"','"+deadLineDate+"','"+timeSql+"'," +
                    "'"+timeSql+"','"+0+"',98,'"+description+"', '"+subjectCode+"', '"+color+"')");
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
            Integer startTimeSql = Integer.valueOf(startTime.substring(0,2));
            Integer endTimeSql = Integer.valueOf(endTime.substring(0,2));
            Date sqlDate = Date.valueOf(date);
            color = "FF0000";
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, repeating, priority," +
                    " description, subjectCode, color) VALUES('"+name+"','"+sqlDate+"','"+sqlDate+"','"+startTimeSql+"'," +
                    "'"+endTimeSql+"','"+0+"',99,'"+description+"', '"+subjectCode+"', '"+color+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Adding new home exam to database.
     * @param name name
     * @param startDate startDate
     * @param endDate endDate
     * @param startTime startTime
     * @param endTime endTime
     * @param description description
     * @param houersOfWork hours
     * @param subjectCode subjectcode
     */
    public void addHomeExam(String name, LocalDate startDate, LocalDate endDate, String startTime, String endTime,
                               String description, Double houersOfWork, String subjectCode){
        try{//converting to sql values.
            Integer startTimeSql = Integer.valueOf(startTime.substring(0,2));
            Integer endTimeSql = Integer.valueOf(endTime.substring(0,2));
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            color = "FFE800";
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, priority," +
                    " description, houersOfWork, subjectCode, color) VALUES('"+name+"','"+sqlStartDate+"','"+sqlEndDate+"'," +
                    "'"+startTimeSql+"','"+endTimeSql+"',95,'"+description+"','"+houersOfWork+"','"+subjectCode+"', '"+color+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    // returnerer på formen [[eventID, name, startDate, endDate, reapiting, priority, description houersOfWork], [eventID, name, startDate, endDate, reapiting, priority, description houersOfWork] ]
    // verdier som ikke skla være med i forskjelige eventer kan stå som null (som string) eller 0 for int.
    //THIS IS USED! BOUND TO BUTTON IN FXML
    public ArrayList<ArrayList<String>> getEvents(String subjectCode){
        ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();
        try {
            ResultSet data = connect.stmt.executeQuery("SELECT * FROM EVENT WHERE EVENT.subjectCode = '"+subjectCode+"'");
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



    /**
     * Get events whith right subjectcode where the id of subject match the id colnum at feedback-table
     * @param subject Subjectcode
     * @return List with feedbacks from a given subject.
     * @throws SQLException If there is none feedback in this subject on homework.
     */
    public ArrayList<ArrayList<String>> getStudentFeedBack(String subject) throws SQLException {
        //Schoolwork som denne læreren har satt inn. trenger kun id. Ta med dato.
        ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();
        connect.stmt = connect.conn.createStatement();
        ResultSet set = connect.stmt.executeQuery("SELECT FEEDBACK.Feedback, EVENT.startDate, EVENT.description, EVENT.houersOfWork FROM FEEDBACK " +
                        "INNER JOIN EVENT ON FEEDBACK.ID=EVENT.eventID WHERE EVENT.subjectCode = '"+subject+"';");
//        "SELECT * FROM EVENT WHERE subjectCode = '"+subject+"' AND priority = '"+96+"'"

        while (set.next()){
            ArrayList<String> event = new ArrayList<>();
            event.add(Integer.toString(set.getInt(1)));
            event.add(set.getString(2));
            event.add(set.getString(3));
            event.add(Integer.toString(set.getInt(4)));
            events.add(event);

        }
        return events;
    }

    public String getTeacher(String subjectCode){
        String corrdinator = "";
        try {
            connect.stmt = connect.conn.createStatement();
            ResultSet set = connect.stmt.executeQuery("SELECT SUBJECT.coordinatorEmail, SUBJECT.subjectCode FROM SUBJECT WHERE SUBJECT.subjectCode = '" + subjectCode + "'");
            while (set.next()) {
                corrdinator = set.getString("coordinatorEmail");
                return corrdinator;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return corrdinator;
    }

}
