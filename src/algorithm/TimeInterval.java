package algorithm;

import java.util.Date;

/**
 * Created by Henning on 24.02.2017.
 * This Class contains to Date objects, start and end, to represent a Time Interval
 */
public class TimeInterval{
    private Date startTime;
    private Date endTime;

    /**
     * Construct a new time interval.
     *
     * @param startTime
     * @param endTime
     */
    public TimeInterval(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

}
