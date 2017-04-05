package scraping;

import database.Teacher;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by larsmade on 23.03.2017.
 * Scrapes the NTNU sides --> Check out Drive Method to see how it it set up.
 */


public class SileniumDriver {

    private WebDriver driver;
    private List<WebElement> timeTable;
    private List<WebElement> lectures = new ArrayList<>();
    //private List<WebElement> labs = new ArrayList<>(); //Use if we want labs to be set to database.
    private String discription;
    private String eMail = "";
    private String courseCoordinator = "";
    private String emailSide = "";
    private String subjectCode = "";
    private String subjectCode1 = "";
    private String department = "";
    private String evaluattion = "";
    private String subjectDescription;

    //For Exam
    private LocalDate startDate = null;
    private String typeOfExam = "";
    private String weight = "";
    private String aid = "";
    private String startTime = "";
    private String endTime = "";
    /**
     * Start the webdriver with Firefoxdriver.
     */
    private void startSileniumDriver(){
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\larsmade\\IdeaProjects\\Programvareutvikling-Gruppe-35\\src\\scraping\\geckodriver.exe"); //Set the geckodriver Location. --> Absolute PATH har ikke fått det til å funke ved å sette en relativ en......
        driver = new FirefoxDriver();
    }

    /**
     * Get the NTNU timePlan website.
     * @param text Must be an NTNU timetablewebside to work.
     */
    private void getWebsite(String text){
        driver.get(text);
    }

    /**
     * Closes the driver.
     */
    private void quitDriver(){
        driver.quit();
    }

    /**
     * Scrapes the side and finds the Timeslots, timeslots put into list.
     * @throws InterruptedException if not sucsessfull
     */
    private void scrapeNtnuTimeSlots() throws InterruptedException{
        try{
            timeTable.clear();
        }catch(Exception e){
            System.out.println("TimeTable was Empty");
        }
        WebElement element = driver.findElement(By.id("timeplan"));
        timeTable = element.findElements(By.tagName("tr"));
    }

    /**
     * Sort into lectures, and labs/exerzises.
     */
    private void relevantListSorter(){
        lectures.clear();
        for(WebElement w : timeTable){
            if(w.getText().contains("Forelesning")){
                lectures.add(w);
            }
//            if(w.getText().contains("Laboratorieøvelse")){ //Use if we want labs to be set to database.
//                labs.add(w);
//            }
        }
    }
//****************************Converting the scraped information to a new teacher.Lecture cell ********//
    /**
     * Startmethod to write the lectures to database.
     */
    private void lecturesToDatabase(){
        for(WebElement w : lectures){
            System.out.println(w.getText());
            discription = w.getText();
            List<WebElement> list = w.findElements(By.tagName("td"));
            sendToTeacher(list);
        }
    }

    /**
     * Seeks out the relevant information, day, amount of weeks, duration of class.
     * @param list list of lectures.
     */
    private void sendToTeacher(List<WebElement> list){
        String day = list.get(0).getText().toUpperCase();
        String duration = list.get(1).getText();
        String weeks = list.get(2).getText();
        //(String name, LocalDate startDate, String startTime, String endTime, int repeating, String description, String subjectCode){
        String name = "Lecture";
        LocalDate startDate = getLocalDate(day); //Gets the closest startdate to the day spesified. --> Så slipper man å legge til ting bakover i kalenderen.
        String startTime = getHour(true, duration);
        String endTime = getHour(false, duration);
        if(startTime.length() == 1){
            startTime = "0"+startTime;
        }
        if(endTime.length() == 1){
            endTime = "0"+endTime;
        }
        int repWeeks = getRepeatingWeeks(weeks);
        System.out.println(discription);
        String subject = subjectCode1;
        System.out.println(subject + "SUBJECTCODE");
        System.out.println(repWeeks + "REPWEEKS");
        System.out.println(startTime + "STARTTID");
        System.out.println(endTime + "SLUTTTID");
        System.out.println(name + " NAME");
        System.out.println(startDate +  "STARTDATE");
        new Teacher().addLecture(name, startDate, startTime, endTime, repWeeks, discription, subject); //Set a new lecture event.
    }

    /**
     * Get the subject sujectcode.
     * @param title The title of the website has the subjectcode.
     * @return subjectcode.
     */
    private String getSubjectCode(String title){
        String[] split = title.split("-");
        String code = split[2];
        code = code.replaceAll("\\s+","");
        return code;
    }
    /**
     * Get number of weeks from this day to the last lectureweek.
     * @param weeks the string that spesifies wich week the lectures is.
     * @return amount of weeks from this date to the last lecture week.
     */
    private int getRepeatingWeeks(String weeks){
        String substring = weeks.substring(Math.max(weeks.length() - 2, 0));
        Calendar cal = Calendar.getInstance();
        int thisWeekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        int subWeeks = Integer.parseInt(substring);
        if(subWeeks - thisWeekOfYear > 0){
            return subWeeks - thisWeekOfYear;
        }return 0;
    }

