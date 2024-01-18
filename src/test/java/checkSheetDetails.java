import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Set;

public class checkSheetDetails {

    public static void main(String[] args) {

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikma\\Downloads\\Selenium coding practice\\assignments\\chromedriver-win32\\chromedriver.exe");

        // Launch Chrome Browser
        WebDriver driver = new ChromeDriver();
        driver.get("https://qaappstotest.ccbp.tech/");
        WebElement buttonMakerBtn = driver.findElement(By.id("buttonMakerBtn"));
        buttonMakerBtn.click();
        String parentHandle = driver.getWindowHandle();

        Set<String> windowHandlesList = driver.getWindowHandles();

        for (String handle : windowHandlesList) {
            if (!handle.equals(parentHandle)) {
                driver.switchTo().window(handle);
                String title = driver.getTitle();
                if(title.equals("Button Maker")) {
                    WebElement bgColorInput = driver.findElement(By.id("bgColorInput"));
                    WebElement fontColorInput = driver.findElement(By.id("fontColorInput"));
                    WebElement fontSizeInput = driver.findElement(By.id("fontSizeInput"));
                    WebElement fontWeightInput = driver.findElement(By.id("fontWeightInput"));
                    WebElement paddingInput = driver.findElement(By.id("paddingInput"));
                    WebElement borderRadiusInput = driver.findElement(By.id("borderRadiusInput"));
                    WebElement stateInput = driver.findElement(By.id("stateInput"));
                    WebElement applyButton = driver.findElement(By.id("applyButton"));
                    WebElement customButton = driver.findElement(By.id("customButton"));

                    boolean flag = true;

                    bgColorInput.sendKeys("blue");
                    fontColorInput.sendKeys("white");
                    fontSizeInput.sendKeys("16px");
                    fontWeightInput.sendKeys("bold");
                    paddingInput.sendKeys("15px");
                    borderRadiusInput.sendKeys("10px");

                    if(!stateInput.isSelected()) {
                        System.out.println("Checkbox is not selected");
                    }

                    applyButton.click();




                    String expected[]={"rgba(0, 0, 255, 1)","rgba(255, 255, 255, 1)","16px","700","15px","10px"};



                    for(int i=0;i<6;i++) {
                        String cssValueBackColor = customButton.getCssValue("background-color");
                        String color = customButton.getCssValue("color");
                        String fontsize = customButton.getCssValue("font-size");
                        String fontweight = customButton.getCssValue("font-weight");
                        String padding = customButton.getCssValue("padding");
                        String border = customButton.getCssValue("border-radius");

                        String actual[]={cssValueBackColor,color,fontsize,fontweight,padding,border};
                        System.out.println(actual[i]);
                        boolean a= actual[i].equals(expected[i]);

                        System.out.println(a);
                    }

                }
            }
        }
    }
}

