package layout.eventButtonWeek;

import javafx.scene.control.Button;

/**
 * Created by torresrl on 22/03/2017.
 */
public class parentEventButton {

    String description;
    String name;
    Button event;
    public parentEventButton(String name, String description){
        this.name = name;
        this.description = description;
        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText("Activity\n" +name+ "\n\nDeskription\n" + description);
        event.setMaxHeight(Double.MAX_VALUE);
        event.setMaxWidth(Double.MAX_VALUE);



    }

    //returns the button that wil be shown in the calender
    public Button getEvent(){
        return event;
    }

    public String getName() {return name;}

    public String getDescription() {return description;}












}
