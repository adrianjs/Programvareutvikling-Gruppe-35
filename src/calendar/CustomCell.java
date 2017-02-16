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
public class CustomCell {
    private Date startDate = new Date(); //Also used to set start time
    private Date endDate = new Date();//Also used to set end time
    private String name;
    private String description;
    private int slotPriority;

    public CustomCell(Date startDate, Date endDate, String name, String description, int slotPriority) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.slotPriority = slotPriority;
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
}