    /**
     * Splits the duration string to get the right hour.
     * @param first to set if start or end hour should be returned-
     * @param duration timeinterval
     * @return start/end time.
     */
    private String getHour(boolean first, String duration){
        String[] split = duration.split("-");
        if(first){
            String start = split[0];
            String[] split2 = start.split(":");
            int i = Integer.parseInt(split2[0]);
            System.out.println(i + "STARTTIME");
            return Integer.toString(i);
        }else{
            String end = split[1];
            String[] split2 = end.split(":");
            String endT = split2[0];
            endT = endT.replaceAll("\\s+","");
            int i = Integer.parseInt(endT);
            i = i-1;//for some reason..
            System.out.println(i + "STARTTIME");
            return Integer.toString(i);
        }
    }

    /**
     * Get a localdate object spesified on day.
     * @param day String from scraping
     * @return startDate.
     */
    private LocalDate getLocalDate(String day){
        LocalDate startDate;
        if(day.contains("MANDAG")){
            startDate = setFirstDateOfDay(2);
        }
        else if(day.contains("TIRSDAG")){
            startDate = setFirstDateOfDay(3);
        }
        else if(day.contains("ONSDAG")){
            startDate = setFirstDateOfDay(4);
        }
        else if(day.contains("TORSDAG")){
            startDate = setFirstDateOfDay(5);
        }
        else if(day.contains("FREDAG")){
            startDate = setFirstDateOfDay(6);
        }
        else if(day.contains("LØRDAG")){
            startDate = setFirstDateOfDay(7);
        }
        else if(day.contains("SØNDAG")){
            startDate = setFirstDateOfDay(1);
        }
        else{
            startDate = null;
        }
        return startDate;
    }

