import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class AdditionGameTest {
    public WebDriver driver;
    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikma\\Downloads\\chromedriver-win32\\chromedriver.exe");
        driver= new ChromeDriver();
        driver.get("https://qaipldashboard.ccbp.tech/");
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
    @Test(priority = 1)
    public void testTheHeadingOfTheHomePage(){
        WebElement logoEl= driver.findElement(By.className("ipl-logo"));
        //String actualEl =;
        Assert.assertEquals(logoEl.isDisplayed(),true,"IPL logo is not displayed");
        WebElement hdngEl = driver.findElement(By.className("ipl-dashboard-heading"));
        String expectedEl = hdngEl.getText();
        Assert.assertEquals(expectedEl,"IPL Dashboard","Heading text does not match");

    }
    public Object[][] loginData(){
        return new Object[][]{

                {"Royal Challengers Bangalore"},
                {"Kolkata Knight Riders"},
                {"Kings XI Punjab"},
                {"Chennai Super Kings"},
                {"Rajasthan Royals"},
                {"Mumbai Indians"},
                {"Sunrisers Hyderabad"},
                {"Delhi Capitals"}

        };
    }
    @Test(priority = 2)
    public void headingText() {
        List<WebElement> listEl = driver.findElements(By.className("Link"));
        for (int i = 0; i < loginData().length; i++) {
            Object[]data = loginData()[i];
        }

    }

}
