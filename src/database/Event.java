package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by torresrl on 30/03/2017.
 */
public class Event{
    private Connect connect = Connect.getInstance();

    public Event() {
        super();
    }

    /**
     * Student can drop a school event
     * @param id
     * Event ID
     * @param email
     * Student Email
     */
    public void deleteEvent(int id, String email){
        try {
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO NOTATTENDINGEVENT VALUES('"+id+"', '"+email+"')");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    /**
     * Students can give feedback to teacher
     * @param id
     * Event ID
     * @param email
     * Student Emial
     * @param hoursUsed
     * How many houers student Used on event
     * @return
     */
    public boolean schoolWorkFeedBack(int id, String email, int hoursUsed){
        try{
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("INSERT INTO FEEDBACK VALUES('"+id+"', '"+email+"', '"+hoursUsed+"')");
            return true;
        }catch (SQLException se){
            se.printStackTrace();
            return false;
        }
    }

    /**
     * get last added event
     * @return
     */

    public String getLastAddedDes(){
        String outData = "";
        try{
            ResultSet data = connect.stmt.executeQuery("SELECT * FROM EVENT ORDER BY eventID DESC LIMIT 1");
            while(data.next()){
                outData = data.getString(9);
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return outData;
    }
}
