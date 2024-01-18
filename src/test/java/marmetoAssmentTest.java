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

public class marmetoAssmentTest {

    public static void main(String[] args) {
        // Step 2: Read URLs from Google Sheet
        Map<Integer, String> studentUrls = readUrlsFromGoogleSheet();
        System.out.println(studentUrls);
        // Step 3: Automate the Testing with Selenium
        Map<Integer, String> testResults = new HashMap<>();
        for (Map.Entry<Integer, String> entry : studentUrls.entrySet()) {
            int rowNumber = entry.getKey();
            String url = entry.getValue();

            try{
                // Your Selenium code to test the project and capture results
                if (!(url=="")){
                    String result = performTests(url);
                    // Store the result in the map
                    testResults.put(rowNumber, result);
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore interrupted status
                // Handle the interruption or log the exception
                System.err.println("Thread was interrupted while performing tests.");
            }

            System.out.println("Called");

        }
        System.out.println("ended");
        // Step 5: Update Google Sheet with Test Results
        updateGoogleSheetWithResults(testResults);
    }


    private static Map<Integer, String> readUrlsFromGoogleSheet() {
        Map<Integer, String> studentUrls = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\nikma\\OneDrive\\Desktop\\myMarmetoExcelData.xlsx")) {
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

    private static String performTests(String url) throws InterruptedException {

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\nikma\\Downloads\\Selenium coding practice\\assignments\\chromedriver-win32\\chromedriver.exe");

        // Launch Chrome Browser
        WebDriver driver = new ChromeDriver();

        // Navigate to the Index page
        driver.get(url);
        int finalResult = 0;

        // Create WebDriverWait with a specified duration
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Example usage: wait for an element to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("categories-tab")));

        // check whether the three categories tabs are appear or not
        List<WebElement> categoriesList = driver.findElements(By.cssSelector("div.categories-tab"));

//        To check categories
//        System.out.println(categoriesList.get(0).getText());
//        System.out.println(categoriesList.get(1).getText());
//        System.out.println(categoriesList.get(2).getText());

        int i;
        for (i = 0; i < categoriesList.size(); i++) {
            WebElement specificCategoryElement = categoriesList.get(i);
            Actions actions = new Actions(driver);
            actions.moveToElement(specificCategoryElement).perform();

//            to check the background color and color values
//            System.out.println(menElement.getCssValue("background-color"));
//            System.out.println(menElement.getCssValue("color"));


            boolean checkOnHoverBackgroundColor = (specificCategoryElement.getCssValue("background-color").equals("rgba(0, 0, 0, 1)"));
            boolean checkOnHoverColor = (specificCategoryElement.getCssValue("color").equals("rgba(255, 255, 255, 1)"));

            Thread.sleep(1500);

            // Move the mouse cursor by 220 pixels to the right and 80 pixels down from the current position
            actions.moveByOffset(220, 80).perform();

//            to check the background color and color values
//            System.out.println(specificCategoryElement.getCssValue("background-color"));
//            System.out.println(specificCategoryElement.getCssValue("color"));

            boolean checkOnUnhoverBackgroundColor = (specificCategoryElement.getCssValue("background-color").equals("rgba(0, 0, 0, 0)"));
            boolean checkOnUnhoverColor = (specificCategoryElement.getCssValue("color").equals("rgba(0, 0, 0, 1)"));


            if (checkOnHoverBackgroundColor && checkOnHoverColor && checkOnUnhoverBackgroundColor && checkOnUnhoverColor) {
                System.out.println("Correctly work all for" + categoriesList.get(i).getText() + "element");
            } else if (!checkOnHoverBackgroundColor) {
                System.out.println("Not working fine checkOnHoverBackgroundColor for" + categoriesList.get(i).getText() + "element");
                break;
            } else if (!checkOnHoverColor) {
                System.out.println("Not working fine checkOnHoverColor for" + categoriesList.get(i).getText() + "element");
                break;
            } else if (!checkOnUnhoverBackgroundColor) {
                System.out.println("Not working fine checkOnUnhoverBackgroundColor for" + categoriesList.get(i).getText() + "element");
                break;
            } else if (!checkOnUnhoverColor) {
                System.out.println("Not working fine checkOnUnhoverColor for" + categoriesList.get(i).getText() + "element");
                break;
            }
        }

        if (i == 3) {
            finalResult += 50;
            System.out.println("Toggle test works fine: 50% done");
        }


        // Now write a code to check if all categories products are displayed upon clicking particular category
        // Create a list of lists
        List<List<String>> listOfAltValueOfAllImages = new ArrayList<>();

        // Create and add the first list
        List<String> mensList = new ArrayList<>();
        mensList.add("Mens Kurta");
        mensList.add("RCB Tshirt");
        mensList.add("Green Charm");
        mensList.add("Mens Tshirt");
        listOfAltValueOfAllImages.add(mensList);

        // Create and add the second list
        List<String> womensList = new ArrayList<>();
        womensList.add("Women Kurti");
        womensList.add("Yellow casual dress");
        womensList.add("Women Black & Golden A-Line Kurti");
        womensList.add("METRO-FASHION");
        listOfAltValueOfAllImages.add(womensList);

        // Create and add the third list
        List<String> kidsList = new ArrayList<>();
        kidsList.add("Chicco");
        kidsList.add("Girls White & Black Printed Sustainable Tracksuit");
        kidsList.add("Custom t-shirt");
        kidsList.add("Kids Tshirt");
        listOfAltValueOfAllImages.add(kidsList);

        List<String> finalCardResultCheck = Arrays.asList("mensList", "womensList", "kidsList");


        List<WebElement> categoriesProductsList = driver.findElements(By.cssSelector("div.categories-product"));
        int x;
        for (x = 0; x < categoriesList.size(); x++) {
            categoriesList.get(x).click();
            List<WebElement> displayedProductsList = new ArrayList<>();
            int y;
            for (y = 0; y < categoriesProductsList.size(); y++) {
                if (categoriesProductsList.get(y).isDisplayed()) {
                    displayedProductsList.add(categoriesProductsList.get(y));
                }
            }

            if (displayedProductsList.size() == 4) {
                System.out.println("Show all 4 products for " + categoriesList.get(x).getText() + "category");
                List<String> cardResultCheck = new ArrayList<>();
                int z;
                for (z = 0; z < 4; z++) {
                    String altValue = displayedProductsList.get(z).findElement(By.tagName("img")).getAttribute("alt");
                    if (altValue.equals(listOfAltValueOfAllImages.get(x).get(z))) {
                        System.out.println("Image with alt: " + altValue + " found");
                    } else {
                        System.out.println("Image with alt: " + altValue + " not found");
                        break;
                    }
                }
                if (z == 4) {
                    cardResultCheck.add(finalCardResultCheck.get(x));
                } else {
                    System.out.println("Not fetching the " + finalCardResultCheck.get(x) + " cards");
                    break;
                }
            } else {
                System.out.println("Show only " + categoriesProductsList.size() + " products for " + categoriesList.get(x).getText() + "category");
            }

        }
        if (x == 3) {
            finalResult += 25;
            System.out.println("75% done");
        }


        // Now write a code to check if all categories products are displayed upon clicking particular category
        // Create a list of lists
        List<List<String>> listOfTextValueOfBadgeButtons = new ArrayList<>();

        // Create and add the first list
        List<String> mensBadgeButtonList = new ArrayList<>();
        mensBadgeButtonList.add("Wedding Special");
        mensBadgeButtonList.add("");
        mensBadgeButtonList.add("On offer");
        mensBadgeButtonList.add("New season");
        listOfTextValueOfBadgeButtons.add(mensBadgeButtonList);

        // Create and add the second list
        List<String> womensBadgeButtonList = new ArrayList<>();
        womensBadgeButtonList.add("New season");
        womensBadgeButtonList.add("");
        womensBadgeButtonList.add("On offer");
        womensBadgeButtonList.add("New season");
        listOfTextValueOfBadgeButtons.add(womensBadgeButtonList);

        // Create and add the third list
        List<String> kidsBadgeButtonList = new ArrayList<>();
        kidsBadgeButtonList.add("Wedding Special");
        kidsBadgeButtonList.add("New season");
        kidsBadgeButtonList.add("On offer");
        kidsBadgeButtonList.add("New season");
        listOfTextValueOfBadgeButtons.add(kidsBadgeButtonList);

        List<String> finalBadgeButtonResultCheck = Arrays.asList("mensBadgeButtonList", "womensBadgeButtonList", "kidsBadgeButtonList");


        for (x = 0; x < categoriesList.size(); x++) {
            categoriesList.get(x).click();
            List<WebElement> displayedProductsList = new ArrayList<>();
            int y;
            for (y = 0; y < categoriesProductsList.size(); y++) {
                if (categoriesProductsList.get(y).isDisplayed()) {
                    displayedProductsList.add(categoriesProductsList.get(y));
                }
            }

            if (displayedProductsList.size() == 4) {
                List<String> onImageTextCheck = new ArrayList<>();
                int z;
                for (z = 0; z < 4; z++) {
                    String textValue = displayedProductsList.get(z).findElement(By.cssSelector("button[class='badge-button']")).getText();
                    if (textValue.equals(listOfTextValueOfBadgeButtons.get(x).get(z))) {
                        System.out.println("Image with text: " + textValue + " found");
                    } else {
                        System.out.println("Image with text: " + textValue + " not found");
                        break;
                    }
                }
                if (z == 4) {
                    onImageTextCheck.add(finalBadgeButtonResultCheck.get(x));
                } else {
                    System.out.println("Not fetching the card with text " + finalBadgeButtonResultCheck.get(x));
                    break;
                }
            } else {
                System.out.println("Show only " + categoriesProductsList.size() + " products for " + categoriesList.get(x).getText() + "category");
            }

        }
        if (x == 3) {
            finalResult += 25;
            System.out.println("100% done");
        }

        System.out.println(finalResult);
        driver.quit();
        return String.valueOf(finalResult);
    }

    private static void updateGoogleSheetWithResults(Map<Integer, String> testResults) {
        System.out.println("I am called");
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\nikma\\OneDrive\\Desktop\\myMarmetoExcelData.xlsx");
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
            try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\nikma\\OneDrive\\Desktop\\myMarmetoExcelData.xlsx")) {
                workbook.write(fileOutputStream);
            }
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }
    }


}
