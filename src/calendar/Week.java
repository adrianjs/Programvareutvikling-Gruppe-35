package calendar;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Henning on 16.02.2017.
 * This class will hold 7 "Day"-objects to make up a full week
 * TODO: Compose a week of 7 Day-objects
 */
public class Week {
    private int weekInMonth;
    private int monthOfYear;
    private Set<Day> daysOfWeek;

    public Week(int weekInMonth, int monthOfYear, Set<Day> daysOfWeek) {
        this.weekInMonth = weekInMonth;
        this.monthOfYear = monthOfYear;
        this.daysOfWeek = new LinkedHashSet<>(daysOfWeek);
    }

    public int getWeekInMonth() {
        return weekInMonth;
    }

    public void setWeekInMonth(int weekInMonth) {
        this.weekInMonth = weekInMonth;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public Set<Day> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(Set<Day> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}
