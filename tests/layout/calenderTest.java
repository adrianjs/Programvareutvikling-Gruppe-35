package layout;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by torresrl on 20/04/2017.
 */
public class calenderTest {



    Calendar cal;

    @Before
    public void setup() {
        new JFXPanel();
        cal = new Calendar();
        //cal.stage = new Stage();
        cal.loader = new FXMLLoader();
        cal.load = new AnchorPane();
    }

    @Test
    public void testLoading() throws IOException {

        cal.loading("/resources/fxml/feedback.fxml");
        assertEquals(new FXMLLoader(getClass().getResource("/resources/fxml/feedback.fxml")).getClass(), cal.loader.getClass());



    }

/*
    @Test
    public void testSetStage(){
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

            }
        });
    }*/
}
