package layout;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class CreatUserTest {

    CreateUser cu;

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

         cu.firsty  = new JFXRadioButton();
         cu.secondy = new JFXRadioButton();
         cu.thirdy  = new JFXRadioButton();
         cu.fourthy = new JFXRadioButton();
         cu.fifthy  = new JFXRadioButton();

         cu.fieldStud  = new Group();
         cu.fieldTeach = new Group();
         cu.yearGroup = new Group();

         cu.stud  = new RadioButton();
         cu.teach = new RadioButton();
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










}
