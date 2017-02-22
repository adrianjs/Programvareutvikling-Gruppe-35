package database;

import java.sql.SQLException;

/**
 * Created by Henning on 22.02.2017.
 */
public class Run {
    public static void main(String[] args) {
        Login login = null;
        try {
            login = new Login();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            login.getStudent();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
