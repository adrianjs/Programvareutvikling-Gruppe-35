package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 15.03.2017.
 */
public class Event extends Cell {
    private String name;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
    private int repeating;
    private int priority;
    private String description;
    private int hoursOfWork;
    private String subjectCode;

    public Event(String name, Date startDate, Date endDate,
                 String startTime, String endTime, int repeating,
                 int priority, String description, int hoursOfWork,
                 String subjectCode) {
        super(startDate, endDate, name, description, priority, (repeating!=0));
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeating = repeating;
        this.priority = priority;
        this.description = description;
        this.hoursOfWork = hoursOfWork;
        this.subjectCode = subjectCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRepeating() {
        return repeating;
    }

    public void setRepeating(int repeating) {
        this.repeating = repeating;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
