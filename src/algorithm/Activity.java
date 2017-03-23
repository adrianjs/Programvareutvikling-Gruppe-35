package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * This is a subclass of Cell which only contains cells made by users manually.
 *
 */
public class Activity extends Cell {

    int startTime;
    int endTime;

    public Activity(Date startDate, Date endDate, String name, String description, int slotPriority, boolean repeating) {
        super(startDate, endDate, name, description, slotPriority, repeating);
    }

    public Activity(Date startDate, Date endDate, String name, String description, int slotPriority, boolean repeating,
                    int startTime, int endTime) {
        super(startDate, endDate, name, description, slotPriority, repeating);

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime(){
        return startTime;
    }

    public int getEndTime(){
        return endTime;
    }
}
