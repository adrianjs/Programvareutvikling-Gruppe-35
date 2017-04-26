package layout;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by larsmade on 11.04.2017.
 * Feedbackcell to put in tableView.
 */
public class FeedbackElement {

    private final SimpleStringProperty date;
    private final SimpleStringProperty estimate;
    private final SimpleStringProperty avg;
    private final SimpleStringProperty description;

    /**
     * Making a feedcak element cell.
     * @param date date
     * @param estimate teacher estimate
     * @param avg average of students feedback.
     * @param description description of event.
     */
    public FeedbackElement(String date, String estimate, String avg, String description){
        this.date = new SimpleStringProperty(date);
        this.estimate = new SimpleStringProperty(estimate);
        this.avg = new SimpleStringProperty(avg);
        this.description = new SimpleStringProperty(description);
    }


    //Must have getters and setters, tableview uses the getters to get information on a feed

    /**
     * Sets the avg.
     * @param avg average estimate.
     */
    public void setAvg(String avg) {
        this.avg.set(avg);
    }

    /**
     * Sets the date
     * @param date date
     */
    public void setDate(String date) {
        this.date.set(date);
    }

    /**
     * Sets the description
     * @param description description
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    /**
     * Sets the estimate
     * @param estimate estimate
     */
    public void setEstimate(String estimate) {
        this.estimate.set(estimate);
    }

    /**
     * Get average
     * @return average
     */
    public String getAvg() {
        return avg.get();
    }

    /**
     * Get date
     * @return date
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Get description
     * @return description
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Get estimate
     * @return estimate.
     */
    public String getEstimate() {
        return estimate.get();
    }
}
