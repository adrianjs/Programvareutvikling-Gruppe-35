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

    public void addSubject(String subjectCode, String evaluation, String description,
                           String coordinatorEmail){

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO SUBJECT VALUES('"+subjectCode+"', '"+evaluation+"'," +
                    " '"+description+"', '"+coordinatorEmail+"')");

        } catch (SQLException se){
            se.printStackTrace();
        }

    }




    public void addSubject(String subjectCode, String evaluation,
                           String coordinatorEmail){

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO SUBJECT VALUES('"+subjectCode+"', '"+evaluation+"'," +
                    " NULL, '"+coordinatorEmail+"')");
            System.out.println("subject added");

        } catch (SQLException se){
            se.printStackTrace();
        }

    }


    // kommer ut i formen [fagkode, vurdering, beskrivelse]
    public ArrayList<String> getSubjects(String coordinatorEmail){
        ArrayList<String> subject = new ArrayList<String>();
        try {
            ResultSet data = stmt.executeQuery("SELECT * FROM SUBJECT WHERE SUBJECT.coordinatorEmail = '"+coordinatorEmail+"'");
            while (data.next()){
                subject.add(data.getString("subjectCode"));
                subject.add(data.getString("evaluation"));
                if(data.getString("description") != null) {
                    subject.add(data.getString("description"));
                } else {
                    subject.add("");
                }

            }

        } catch (SQLException se){
            se.printStackTrace();
        }

        return subject;
    }


    //----------------------------------------------------EVENT---------------------------------------------------------

    public void addLecture(String name, LocalDate startDate, Time startTime, Time endTime, int repeating,
                           String description, String subjectCode){
        try {
            Date sqlStartDate = Date.valueOf(startDate);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, startTime, endTime, repeating, priority, description, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+startTime+"'," +
                    "'"+endTime+"','"+repeating+"',96,'"+description+"', '"+subjectCode+"')");
            System.out.println("Lecture added");

        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    // addSchoolWork navn, startDato, sluttDato, StartTime, endTime, houersOfWork, prioritet(97) beskrivelse
    //addDeadLine navn, endDate, time, prioritet(98),  beskrivelse
    // addExam navn, startDato, endDato, StartTid, EndTid, prioritet(99), beskrivelse




    //----------------------------------------------------STUDASS-------------------------------------------------------



    public static void main(String[] args) {
        Teacher test = new Teacher();
        LocalDate today = LocalDate.now();
        java.sql.Time sTime = java.sql.Time.valueOf("11:00:00");
        java.sql.Time eTime = java.sql.Time.valueOf("13:00:00");


       //test.addSubject("it5001", "Mappe oppgave","Henning like at det virker","teacher@gmail.com" );
        test.addLecture("lecture2", today, sTime, eTime, 0, "dette er en test", "it5001");

        System.out.println(test.getSubjects("teacher@gmail.com"));
        test.close();

    }




}
