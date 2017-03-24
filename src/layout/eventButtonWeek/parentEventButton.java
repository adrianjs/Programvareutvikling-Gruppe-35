package layout.eventButtonWeek;

import calendar.Cell;
import javafx.scene.control.Button;

/**
 * Created by torresrl on 22/03/2017.
 */
public class parentEventButton {

    private Button event;
    private Cell cell;

    public parentEventButton( Cell cell){
        this.cell = cell;
        event = new Button();
        event.setId("eventButton");
        event.wrapTextProperty().setValue(true);
        event.setText(cell.getType() +"\n" + cell.getName() + "\n\n" + cell.getDescription());
        event.setMaxHeight(Double.MAX_VALUE);
        event.setMaxWidth(Double.MAX_VALUE);
    }

    //returns the button that wil be shown in the calender
    public Button getEvent(){
        return event;
    }

    public Cell getCell() {
        return cell;
    }
}
