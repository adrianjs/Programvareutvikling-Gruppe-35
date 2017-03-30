package database;

import java.sql.SQLException;

/**
 * Created by torresrl on 30/03/2017.
 */
public class Activity extends Connect {

    public Activity(){
        super();
    }

    public void delteActivity(int id){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM ACTIVITY WHERE activityID = '"+id+"'");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
