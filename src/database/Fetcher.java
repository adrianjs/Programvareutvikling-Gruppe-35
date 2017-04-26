package database;

import layout.User;

import java.sql.*;
import java.util.*;

/**
 * Created by Henning on 02.03.2017.
 * More general databse class, you can write in more spesific commands
 */
public class Fetcher{
    private Connect connect = Connect.getInstance();
    private String query;
    private Set<List> results = new HashSet<>();

    /**
     * write in SQL code
     * @param query
     */

    public Fetcher(String query){
        this.query = query;
    }


    public Set<List> getUserRelatedResults(int numberOfColumns) throws SQLException{
        ResultSet m_ResultSet = connect.stmt.executeQuery(query);
        //This works as long as username / email lies at column 8 in the DB
        while(m_ResultSet.next()){
            if(m_ResultSet.getString(8).equals(User.getInstance().getUsername())) {
                //This works as of 02.03.17 due to ACTIVITY having 9 columns.
                List activity = new ArrayList();
                for(int i=1;i<numberOfColumns;i++){
                    activity.add(m_ResultSet.getString(i));
                }
                results.add(activity);
            }
        }
        return results;
    }

}