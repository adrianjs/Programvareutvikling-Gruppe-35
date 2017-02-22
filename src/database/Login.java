package database;

import java.sql.*;

/**
 * Created by Henning on 22.02.2017.
 */
public class Login {
    private Connect connecter;
    private String query = "SELECT * FROM STUDENT";

    public Login() throws SQLException {
        connecter = new Connect();
    }

    public void getStudent() throws SQLException {
        ResultSet m_ResultSet = connecter.stmt.executeQuery(query);
        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", "
                    + m_ResultSet.getString(3));

        }
    }

}
