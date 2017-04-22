package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * This is a subclass of Cell which only contains cells made by users manually.
 *
 */
public class Activity extends Cell {

    /**
     * Constructs a new Activity which is a subclass of Cell.
     * It is the same as Cell, but it has two integers startTime and endTime too.
     *
     * @param startDate
     * @param endDate
     * @param name
     * @param description
     * @param slotPriority
     * @param repeating
     */
    public Activity(Date startDate, Date endDate, String startTime, String endTime, String name, String description, int slotPriority, boolean repeating, int ID, String color) {
        super(startDate, endDate, startTime, endTime, name, description, slotPriority, repeating, ID, color);
        super.setType("activity");
    }

    public Activity(Activity activity){
        super(activity);
    }

}
