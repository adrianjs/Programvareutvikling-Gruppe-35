package algorithm;

/**
 * Created by Henning on 15.03.2017.
 * A Subject contains information about a subject at NTNU.
 * The fields include subject code, type of evaluation, a description and the coordinator's email.
 */
public class Subject {

    private String subjectCode;
    private String evaluation;
    private String description;
    private String coordinatorEmail;

    /**
     * Constructs a new Subject from data from DB.
     * @param subjectCode
     * @param evaluation
     * @param description
     * @param coordinatorEmail
     */
    public Subject(String subjectCode, String evaluation, String description, String coordinatorEmail) {
        this.subjectCode = subjectCode;
        this.evaluation = evaluation;
        this.description = description;
        this.coordinatorEmail = coordinatorEmail;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public String getDescription() {
        return description;
    }

    public String getCoordinatorEmail() {
        return coordinatorEmail;
    }
}
