package database;

import layout.User;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Created by Henning on 02.03.2017.
 */
public class Fetcher extends Connect{
    private String query;
    private Set<List> results = new HashSet<>();

    public Fetcher(String query) throws SQLException{
        this.query = query;
    }

    public Set<List> getUserRelatedResults(int numberOfColumns) throws SQLException{
        ResultSet m_ResultSet = stmt.executeQuery(query);
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