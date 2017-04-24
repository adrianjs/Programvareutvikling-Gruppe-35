package controllers.add;


import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by torresrl on 19/04/2017.
 */


public class addTeachingSubjectControllerTest {

    private addTeachingSubjectController con;

    @Before
    public void setup(){
        new JFXPanel();
        con = new addTeachingSubjectController();

        con.subjectCode = new JFXTextField();
        con.evaluation = new JFXTextField();
        con.desc       = new JFXTextField();
        con.errorLabel = new Label();

    }
    
    @Test
    public void testCheckSubjectCode(){

        assertEquals(false, con.checkSubjectCode() );
        con.subjectCode.setText("AAR4335");
        assertEquals(true, con.checkSubjectCode() );
    }

    @Test
    public void testCheckEvaluation(){

        assertEquals(false, con.checkEvaluation() );
        con.evaluation.setText("examen");
        assertEquals(true, con.checkEvaluation() );
    }

    @Test
    public void testCheckDescription(){

        assertEquals(false, con.checkDescription() );
        con.desc.setText("Dette er en test");
        assertEquals(true, con.checkDescription() );
    }





}
