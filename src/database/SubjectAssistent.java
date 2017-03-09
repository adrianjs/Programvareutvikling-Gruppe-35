package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by torresrl on 09/03/2017.
 */
public class SubjectAssistent extends Connect {

    public SubjectAssistent(){
        super();
    }

    //-----------------------------ADD SUBJECTASSISTENT------------------------------------

    public void addSubjectAssistent(String studentEmail, String description, String subjectCode){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO SUBJECTASSISTENT VALUES('"+studentEmail+"', '"+description+"'," +
                    " '"+subjectCode+"')");
            System.out.println("Subject assisten added");

        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public void addSubjectAssistent(String studentEmail,  String subjectCode){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO SUBJECTASSISTENT VALUES('"+studentEmail+"', '"+""+"', '"+subjectCode+"')");
            System.out.println("Subject assisten added");

        } catch (SQLException se){
            se.printStackTrace();
        }
    }



    //----------------------------GET STUDENT ASSISTENT----------------------------------------
    /*
    kommer i formen [[mail, beskrivelse, fagkode], [mail, beskrivelse, fagkode]]
     */



    public ArrayList<ArrayList<String>> getSubjectAssistentOnSubjectCode(String subjectCode){
        ArrayList<ArrayList<String>> subjectAssistents = new ArrayList<ArrayList<String>>();
        try{
            ResultSet data = stmt.executeQuery("SELECT * FROM SUBJECTASSISTENT WHERE SUBJECTASSISTENT.subjectCode = '"+subjectCode+"' ");
            while (data.next()){
                ArrayList<String> subjectAssistent = new ArrayList<String>();
                subjectAssistent.add(data.getString("studentEmail"));
                subjectAssistent.add(data.getString("description"));
                subjectAssistent.add(data.getString("subjectCode"));
                subjectAssistents.add(subjectAssistent);

            }

            System.out.println("subject assistent fetched");



        } catch(SQLException se){
            se.printStackTrace();
        }

        return subjectAssistents;
    }


    public ArrayList<ArrayList<String>> getSubjectAssistentOnEmail(String studentEmail){
        ArrayList<ArrayList<String>> subjectAssistents = new ArrayList<ArrayList<String>>();
        try{
            ResultSet data = stmt.executeQuery("SELECT * FROM SUBJECTASSISTENT WHERE SUBJECTASSISTENT.studentEmail = '"+studentEmail+"' ");
            while (data.next()){
                ArrayList<String> subjectAssistent = new ArrayList<String>();
                subjectAssistent.add(data.getString("studentEmail"));
                subjectAssistent.add(data.getString("description"));
                subjectAssistent.add(data.getString("subjectCode"));
                subjectAssistents.add(subjectAssistent);

            }

            System.out.println("subject assistent fetched");



        } catch(SQLException se){
            se.printStackTrace();
        }

        return subjectAssistents;
    }


    //---------------------------------------------SUBJECTASSISTEN ON ROOM----------------------------------------------


    public void addStudentAssistentOnRoom( String roomNr, String studentEmail, String subjectCode){
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO ONROOM VALUES ('"+roomNr+"', '"+studentEmail+"', '"+subjectCode+"')");
            System.out.println("StudentAssistent on room added ");

        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getStudentAssistentOnSubjectCode(String subjectCode){
        ArrayList<ArrayList<String>> StudentAssistens = new ArrayList<ArrayList<String>>();
        try{
            ResultSet data = stmt.executeQuery("SELECT * FROM ONROOM WHERE ONROOM.subjectCode = '"+subjectCode+"'");
            while(data.next()) {
                ArrayList<String> studentAssistent = new ArrayList<>();

                studentAssistent.add(data.getString("roomNr"));
                studentAssistent.add(data.getString("studentEmail"));
                studentAssistent.add(data.getString("subjectCode"));
                StudentAssistens.add(studentAssistent);
            }
            System.out.println("onroom fetched");
        } catch(SQLException se){
            se.printStackTrace();
        }
        return StudentAssistens;
    }


    public ArrayList<ArrayList<String>> getStudentAssistentOnSudentEmail(String studentEmail){
        ArrayList<ArrayList<String>> StudentAssistens = new ArrayList<>();
        try{
            ResultSet data = stmt.executeQuery("SELECT * FROM ONROOM WHERE ONROOM.studentEmail = '"+studentEmail+"'");
            while(data.next()) {
                ArrayList<String> studentAssistent = new ArrayList<>();

                studentAssistent.add(data.getString("roomNr"));
                studentAssistent.add(data.getString("studentEmail"));
                studentAssistent.add(data.getString("subjectCode"));
                StudentAssistens.add(studentAssistent);
            }
            System.out.println("onroom fetched");
        } catch(SQLException se){
            se.printStackTrace();
        }
        return StudentAssistens;
    }





}
