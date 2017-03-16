package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * This is a subclass of Cell which only contains cells made by users manually.
 *
 */
public class Activity extends Cell {

    public Activity(Date startDate, Date endDate, String name, String description, int slotPriority, boolean repeating) {
        super(startDate, endDate, name, description, slotPriority, repeating);
    }
}
