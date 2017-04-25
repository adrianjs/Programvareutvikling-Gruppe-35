package database;
import calendar.Cell;
import layout.User;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * Created by torresrl on 21/02/2017.
 *
 *
 * -------------- LES FØR DU LEGGER NOE TIL---------------------
 *
 *
 * Denne klassen er for å koble seg til databasen og hente ut data
 *
 * Metodene kan være laget for en spesiel ting så skriv hvem som laget den og hvor den skal
 * brukes i koden (lokasjon og hva den henter ut)
 *
 * Ikke lag noen spesiele metoder over linjen metoder, for her har vi de mest standariserte metodene
 * som å koble seg til, stenge tilkoblingen, legge til og hente ut for hver enkel tabll.
 * Hvis du skal lage flere spesialiserte metoder fåreslår jeg og lage en subklasse.
 *
 */

public class Connect {
    //Login info
    static final String URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/torresrl_eduorg";
    static final String user = "torresrl_data";
    static final  String pass = "admin";

    public Connection conn = null;
    public Statement stmt = null;

    //When you instanciate the class, it automatically connects
    private static Connect instance = null; //InstanceControl singelton Pattern.
    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    /**
     * kobler opp EO opp mot databasen.
     */

    public Connect(){
        try {
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, user, pass); 
            stmt = conn.createStatement();

        } catch (SQLException se){
            se.printStackTrace();

        } catch (Exception e){
            e.printStackTrace();
        }

        if(conn != null){
            System.out.println("Connected to database");
        }
    }

    /**
     * stenger forbindelsen med databsen
     */

    // Closing the connection after use
    public void close(){
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("statment closed");
            }

            if (conn != null){
                conn.close();
                System.out.println("connection closed");
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    //----------------------------------------------------METHODS----------------------------------------------------

    /**
     * Leger til student i databsen
     * @param email
     * mailen til studenten
     * @param firstName
     * Fornavn
     * @param lastName
     * EtterNavn
     * @param field
     * Studieprogram
     * @param year
     * Hvilke studie år
     * @param pass
     * Passord
     */

    public void addStudent(String email, String firstName, String lastName, String field, int year, String pass){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO STUDENT VALUES('"+email+"','"+firstName+"', '"+lastName+"', '"+field+
                    "', '"+year+"', '"+pass+"', '"+""+"')");
        } catch (SQLException se){
            se.printStackTrace();

        }
    }

    /**
     * Leger til Lærer i databsen
     * @param email
     * Emailen
     * @param firstName
     * Fornavn
     * @param lastName
     * Etternavn
     * @param department
     * Avdeling
     * @param description
     * Beskrivelse av proffesoren
     * @param pass
     * Passordet
     */

    public void addTeacher(String email, String firstName, String lastName, String department, String description, String pass){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO COURSECOORDINATOR VALUES('"+email+"','"+firstName+"', '"+lastName+"', '"+department+
                    "', '"+pass+"', '"+description+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Legge til aktivitet
     * @param name
     * navne på aktiviteten
     * @param date
     * når er aktiviteten
     * @param repeating
     * skal den repeteres?
     * @param priority
     * Hvor viktig er den
     * @param startTime
     * når starter det
     * @param endTime
     * når slutter det
     * @param studentEmail
     * mail til studenten som legger aktiviteten til
     * @param description
     * beskrivelse av aktiviteten
     */
    public void addActivity(String name, Date date, boolean repeating, int priority, double startTime, double endTime, String studentEmail, String description){
        int repeatingInt = (repeating) ? 1 : 0;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO ACTIVITY(name, date, repeating, priority, startTime, endTime, studentEmail, description, color) VALUES('"+name+"','"+date+"','"
                    +repeatingInt+"','"+priority+"','"+startTime+"','"+endTime+"','"+studentEmail+"','"+
                    description+"', '"+"75bc1b"+"')");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void addStudentSubject(String subject) throws SQLException{
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO STUDTAKESUB(subjectCode, studentEmail) VALUES('"+subject+"','"+User.getInstance().getUsername()+"')");
    }

    public void removeStudentSubject(String subject) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM STUDTAKESUB WHERE subjectCode='"+subject+"' AND studentEmail='"+User.getInstance().getUsername()+"'");
    }

    public void deleteActivity(Cell activity) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM ACTIVITY WHERE name='"+activity.getName()+"' AND date='" +(new SimpleDateFormat("yyyy-MM-dd").format(activity.getStartDate()))+
                "' AND startTime='" +activity.getStartTime()+"' AND endTime='"+activity.getEndTime()+"' AND studentEmail='"+User.getInstance().getUsername()+"'");
        System.out.println("Deleted activity: " + activity.getName());
    }

} //END
