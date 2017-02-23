package database;
import java.sql.*;



/**
 * Created by torresrl on 21/02/2017.
 *
 *
 * -------------- LES FØR DU LEGGER NOE TIL---------------------
 *
 *
 * Denne klassen er for å koble seg til databasen og hente ut data
 *
 * Metodene kan være laget for en spesiel ting så skriv hvem som laget den og hvor den skal
 * brukes i koden (lokasjon og hva den henter ut)
 *
 * ikke lag noen spesiele metoder over linjen metoder, for her har vi de mest standariserte metodene
 * som å koble seg til, stenge tilkoblingen, legge til og hente ut for hver enkel tabll.
 * Hvis du skal lage flere spesialiserte metoder fåreslår jeg og lage en subklasse.
 *
 */


public class Connect {

    //login info
    static final String URL = "jdbc:mysql://mysql.stud.ntnu.no:3306/torresrl_eduorg";
    static final String user = "torresrl_data";
    static final  String pass = "admin";
    
    

    Connection conn = null;
    Statement stmt = null;

    //når du oppreter klassen kobler den seg automatisk opp.
    public Connect(){



        try {
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, user, pass); 
            stmt = conn.createStatement();

        } catch (SQLException se){
            se.printStackTrace();

        } catch (Exception e){
            e.printStackTrace();
        }

        if(conn != null){
            System.out.println("Connected to database");
        }
    }


    // stenger forbinelsen etter bruk
    public void close(){
        try {
            if (stmt != null) {
                stmt.close();
                System.out.println("statment closed");
            }

            if (conn != null){
                conn.close();
                System.out.println("connection closed");
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    
   public static void main(String[] args) {
   	Connect test = new Connect();


   	test.close();
   	}

    public void addStudent(String email, String firstName, String lastName, String field, int year, String pass){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO STUDENT VALUES('"+email+"','"+firstName+"', '"+lastName+"', '"+field+"'" +
                    ", '"+year+"', '"+pass+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }

    }

    public void addTeacher(String email, String firstName, String lastName, String department, String description, String pass){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO COURSECOORDINATOR VALUES('"+email+"','"+firstName+"', '"+lastName+"', '"+department+"'" +
                    ", '"+pass+"', '"+description+"')");
        } catch (SQLException se){
            se.printStackTrace();
        }

    }




    //----------------------------------------------------METODER----------------------------------------------------





} //slutt på Connect klassen
