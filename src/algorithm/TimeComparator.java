package algorithm;

import calendar.Cell;

import java.util.Date;

/**
 * Created by Henning on 22.03.2017.
 * The TimeComparator compares the two Cells and tells you if they overlap.
 */
public class TimeComparator{
    //Pass in the already placed cell first.
    public boolean compare(Cell o1, Cell o2) {
        boolean statement = isOverLap(o1.getStartDate(), o1.getEndDate(), o2.getStartDate(), o2.getEndDate());
        return statement; //When this is true, don't add cell
    }

    /**
     * Compares two java.util.Date objects and checks to see if they overlap in any way.
     * Returns true if they overlap.
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return boolean
     * @throws NullPointerException
     */
    public boolean isOverLap(Date start1, Date end1, Date start2, Date end2) throws NullPointerException{
        if ((start1.before(start2) && end1.after(start2)) ||
                (start1.before(end2) && end1.after(end2)) ||
                (start1.before(start2) && end1.after(end2)) ||
                (start1.equals(start2) && end1.equals(end2)) ||
                (start1.equals(start2) && end1.before(end2)) ||
                (start1.after(start2) && end1.equals(end2)) ||
                (start1.before(start2) && end1.after(end2)) ||
                (start2.before(start1) && end2.after(start1))
                )  {
            return true;
        } else {
            return false;
        }
    }
}
