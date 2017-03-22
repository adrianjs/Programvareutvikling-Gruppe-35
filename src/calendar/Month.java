package calendar;


import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Henning on 16.02.2017.
 * This class will hold 4 "Week"-objects to make up a full month
 * TODO: Make this class hold 4 weeks and handle them in ways necesary
 */
public class Month {
    private int monthOfYear;  //0 indexed
    private Set<Week> weeksInMonth;

    public Month(int monthOfYear, Set<Week> weeksInMonth) {
        this.monthOfYear = monthOfYear;
        this.weeksInMonth = new LinkedHashSet<>(weeksInMonth);
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public Set<Week> getWeeksInMonth() {
        return weeksInMonth;
    }

    public void setWeeksInMonth(Set<Week> weeksInMonth) {
        this.weeksInMonth = weeksInMonth;
    }

    public void addWeekToWeeksInMonth(Week week){
        this.weeksInMonth.add(week);
    }
}
