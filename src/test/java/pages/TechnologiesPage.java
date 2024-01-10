package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TechnologiesPage {

    @FindBy(css = "img[src *= 'advanced']")
    @CacheLookup
    WebElement navAdvancedTechologiesElement;

    @FindBy(className = "advanced-technologies-title")
    WebElement getTechnologyTitleElement;

    @FindBy(className = "advanced-technologies-description")
    WebElement getTechnologyDescElement;

    @FindBy(className = "advanced-technologies-learn-more-button")
    WebElement advancedTechnologiesLearnMoreElement;

    @FindBy(className = "btn-primary")
    @CacheLookup
    WebElement backButtonElement;

    @FindBy(className = "software-developer-image")
    WebElement appSoftwareImageElement;

    WebDriver driver;
    WebDriverWait wait;

    public TechnologiesPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOnNavAdvancedLink(){
        navAdvancedTechologiesElement.click();
    }

    public String getAdvancedTechnologyTitleElement(){
        return getTechnologyTitleElement.getText();
    }

    public String getTechnologyParaElement(){
        return  getTechnologyDescElement.getText();
    }

    public String getLearnMoreElement(){
        return advancedTechnologiesLearnMoreElement.getText();
    }

    public void clickOnBackButton(){
        backButtonElement.click();
    }

    public WebElement findHomeSoftwareImage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("software-developer-image")));
        return appSoftwareImageElement;
    }
}