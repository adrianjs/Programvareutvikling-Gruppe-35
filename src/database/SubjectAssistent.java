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





//--------------------------------------- FOR TESTING --------------------------------------

    public static void main(String[] args) {
        SubjectAssistent test = new SubjectAssistent();
        System.out.println(test.getSubjectAssistentOnEmail("torres.lande@gmail.com"));
        test.close();
    }


}
