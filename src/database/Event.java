package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by torresrl on 30/03/2017.
 */
public class Event extends Connect {

    public Event() {
        super();
    }


    public void deleteEvent(int id, String email){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO NOTATTENDINGEVENT VALUES('"+id+"', '"+email+"')");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }


    public boolean schoolWorkFeedBack(int id, String email, int hoursUsed){
        try{
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO FEEDBACK VALUES('"+id+"', '"+email+"', '"+hoursUsed+"')");
            return true;
        }catch (SQLException se){
            se.printStackTrace();
            return false;
        }
    }

    public String getLastAddedDes(){
        String outData = "";
        try{
            ResultSet data = stmt.executeQuery("SELECT * FROM EVENT ORDER BY eventID DESC LIMIT 1");
            while(data.next()){
                outData = data.getString(9);
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return outData;
    }
}
