package calendar;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * Class for handling a single cell/timeslot in the calendar
 * It contains information about the timeslot including:
 *  - Start/end times
 *  - Slot priority
 *  - Slot name
 *  - Slot description
 */
public class Cell {

    private Date startDate = new Date(); //Also used to set start time
    private Date endDate = new Date();//Also used to set end time
    private String startTime;
    private String endTime;
    private String name;
    private String description;
    private int slotPriority;
    private boolean repeating;
    private String type;

    /**
     * Construct a single Cell. This super class will never be instantiated, but it can be.
     * You should use Event or Activity.
     * @param startDate
     * @param endDate
     * @param name
     * @param description
     * @param slotPriority
     * @param repeating
     */
    public Cell(Date startDate, Date endDate, String startTime, String endTime, String name, String description, int slotPriority, boolean repeating) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
        this.slotPriority = slotPriority;
        this.repeating = repeating;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSlotPriority() {
        return slotPriority;
    }

    public void setSlotPriority(int slotPriority) {
        this.slotPriority = slotPriority;
    }

    public boolean isRepeating() {
        return repeating;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
