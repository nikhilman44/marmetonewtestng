import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import pages.*;

public class LoginPageTest {

    WebDriver driver;
    LoginPage loginPage;
    HomePage homePage;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikma\\Downloads\\chromedriver-win32\\chromedriver.exe");
        driver=new ChromeDriver();
        loginPage=new LoginPage(driver);
        homePage=new HomePage(driver);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.loginToApplicationOne("https://qamoviesapp.ccbp.tech/login","rahul","rahul@2021");
    }

    @Test (priority = 1)
    public void CheckHomePage(){
        System.out.println("Hi");
    }

    @Test (priority = 2)
    public void CheckHomPage(){
        System.out.println("Hi one");
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}