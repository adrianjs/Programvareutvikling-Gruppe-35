package calendar;

import java.util.*;

/**
 * Created by Henning on 16.02.2017.
 * This class will hold all "Cell"-objects which have timestamps within a single day.
 * TODO: Collect all Cells that spans within a single day
 */
public class Day {
    private Date date;
    private int monthOfYear;
    private int dayOfWeek;
    private Set<Cell> timeSlots;

    /**
     * Constructor for a Day objects which holds all the Cells with the same date.
     * //TODO: Might not be used
     * @param date
     * @param monthOfYear
     * @param dayOfWeek
     * @param timeSlots
     */
    public Day(Date date, int monthOfYear, int dayOfWeek, Set<Cell> timeSlots) {
        this.date = date;
        this.monthOfYear = monthOfYear;
        this.dayOfWeek = dayOfWeek;
        this.timeSlots = new LinkedHashSet<>(timeSlots);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Set<Cell> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<Cell> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
