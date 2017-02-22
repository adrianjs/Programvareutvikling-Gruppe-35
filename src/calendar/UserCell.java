package calendar;

import java.util.Date;

/**
 * Created by Henning on 16.02.2017.
 * This is a subclass of Cell which only contains cells made by users manually.
 *
 */
public class UserCell extends Cell {
    public UserCell(Date startDate, Date endDate, String name, String description, int slotPriority) {
        super(startDate, endDate, name, description, slotPriority);
    }
}
