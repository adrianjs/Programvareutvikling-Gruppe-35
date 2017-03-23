package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * This is a subclass of Cell which only contains cells made by users manually.
 *
 */
public class Activity extends Cell {

    private int startTime;
    private int endTime;

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
    public Activity(Date startDate, Date endDate, String name, String description, int slotPriority, boolean repeating) {
        super(startDate, endDate, name, description, slotPriority, repeating);
    }

    public Activity(Date startDate, Date endDate, String name, String description, int slotPriority, boolean repeating,
                    int startTime, int endTime) {
        super(startDate, endDate, name, description, slotPriority, repeating);

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the int startTime
     * @return int startTime
     */
    public int getStartTime(){
        return startTime;
    }

    /**
     * Returns the int endTime
     * @return int endTime
     */
    public int getEndTime(){
        return endTime;
    }
}
