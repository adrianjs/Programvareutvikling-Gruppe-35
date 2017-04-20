package layout;

import calendar.Cell;
import javafx.scene.control.Button;

/**
 * Created by torresrl on 03/04/2017.
 */
public class monthEventButton {

    Cell cell;
    Button btn;
    public monthEventButton(Cell cell){
        this.cell = cell;
        btn = new Button();
        btn.setText(cell.getName());


    }

    public Button getEvent(){
        return btn;
    }
}
