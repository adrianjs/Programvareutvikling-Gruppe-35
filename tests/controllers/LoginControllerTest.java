package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class LoginControllerTest {

    LoginController con;

    @Before
    public void setup(){
        new JFXPanel();
        con = new LoginController();

        con.loginField     = new JFXTextField();
        con.passwordField  = new JFXPasswordField();
        con.openCalendar   = new JFXButton();
        con.loginError     = new Label();
        con.snackBarPane   = new AnchorPane();
    }


    @Test
    public void testGetUser(){
        assertEquals(con.user, con.getUser());
    }

    @Test
    public void testShowNewUserSnackbar(){
        con.showNewUserSnackbar();
        assertEquals("forTesting", con.jfxSnackbar.getId());
    }

    @Test
    public void testValidateLogin(){
        con.user.setUsername("bull");
        con.user.setPassword("123");
        assertEquals(null,con.validateLogin());
        con.loginField.setText("larsmade@stud.ntnu.no");
        con.passwordField.setText("123");
        con.user.setUsername("larsmade@stud.ntnu.no");
        con.user.setPassword("123");
        assertEquals("STUDENT",con.validateLogin());
    }

    @Test
    public void testLogin() throws IOException, SQLException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    con.loginField.setText("larsmade@stud.ntnu.no");
                    con.passwordField.setText("123");
                    con.stage = new Stage();
                    con.login();
                    assertEquals("student", con.test);
                    con.loginField.setText("larsmade@stud.ntnu.no");
                    con.passwordField.setText("12345");
                    con.login();
                    assertEquals("Wrong username or password", con.loginError.getText());
                    con.loginField.setText("a.helvoort@ntnu.no");
                    con.passwordField.setText("123");
                    con.login();
                    assertEquals("teacher", con.test);


                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });



    }







}
