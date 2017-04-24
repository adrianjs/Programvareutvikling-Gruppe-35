package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by torresrl on 30/03/2017.
 */
public class Activity{
    private Connect connect = Connect.getInstance();

    public Activity(){
        super();
    }

    public void deleteActivity(int id){
        try {
            connect.stmt = connect.conn.createStatement();
            connect.stmt.executeUpdate("DELETE FROM ACTIVITY WHERE activityID = '"+id+"'");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public ArrayList getLastActivity(){
        ArrayList outData = new ArrayList();
        try{
            ResultSet data = connect.stmt.executeQuery("SELECT * FROM ACTIVITY ORDER BY activityID DESC LIMIT 1");
            while(data.next()){
                outData.add(data.getInt(1));
                outData.add(data.getString(2));
                outData.add(data.getDate(3));
                outData.add(data.getInt(4));
                outData.add(data.getDouble(5));
                outData.add(data.getDouble(6));
                outData.add(data.getInt(7));
                outData.add(data.getString(8));
                outData.add(data.getString(9));
                outData.add(data.getString(10));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return outData;
    }

}


