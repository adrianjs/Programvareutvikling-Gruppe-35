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
 */
public class GetAllSubjectsNTNU {

    private WebDriver driver;

    /**
     * Returns a list of all subjects on NTNU.
     * @return List of all NTNU subjects.
     * @throws InterruptedException If driver gets intteruppted.
     */
    public List<String> startSileniumDriver() throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\larsmade\\IdeaProjects\\Programvareutvikling-Gruppe-35\\src\\scraping\\geckodriver.exe"); //Set the geckodriver Location. --> Absolute PATH har ikke fått det til å funke ved å sette en relativ en......
        driver = new FirefoxDriver();
        driver.get("https://www.ntnu.no/studier/emnesok/-/course_list/listall");
        WebElement elemtent = driver.findElement(By.className("allCourses"));
        List<WebElement> li = elemtent.findElements(By.tagName("li"));
        List<String> liste = new ArrayList<>();
        for (WebElement ele: li) {
            String st = ele.getText();
            String[] split = st.split(" ");
            String s = split[split.length -1];
            s = s.substring(1, s.length() - 1);
            if(s.contains("TDT")){ //CHOOSES WICH SUBJECTS TO SCRAPE TO DATABASE.
                liste.add(s);
            }
        }
        driver.quit();
        return liste;
    }
}
