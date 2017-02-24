package calendar;

import java.util.Date;

/**
 * Created by Henning on 24.02.2017.
 * This Class contains to Date objects, start and end, to represent a Time Interval
 */
public class TimeInterval{
    private Date startTime;
    private Date endTime;

    public TimeInterval(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
