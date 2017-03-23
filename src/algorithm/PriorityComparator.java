package algorithm;

import calendar.Cell;

import java.util.Comparator;

/**
 * Created by Henning on 16.03.2017.
 * Comparator for comparing two priorities to figure out which one should go before the other.
 *
 */
public class PriorityComparator implements Comparator<Cell>{
    /**
     * Takes two cells, and compares their slotPriority.
     * These Cells can be both Events and Activities.
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Cell o1, Cell o2) {
        //DESCENDING ORDER
        return Integer.compare(o2.getSlotPriority(), o1.getSlotPriority());
    }
}
