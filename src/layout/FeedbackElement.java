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


    public FeedbackElement(String date, String estimate, String avg, String description){
        this.date = new SimpleStringProperty(date);
        this.estimate = new SimpleStringProperty(estimate);
        this.avg = new SimpleStringProperty(avg);
        this.description = new SimpleStringProperty(description);
    }


    //Must have getters and setters, tableview uses the getters to get information on a feed
    public void setAvg(String avg) {
        this.avg.set(avg);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setEstimate(String estimate) {
        this.estimate.set(estimate);
    }

    public String getAvg() {
        return avg.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getEstimate() {
        return estimate.get();
    }
}
