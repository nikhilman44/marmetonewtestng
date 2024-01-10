package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    //Defining Locators
    By usernameLocator = By.id("usernameInput");
    By passwordLocator = By.id("passwordInput");
    By loginButtonLocator = By.className("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }



    //Defining the methods to perform actions on the web elements
    public void enterUserName(String username){
        driver.findElement(usernameLocator).sendKeys(username);
    }
    public void enterPassword(String password){
        driver.findElement(passwordLocator).sendKeys(password);
    }
    public void clickOnLoginButton(){
        driver.findElement(loginButtonLocator).click();
    }
    public void loginIntoApplication( String username, String password){

        enterUserName(username);
        enterPassword(password);
        clickOnLoginButton();
    }
    public void startLogin(String url,String username,String password){
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("usernameInput")));
        System.out.println("Yes");
        loginIntoApplication(username,password);
        String  expected= "https://qamoviesapp.ccbp.tech/";
        wait.until(ExpectedConditions.urlToBe(expected));
        String actual=driver.getCurrentUrl();
        Assert.assertEquals(actual,expected,"Home Url Mismatch");
    }

    public void loginToApplicationOne(String url, String username, String password){
        startLogin(url, username, password);
    }


}