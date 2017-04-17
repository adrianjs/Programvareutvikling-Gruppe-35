package scraping;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by larsmade on 27.03.2017.
 * Scrapes All NTNU subjects and puts it in a list.
 * NB! Must Link To geckoDriver Manually.
 */
public class GetAllSubjectsNTNU {


    boolean check = false;
    /**
     * Returns a list of all subjects on NTNU.
     * @return List of all NTNU subjects.
     * @throws InterruptedException If driver gets intteruppted.
     */
    public List<String> startSileniumDriver() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\larsmade\\IdeaProjects\\Programvareutvikling-Gruppe-35\\src\\scraping\\geckodriver.exe"); //Set the geckodriver Location. --> Absolute PATH must change in case uf use.
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.ntnu.no/studier/emnesok/-/course_list/listall");
        WebElement elemtent = driver.findElement(By.className("allCourses"));
        List<WebElement> li = elemtent.findElements(By.tagName("li"));
        List<String> liste = new ArrayList<>();
        for (WebElement ele: li) {
            String st = ele.getText();
            String[] split = st.split(" ");
            String s = split[split.length -1];
            s = s.substring(1, s.length() - 1);
            if(s.contains("IMT6001")){
                check = true;
            }
            if(check){
                if(s.contains("BK") || s.contains("TTM") || s.contains("TDT")){ //CHOOSES WICH SUBJECTS TO SCRAPE TO DATABASE.
                    System.out.println(s + "SUBJECT");
                }else{
                    liste.add(s);
                }
            }
            //FM3001
        }
        driver.quit();
        return liste;
    }
}
