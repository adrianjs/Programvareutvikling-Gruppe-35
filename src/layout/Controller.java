package layout;

/**
 * Created by larsmade on 24.02.2017.
 */

/* --------------------------------------- This class was planned to controll the controlclasses if there is many ----*/
/* May not neeeded */
public class Controller{
    AddActivityController add;
    AddSubjectController addSubject;

    public void setAddContoller(AddActivityController controller){
        this.add = controller;
        System.out.println(add + "hop");

    }

    public void setSubjectController(AddSubjectController controller){
        this.addSubject = controller;

    }

}
