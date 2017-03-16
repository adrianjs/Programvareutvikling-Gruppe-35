package algorithm;

import calendar.Cell;

import java.util.Comparator;

/**
 * Created by Henning on 16.03.2017.
 */
public class PriorityComparator implements Comparator<Cell>{
    @Override
    public int compare(Cell o1, Cell o2) {
        //DESCENDING ORDER
        return Integer.compare(o2.getSlotPriority(), o1.getSlotPriority());
    }
}
