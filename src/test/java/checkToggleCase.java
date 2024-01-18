import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;


public class checkToggleCase {
    @Test
    public void runAutomatedTests() {
        String excelFilePath = "C:\\Users\\nikma\\myMarmetoExcelData.xlsx";
        // Step 2: Read URLs from Google Sheet
        Map<Integer, String> studentUrls = readUrlsFromGoogleSheet(excelFilePath);
        System.out.println(studentUrls);
        // Step 3: Automate the Testing with Selenium
        Map<Integer, String> testResults = new HashMap<>();
        Map<Integer, String> testMistakes = new HashMap<>();
        for (Map.Entry<Integer, String> entry : studentUrls.entrySet()) {
            int rowNumber = entry.getKey();
            String url = entry.getValue();

            try {
                // Your Selenium code to test the project and capture results
                if (!(url == "")) {
                    String[] results = performTests(url);
                    String finalResult = results[0];
                    String mistakes = results[1];
                    // Store the result in the map
                    testResults.put(rowNumber, finalResult);
                    testMistakes.put(rowNumber, mistakes);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore interrupted status
                // Handle the interruption or log the exception
                System.err.println("Thread was interrupted while performing tests.");
            }
        }
        // Step 5: Update Google Sheet with Test Results
        updateGoogleSheetWithResults(testResults,excelFilePath);
        updateGoogleSheetWithMistakes(testMistakes,excelFilePath);
    }


    private static Map<Integer, String> readUrlsFromGoogleSheet(String excelFilePath) {
        Map<Integer, String> studentUrls = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath)) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet

            int numberOfRows = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < numberOfRows; i++) { // Start from 1 to skip header row
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(0); // Assuming URL is in the first column

                // Extract URL and store it in the map
                studentUrls.put(i, cell.getStringCellValue());
            }

            workbook.close();
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }

        return studentUrls;
    }

    private static String[] performTests(String url) throws InterruptedException {

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikma\\Downloads\\Selenium coding practice\\assignments\\chromedriver-win32\\chromedriver.exe");

        // Launch Chrome Browser
        WebDriver driver = new ChromeDriver();

        // Navigate to the Index page
        driver.get(url);
        int final_result = 0;
        String mistakes = "";
        String[] texts = {"men", "women", "kids"};
        for (String text : texts) {
            try {
                String searchText = text;

                String xpathExpression = "//*[normalize-space(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'))='" + searchText+ "']";
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement menCategoryTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));

                // If the element is found, perform actions
                menCategoryTab.click();
                Thread.sleep(3000);
                // Find all image elements

                List<WebElement> imageElements = driver.findElements(By.tagName("img"));
                List<WebElement> displayedImages = new ArrayList<>();

                for (WebElement img : imageElements) {
                    if (img.isDisplayed()) {
                        displayedImages.add(img);
                    }
                }

                System.out.println(final_result);

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
            }catch (InvalidArgumentException e) {
                System.err.println("Invalid argument exception: " + e.getMessage());
                // Handle the exception or log more details as needed
            }
            catch (Exception e) {
                // Handle any other exceptions
                System.err.println("Exception: " + e.getMessage());
                mistakes += "Exception: " + e.getMessage() + ". ";
            }

        }
        driver.quit();
        return new String[]{String.valueOf(final_result), mistakes};
    }

    private static void updateGoogleSheetWithResults(Map<Integer, String> testResults,String excelFilePath) {
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(testResults);
            for (Map.Entry<Integer, String> entry : testResults.entrySet()) {
                int rowNumber = entry.getKey();
                String result = entry.getValue();

                Row row = sheet.getRow(rowNumber);
                Cell cell = row.createCell(1, CellType.STRING); // Assuming you want to store the result in the second column
                cell.setCellValue(result);
            }

            // Save the updated workbook
            try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOutputStream);
            }
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }

    private static void updateGoogleSheetWithMistakes(Map<Integer, String> testMistakes, String excelFilePath) {
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            System.out.println(testMistakes);
            for (Map.Entry<Integer, String> entry : testMistakes.entrySet()) {
                int rowNumber = entry.getKey();
                String result = entry.getValue();

                Row row = sheet.getRow(rowNumber);
                Cell cell = row.createCell(4, CellType.STRING); // Assuming you want to store the result in the second column
                cell.setCellValue(result);
            }

            // Save the updated workbook
            try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOutputStream);
            }
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }


}
