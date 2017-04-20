package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Henning on 22.02.2017.
 */
public class Login extends Connect{
    private String query = "SELECT * FROM STUDENT";
    private String query1 = "SELECT * FROM COURSECOORDINATOR";
    private Set<List> users = new HashSet<>();
    private Set<List> coordinators = new HashSet<>();

    public Login() throws SQLException {
    }

    /**
     * Get all users and add it to list
     * @return list with users
     * @throws SQLException
     */
    public Set getStudent() throws SQLException {
        ResultSet m_ResultSet = stmt.executeQuery(query);
        while (m_ResultSet.next()) {
            List user = new ArrayList<>();
            user.add(m_ResultSet.getString(1));
            user.add(m_ResultSet.getString(6));
            user.add(m_ResultSet.getString(2));
            user.add(m_ResultSet.getString(3));
            users.add(user);
        }
        System.out.println("getStudent: done");
        return users;
    }

    /**
     * Get all course coordinators and add it to list.
     * @return list with coursecoordinators.
     * @throws SQLException
     */
    public Set getCourseCoordinator() throws SQLException {
        ResultSet set = stmt.executeQuery(query1);
        while (set.next()){
            List user = new ArrayList<>();
            user.add(set.getString(1));
            user.add(set.getString(5));
            coordinators.add(user);
        }
        return coordinators;
    }
}
