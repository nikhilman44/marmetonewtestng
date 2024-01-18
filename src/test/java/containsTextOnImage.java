import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.io.IOException;

public class containsTextOnImage   {
    public static void main(String[] args) throws InterruptedException {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikma\\Downloads\\Selenium coding practice\\assignments\\chromedriver-win32\\chromedriver.exe");

        // Launch Chrome Browser
        WebDriver driver = new ChromeDriver();

        // Navigate to the Index page
        driver.get("https://marmetocvk.ccbp.tech/");
        Thread.sleep(2000);
        int final_result = 0;
        String mistakes = "";
        String[] texts = {"men", "women", "kids"};
        for (String text : texts) {
            try {
                String searchText = text;
                System.out.println(text);
                String xpathExpression = "//*[normalize-space(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'))='" + searchText+ "']";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement menCategoryTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
                System.out.println(menCategoryTab);
                // If the element is found, perform actions
                menCategoryTab.click();

                Thread.sleep(3000);
                // Find all image elements

                List<WebElement> imageElements = driver.findElements(By.tagName("img"));
                System.out.println(imageElements.size());
                List<WebElement> displayedImages = new ArrayList<>();

                for (WebElement img : imageElements) {
                    if (img.isDisplayed()) {
                        displayedImages.add(img);
                    }
                }


                System.out.println(displayedImages.size());


                if (displayedImages.size() >= 3) {
                    final_result += 50 / 3;
                    if (final_result == 16) {
                        final_result = 15;
                    } else if (final_result == 31) {
                        final_result = 25;
                    } else if (final_result == 41) {
                        final_result = 50;
                    }
                } else {
                    List<WebElement> backgroundElements = driver.findElements(By.cssSelector("*[style*='background-image']"));
                    List<WebElement> displayedBackgroundElements = new ArrayList<>();

                    for (WebElement element : backgroundElements) {
                        if (element.isDisplayed()) {
                            displayedBackgroundElements.add(element);
                        }
                    }
                    System.out.println(displayedBackgroundElements.size());
                    if (displayedBackgroundElements.size() >= 3) {
                        final_result += 50 / 3;
                        if (final_result == 16) {
                            final_result = 15;
                        } else if (final_result == 31) {
                            final_result = 25;
                        } else if (final_result == 41) {
                            final_result = 50;
                        }
                    } else {
                        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                        List<WebElement> elementsWithBackgroundImage = (List<WebElement>) jsExecutor.executeScript(
                                "var elements = [];" +
                                        "var allElements = document.querySelectorAll('*');" +
                                        "for (var i = 0; i < allElements.length; i++) {" +
                                        "   var backgroundImage = window.getComputedStyle(allElements[i]).getPropertyValue('background-image');" +
                                        "   if (backgroundImage && backgroundImage !== 'none') {" +
                                        "       elements.push(allElements[i]);" +
                                        "   }" +
                                        "}" +
                                        "return elements;"
                        );

                        List<WebElement> displayedBackgroundElementsCss = new ArrayList<>();

                        for (WebElement element : elementsWithBackgroundImage) {
                            if (element.isDisplayed()) {
                                displayedBackgroundElementsCss.add(element);
                            }
                        }
                        System.out.println(displayedBackgroundElementsCss);
                        if (displayedBackgroundElementsCss.size() >= 3) {
                            final_result += 50 / 3;
                            if (final_result == 16) {
                                final_result = 15;
                            } else if (final_result == 31) {
                                final_result = 25;
                            } else if (final_result == 41) {
                                final_result = 50;
                            }

                        } else {

                            mistakes += "At least 3 cards with image or background image are not displayed for the category: " + text + ". ";
                            System.out.println("At least 3 cards with image or background image are not displayed for the category: " + text);
                        }
                    }
                }

            } catch (NoSuchElementException e) {
                // If the element is not found, print an error message
                System.out.println("Element: " + e.getMessage() + "not found.");
            }

        }
    }
}