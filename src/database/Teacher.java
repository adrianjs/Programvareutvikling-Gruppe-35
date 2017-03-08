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


    //---------------------------------------------------EVENTS--------------------------------------------------------

    public void addLecture(String name, LocalDate startDate, Time startTime, Time endTime, int repeating,
                           String description, String subjectCode){
        try {
            Date sqlStartDate = Date.valueOf(startDate);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, startTime, endTime, repeating, priority," +
                    " description, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+startTime+"'," +
                    "'"+endTime+"','"+repeating+"',96,'"+description+"', '"+subjectCode+"')");
            System.out.println("Lecture added");

        } catch (SQLException se){
            se.printStackTrace();
        }
    }


    public void addSchoolWork(String name, LocalDate startDate, LocalDate endDate, Time startTime, Time endTime,
                              int repeating, String description, Double houersOfWork, String subjectCode){
        try{
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);

            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime,repeating, priority," +
                    " description, houersOfWork, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+sqlEndDate+"'," +
                    "'"+startTime+"','"+endTime+"','"+repeating+"',97,'"+description+"','"+houersOfWork+"','"+subjectCode+"')");
            System.out.println("SchoolWork added");


        } catch (SQLException se){
            se.printStackTrace();
        }

    }


    public void addDeadLine(String name, LocalDate date, Time time, String description, String subjectCode){
        try{
            Date deadLineDate = Date.valueOf(date);
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, endDate, endTime, priority, description, subjectCode)" +
                    " VALUES('"+name+"', '"+deadLineDate+"', '"+time+"', 98, '"+description+"', '"+subjectCode+"')");


        } catch (SQLException se){
            se.printStackTrace();
        }

    }



    public void addExam(String name, LocalDate date, Time startTime, Time endTime,
                               String description, String subjectCode){
        try{
            Date sqlDate = Date.valueOf(date);


            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, startTime, endTime, priority, description," +
                    " subjectCode) VALUES('"+name+"','"+sqlDate+"','"+startTime+"','"+endTime+"',99,'"+description+"'," +
                    "'"+subjectCode+"')");
            System.out.println("SchoolWork added");


        } catch (SQLException se){
            se.printStackTrace();
        }

    }

    public void addHomeExam(String name, LocalDate startDate, LocalDate endDate, Time startTime, Time endTime,
                               String description, Double houersOfWork, String subjectCode){
        try{
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);

            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO EVENT(name, startDate, endDate, startTime, endTime, priority," +
                    " description, houersOfWork, subjectCode) VALUES('"+name+"','"+sqlStartDate+"','"+sqlEndDate+"'," +
                    "'"+startTime+"','"+endTime+"',95,'"+description+"','"+houersOfWork+"','"+subjectCode+"')");
            System.out.println("SchoolWork added");


        } catch (SQLException se){
            se.printStackTrace();
        }

    }




    //----------------------------------------------------STUDASS-------------------------------------------------------

    //----------------------------------------------TESTING AV METODER--------------------------------------------------



    public static void main(String[] args) {
        Teacher test = new Teacher();
        LocalDate today = LocalDate.now();
        java.sql.Time sTime = java.sql.Time.valueOf("11:00:00");
        java.sql.Time eTime = java.sql.Time.valueOf("18:00:00");


       //test.addSubject("it5001", "Mappe oppgave","Henning like at det virker","teacher@gmail.com" );
        //test.addLecture("lecture2", today, sTime, eTime, 0, "dette er en test", "it5001");
        //test.addSchoolWork("forbredelse", today, today, sTime, eTime,0, "les side 11-22", 2.0, "it5001");
        //test.addDeadLine("prosjekt", today, eTime,"alle deller av prosjektet må leveres inn", "it5001");
        //test.addExam("eksamen", today, sTime, eTime, "en heilt jævlig eksamen", "TDT999");
        //test.addHomeExam("hjemme eksamen", today, today, sTime, eTime, "hjemme eksamen er vell mest sannsynlig dragvold fag? (CHILL)", 4.0, "TEST5132");

        System.out.println(test.getSubjects("teacher@gmail.com"));
        test.close();

    }




}
