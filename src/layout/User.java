package layout;

import database.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by larsmade on 21.02.2017.
 */

public class User{

    private Connect connect = Connect.getInstance();
    private String username;
    private String password;
    private Set<String> subjects;

    private static User instance = null;
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void updateSubjects() throws SQLException {
        ResultSet stud_sub_resultset = connect.stmt.executeQuery("SELECT * FROM STUDTAKESUB WHERE studentEmail='"+ username +"'");
        subjects = new LinkedHashSet<>();
        while(stud_sub_resultset.next()){
            subjects.add(stud_sub_resultset.getString(1));
        }
    }

    public Set<String> getSubjects() {
        return subjects;
    }
}
