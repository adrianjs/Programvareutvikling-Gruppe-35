package database;

import java.sql.SQLException;

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
            stmt.executeUpdate("INSERT INTO NOTATTENDINGEVENT VALUES('"+id+"', '"+email+"'");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
