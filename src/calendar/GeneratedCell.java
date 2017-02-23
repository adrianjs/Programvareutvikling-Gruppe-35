package calendar;

import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * This class will be used to hold generated cells from time estimates given by the teachers.
 * It will recieve it's input from the teachers through an unknown input source.
 * TODO: Figure out how to get time estimate input from teacher
 * TODO: Set up useful fields and methods
 */
public class GeneratedCell extends Cell {

    public GeneratedCell(Date startDate, Date endDate, String name, String description, int slotPriority, boolean repeating) {
        super(startDate, endDate, name, description, slotPriority, repeating);
    }
}
