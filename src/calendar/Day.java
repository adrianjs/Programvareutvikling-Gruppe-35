package calendar;

import java.util.*;

/**
 * Created by Henning on 16.02.2017.
 * This class will hold all "Cell"-objects which have timestamps within a single day.
 * TODO: Collect all Cells that spans within a single day
 */
public class Day {
    private Date date = new Date();
    private List<Cell> timeSlots = new ArrayList<>();

    public Day(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public List<Cell> getTimeSlots() {
        return timeSlots;
    }

    public void addCell(Cell cell){
        timeSlots.add(cell);
        //TODO: Sorter denne listen
    }
}
