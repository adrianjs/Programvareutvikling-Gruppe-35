package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 15.03.2017.
 * A subclass of Cell-object which contains some additional information.
 */
public class Event extends Cell {

    private int hoursOfWork;
    private String subjectCode;

    /**
     * Constructs a new Event, which is something teachers creates.
     * An Event can be something like a delivery, quiz or exam, and is tied up to a Subject.
     * @param name
     * @param startDate
     * @param endDate
     * @param startTime
     * @param endTime
     * @param repeating
     * @param slotPriority
     * @param description
     * @param hoursOfWork
     * @param subjectCode
     */
    public Event(Date startDate, Date endDate, String startTime, String endTime, String name, String description, int slotPriority, boolean repeating, int hoursOfWork, String subjectCode) {
        super(startDate, endDate, startTime, endTime, name, description, slotPriority, repeating);
        this.hoursOfWork = hoursOfWork;
        this.subjectCode = subjectCode;
        super.setType("event");
    }

    public int getHoursOfWork() {
        return hoursOfWork;
    }

    public void setHoursOfWork(int hoursOfWork) {
        this.hoursOfWork = hoursOfWork;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
}