    /**
     * Sets the startDate for the lecture, converting from int to date.
     * @param day Int with the value of a day.
     */
    private LocalDate setFirstDateOfDay(int day){
        Calendar cal = Calendar.getInstance();
        while (cal.get(Calendar.DAY_OF_WEEK) != day) {
            cal.add(Calendar.DATE, 1);
        }
        Date input = cal.getTime();
        return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void goToEmneInfo(){
        WebElement element = driver.findElement(By.className("assessment"));
        List<WebElement> el = element.findElements(By.tagName("td"));
        evaluattion = el.get(0).getText();

        String title = getSubjectCode(driver.getTitle());
        System.out.println(title + "TITLE");
        subjectCode = title;
        String emailSite = findEmailSite();
        if(emailSite.length() > 3){
            emailSide = emailSite;
        }
        subjectDescription();
    }

    /**
     * Get the subject description.
     */
    private void subjectDescription(){
        WebElement element = driver.findElement(By.id("course-details"));
        WebElement element1 = element.findElement(By.tagName("h1"));
        String desc = element1.getText();
        String[] split = desc.split("-", 2);
        subjectDescription = split[1];
    }

    /**
     * Find the courseCoordinator Email side.
     * @return The side to get Email.
     */
    private String findEmailSite(){
        List<WebElement> element = driver.findElements(By.className("sidebar"));
        for (WebElement e: element) {
            try {
                List<WebElement> element1 = e.findElements(By.tagName("a"));
                for (WebElement el: element1) {
                    String s = el.getAttribute("outerHTML");
                    if(s.contains("ansatte")){
                        courseCoordinator = el.getAttribute("innerHTML"); //Set The Course Coordinator Name.
                        Pattern p = Pattern.compile("\"([^\"]*)\"");
                        Matcher m = p.matcher(s);
                        while (m.find()) {
                            s = m.group(1);
                        }
                        return s;
                    }
                }
            }catch(Exception err){
                System.out.println("Failed to find EMAIL-Webside");
            }
        }
        return "";
    }



    /**
     * Find the CourseCoordinator Email.
     */
    private void CourseCoordinator() {
        //Find Email
        List<WebElement> element = driver.findElements(By.className("coreinfo"));
        WebElement el = driver.findElement(By.className("coreinfo"));
        WebElement ele = el.findElement(By.className("text"));
        eMail = ele.getText();
        //Find Department
        WebElement dep = driver.findElement(By.className("orgUnit"));
        department = dep.getText();
    }

    /**
     * Sets new coursecoordinator to the databas.
     */
    private void setNewCourseCoordinator(){
        //String email, String firstName, String lastName, String department, String description
        String[] name = courseCoordinator.split(" ");
        new Teacher().addCourseCoordinator(eMail, name[0], name[name.length-1], department,"123", "Description");
    }

    /**
     * Sets new Subject to the database.
     */
    private void setNewSubject(){
        System.out.println(subjectCode + "SUBJECTCODE");
        new Teacher().updateSubject(subjectDescription, subjectCode1); // --> IF SUBJECT DESCRIPTION IS BEEING UPDATED.
//        new Teacher().addSubject(subjectCode1, subjectDescription, evaluattion, eMail); //IF NEW SUBJECT IS SET TO DATABASE
    }

    /**
     * Sets new exam to the database.
     */
    private void setNewExam(){
        //(String name, LocalDate date, String startTime, String endTime,String description, String subjectCode)
        if(typeOfExam.contains("Skriftlig")){
            new Teacher().addExam(typeOfExam, startDate,startTime,endTime,"Vekting: "+ weight+ " Hjelpemiddelkode: "
                    + aid, subjectCode1);
        }else{
            System.out.println("This is not a writing exam: " + typeOfExam);
        }

    }

    /**
     * Scrapes the NTNU emne OM-EKSAMEN side. To get the relevant exam information.
     */
    private void scrapeExamInfo(){
        WebElement element = driver.findElement(By.id("omEksamen"));
        WebElement element1 = element.findElement(By.tagName("tbody"));
        List<WebElement> examInfo = element1.findElements(By.tagName("tr"));
        List<WebElement> info = null;
        for (WebElement e:examInfo
             ) {

            if(e.getText().contains("Skriftlig")){
                System.out.println(e.getText());
                info = e.findElements(By.tagName("td"));
                break;
            }
        }
        typeOfExam = info.get(1).getText();
        weight = info.get(2).getText();
        aid = info.get(3).getText();
        startDate = makeLocalDate(info.get(4).getText());
        startTime = makeStartTime(info.get(5).getText());
        System.out.println("STARTTIME: " + startTime);
        endTime = "13";
    }

    /**
     * Removes starttime minutes.
     * @param startTime startTime
     * @return starttime without minutes
     */
    private String makeStartTime(String startTime){
        String[] split = startTime.split(":");
        return split[0];
    }
    /**
     * Makes the date from NTNU-side to a localDate.
     * @param date Date from NTNU
     * @return LocalDate
     */
    private LocalDate makeLocalDate(String date){
        String[] split = date.split("\\.");
        int day = removeZero(split[0]);
        int month = removeZero(split[1]);
        int year = removeZero(split[2]);
        return LocalDate.of(year, month, day);
    }

    /**
     * Removes zero from start of string if existing.
     * @param tall numbered string.
     * @return integer
     */
    private int removeZero(String tall){
        if(tall.charAt(0) == '0'){
            return Character.getNumericValue(tall.charAt(1));
        }
        return Integer.parseInt(tall);
    }

    //*************************For å scrape en side*************FØLG rekkefølgen der så skal det gå smuud//

    /**
     * Scrapes the websides in right order.
     * @param fagkode Subjectcode
     * @throws InterruptedException Some of NTNU-sides dont have all information.
     */
    private void drive(String fagkode) throws InterruptedException {
        //NB BEFORE SCRAPING: SOME SUBJECTS HAVE SAME COURSECOORDINATOR, CAN CAUSE PROBLEMS..
        subjectCode1 = fagkode;
//        //Gets subject information.
//        try{
//            startSileniumDriver();
//            getWebsite("https://www.ntnu.no/studier/emner/"+ fagkode +"#tab=omEmnet");
//            goToEmneInfo();
//            quitDriver();
//        }catch (Exception e){
//            quitDriver();
//            System.out.println("Failed to get subject information");
//            e.printStackTrace();
//        }

//        //Get courseCoordinator email.
//        try{
//            startSileniumDriver();
//            getWebsite(emailSide);
//            CourseCoordinator();
//            setNewCourseCoordinator();
//            setNewSubject(); //Subject must be set after courseCoordinator.
//            quitDriver();
//        }catch(Exception e){
//            quitDriver();
//            System.out.println("Failed to get course coordinator Email.");
//            e.printStackTrace();
//        }


        //Get writing Exam.
        try{
            startSileniumDriver();
            getWebsite("https://www.ntnu.no/studier/emner/"+fagkode+"#tab=omEksamen");
            scrapeExamInfo();
            setNewExam();
            quitDriver();
        }catch(Exception e){//If all information i not there, it is probaly not a writing exam.
            quitDriver();
            System.out.println("All information for Exam not found");
            e.printStackTrace();
        }


//        //Get lectures.
//        try{
//            startSileniumDriver();
//            getWebsite("https://www.ntnu.no/studier/emner/"+ fagkode +"#tab=timeplan");
//            scrapeNtnuTimeSlots();
//            relevantListSorter();
//            lecturesToDatabase();
//            quitDriver();
//        }catch (Exception e){
//            System.out.println("Failed to get Lecture");
//            quitDriver();
//            e.printStackTrace();
//        }
//
    }


    public static void main(String[] args) throws InterruptedException {
        //Add one subject.
//        SileniumDriver sc = new SileniumDriver();
//        sc.drive("TDT4215");

        //Get ALL NTNU subjects.
        GetAllSubjectsNTNU subjects = new GetAllSubjectsNTNU();
		List<String> liste = subjects.startSileniumDriver(); //List of NTNU subjects.

        for (String s : liste) {
            SileniumDriver sc = new SileniumDriver();
            sc.drive(s);
            for(int i = 0; i <= 100; i++){
                System.out.println("Pause");
            }
            try {
                sc.driver.close();
            }catch(Exception e){
                System.out.println("Could not quit driver");
            }
        }
    }
}

