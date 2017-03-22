package algorithm;

import calendar.Cell;


import java.util.Comparator;
import java.util.Date;

/**
 * Created by Henning on 22.03.2017.
 */
public class TimeComparator{
    //Pass in the already placed cell first.
    public boolean compare(Cell o1, Cell o2) {
        System.out.println("o1: " + o1.getName() + "   o2: " + o2.getName());
        System.out.println("o1 STARTDATE FØR o2 STARTDATE: " + o1.getStartDate().before(o2.getStartDate()));
        System.out.println("o1 ENDDATE ETTER o2 STARTDATE: " + o1.getEndDate().after(o2.getStartDate()));
        System.out.println("o2 STARTDATE FØR o1 STARTDATE: " + o2.getStartDate().before(o1.getStartDate()));
        System.out.println("o2 ENDDATE ETTER o2 STARTDATE: " + o2.getEndDate().after(o1.getStartDate()));


        //TODO: FIND THE RIGHT IF STATEMENT THAT COVERS ALL!
        boolean statement = ((o1.getStartDate().before(o2.getStartDate()) || o1.getStartDate().equals(o2.getStartDate()))
                && (o1.getEndDate().after(o2.getStartDate())))
                || (o2.getStartDate().before(o1.getStartDate())
                && (o2.getEndDate().after(o1.getStartDate()))) ;
        System.out.println("ENDELIG STATEMENT: " + statement);
        return statement; //When this is true, don't add cell
    }

    boolean isOverLaped(Date start1, Date end1, Date start2, Date end2) throws NullPointerException{
        if ((start1.before(start2) && end1.after(start2)) ||
                (start1.before(end2) && end1.after(end2)) ||
                (start1.before(start2) && end1.after(end2))) {
            return true;
        } else {
            return false;
        }
    }
}
