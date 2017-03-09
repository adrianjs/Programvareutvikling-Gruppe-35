package algorithm;

import calendar.Cell;
import database.Connect;
import layout.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Henning on 16.02.2017.
 * Lecture = 96
 * SchoolWork = 97
 * deadline = 98
 * exam = 99
 * hjemme eksamen = 95
 *
 *
 * TODO: This call will be the all mighty sorting algorithm for the calendar
 */
public class SuperSorter extends Connect {
    private User user = User.getInstance(); //This is the currently logged in user.
    private Set<Cell> schedule = new HashSet<>();
    private Set<Cell> activities = new HashSet<>();

    //TODO: Get all the subjects that the student attends
    //TODO: Get all the schedules from students subjects
    //TODO: Sort Cells inside a single Day-object
    //TODO: Sort Days inside a Week-object
    //TODO: Sort Weeks inside a Month-object
    //TODO: Get all activities which the student have entered
    //TODO: Sort on prioirty
    //TODO: Merge the two Sets of only Cells
    //TODO: Return the set
    //TODO: Implement an update option so that the algorithm does not have to run all over.
    //TODO: Save the results?
    //TODO: Delete backwards in time?



}
