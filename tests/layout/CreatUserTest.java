package layout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controllers.JavaFXThreadingRule;
import database.Connect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class CreatUserTest {

    CreateUser cu;
    Connect connect;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    
    @Before
    public void setup(){
         new JFXPanel();
         cu = new CreateUser();

         cu.email        = new JFXTextField();
         cu.firstName    = new JFXTextField();
         cu.lastName     = new JFXTextField();
         cu.password = new JFXPasswordField();
         cu.study        = new JFXTextField();
         cu.department   = new JFXTextField();
         cu.description = new JFXTextArea();

         cu.firsty  = new JFXRadioButton();
         cu.secondy = new JFXRadioButton();
         cu.thirdy  = new JFXRadioButton();
         cu.fourthy = new JFXRadioButton();
         cu.fifthy  = new JFXRadioButton();

         cu.fieldStud  = new Group();
         cu.fieldTeach = new Group();
         cu.yearGroup = new Group();

         cu.submit = new Button();
         cu.cancel = new Button();


         cu.stud  = new RadioButton();
         cu.teach = new RadioButton();
         cu.initialize();
         connect = new Connect();


        cu.email      .setText("torres.lande@gmail.com");
        cu.firstName  .setText("test");
        cu.lastName   .setText("test");
        cu.password   .setText("test");
        cu.study      .setText("test");
        cu.department .setText("test");



        /*cu.description.setVisible(false);
        cu.fieldStud.setVisible(false);
        cu.fieldTeach.setVisible(false);
        cu.yearGroup.setVisible(false);  */



    }
    
    @Test
    public void testGetSuccess(){
        assertEquals(false, cu.getSuccess());
    }

    @Test
    public void testValidateEmailFormat(){
        assertEquals(false, cu.validateEmailFormat("hei"));
        assertEquals(true, cu.validateEmailFormat("torres.lande@gmail.com"));
    }

    @Test
    public void testGetSchoolYear(){
        cu.firsty.setSelected(true);
        assertEquals(1, cu.getSchoolYear());
        cu.firsty.setSelected(false);
        cu.secondy.setSelected(true);
        assertEquals(2, cu.getSchoolYear());
        cu.secondy.setSelected(false);
        cu.thirdy.setSelected(true);
        assertEquals(3, cu.getSchoolYear());
        cu.thirdy.setSelected(false);
        cu.fourthy.setSelected(true);
        assertEquals(4, cu.getSchoolYear());
        cu.fourthy.setSelected(false);
        cu.fifthy.setSelected(true);
        assertEquals(5, cu.getSchoolYear());
        cu.fifthy.setSelected(false);
        assertEquals(0, cu.getSchoolYear());
    }


    @Test
    public void testIsInputValid1(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                assertEquals(false, cu.isInputValid());

            }
           });


    }

    @Test
    public void testIsInputValid2() {

        cu.email      .setText("torres.lande@gmail.com");
        cu.firstName  .setText("jnejknfjneofnrejnfklernjgrnerjgknerngkjngkjmekjgnrjkgntjkgnrtjkgnkjrtengkjtrngkjrtngjkrntjkngrtkn");
        cu.lastName   .setText("jnejknfjneofnrejnfklernjgrnerjgknerngkjngkjmekjgnrjkgntjkgnrtjkgnkjrtengkjtrngkjrtngjkrntjkngrtkn");
        cu.password   .setText("jnejknfjneofnrejnfklernjgrnerjgknerngkjngkjmekjgnrjkgntjkgnrtjkgnkjrtengkjtrngkjrtngjkrntjkngrtkn");
        cu.study      .setText("jnejknfjneofnrejnfklernjgrnerjgknerngkjngkjmekjgnrjkgntjkgnrtjkgnkjrtengkjtrngkjrtngjkrntjkngrtkn");
        cu.department .setText("jnejknfjneofnrejnfklernjgrnerjgknerngkjngkjmekjgnrjkgntjkgnrtjkgnkjrtengkjtrngkjrtngjkrntjkngrtkn");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                assertEquals(false, cu.isInputValid());
            }
        });


    }

    @Test
    public void testIsInputValid3() {

        cu.email      .setText("torres.lande@gmail.com");
        cu.firstName  .setText("test");
        cu.lastName   .setText("test");
        cu.password   .setText("test");
        cu.study      .setText("test");
        cu.department .setText("test");


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                assertEquals(true, cu.isInputValid());
            }
        });


    }


    @Test
    public void testStudOnAction(){
        cu.stud.fire();
        assertEquals(true, cu.fieldStud.isVisible());
        assertEquals(true, cu.yearGroup.isVisible());
        assertEquals(false, cu.fieldTeach.isVisible());
        assertEquals(false, cu.description.isVisible());
    }

    @Test
    public void testTeacheOnAction(){
        cu.teach.fire();
        assertEquals(false, cu.fieldStud.isVisible());
        assertEquals(false, cu.yearGroup.isVisible());
        assertEquals(true, cu.fieldTeach.isVisible());
        assertEquals(true, cu.description.isVisible());

    }


    @Test
    public void testSubmitOnAction() throws SQLException{
        cu.stud.setSelected(true);
        cu.teach.setSelected(false);
        cu.email.setText("testStud@test.com");
        cu.submit.fire();
        ResultSet m_result_set = connect.stmt.executeQuery("SELECT * FROM STUDENT WHERE email='testStud@test.com'");
        m_result_set.next();
        assertEquals("testStud@test.com", m_result_set.getString(1));
        connect.stmt.execute("DELETE FROM STUDENT WHERE email='testStud@test.com'");

        cu.stud.setSelected(false);
        cu.teach.setSelected(true);
        cu.email.setText("testTeach@test.com");
        cu.submit.fire();

        m_result_set = connect.stmt.executeQuery("SELECT * FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");
        m_result_set.next();
        assertEquals("testTeach@test.com", m_result_set.getString(1));

        connect.stmt.execute("DELETE FROM COURSECOORDINATOR WHERE email='testTeach@test.com'");


    }

    @After
    public void close(){
        connect.close();
    }










}
