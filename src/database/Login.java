package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Henning on 22.02.2017.
 */
public class Login {
    private Connect connecter;
    private String query = "SELECT * FROM STUDENT";
    private Set<List> users = new HashSet<>();

    public Login() throws SQLException {
        connecter = new Connect();
    }

    public void getStudent() throws SQLException {
        ResultSet m_ResultSet = connecter.stmt.executeQuery(query);
        while (m_ResultSet.next()) {
            List user = new ArrayList<>();
            user.add(m_ResultSet.getString(1));
            user.add(m_ResultSet.getString(6));
            user.add(m_ResultSet.getString(2));
            user.add(m_ResultSet.getString(3));
            users.add(user);
        }
        System.out.println(users);
    }

}
