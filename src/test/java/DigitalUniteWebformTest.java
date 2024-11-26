import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DigitalUniteWebformTest {

    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Automate Digital Unite Webform with Today's Date Field")
    public void testDigitalUniteWebformWithTodaysDateField() {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        driver.findElement(By.xpath("//input[@id='edit-name']")).sendKeys("Sanjida");
        driver.findElement(By.id("edit-number")).sendKeys("234345");

        WebElement todaysDateField = driver.findElement(By.xpath("//input[@id='edit-date']"));
        String dateToInput = "12/15/2022";
        todaysDateField.clear();
        todaysDateField.sendKeys(dateToInput);
        System.out.println("Date manually inputted: " + dateToInput);


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement datePicker = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'datepicker-popup-class')]")));
            System.out.println("Date picker is visible.");
            WebElement pickDate = driver.findElement(By.xpath("//a[contains(@class, 'day') and text()='15']"));
            pickDate.click();
            System.out.println("Picked date: 15th");
        } catch (TimeoutException e) {
            System.err.println("Date picker did not appear in time.");
        }


        driver.findElement(By.xpath("//input[@id='edit-email']")).sendKeys("afrinsanjida997@gmail.com");
        driver.findElement(By.xpath("//textarea[@id='edit-tell-us-a-bit-about-yourself-']")).sendKeys("This is a test submission.");


        WebElement fileUpload = driver.findElement(By.xpath("//input[@id='edit-uploadocument-upload']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fileUpload);


        String filePath = "D:\\Important\\QC\\TIN\\5.Pdf"; 
        fileUpload.sendKeys(filePath); 


        WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(20));  // Increased wait time
        try {
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert detected: " + alert.getText());

    
            alert.accept();
            System.out.println("Alert accepted, continuing the test.");
        } catch (TimeoutException e) {
            System.out.println("No alert present or alert was not triggered within the expected time.");
        }


        WebElement checkbox = driver.findElement(By.xpath("//input[@id='edit-age']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);


        wait.until(ExpectedConditions.elementToBeClickable(checkbox));


        if (!checkbox.isSelected()) {
            try {
                checkbox.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            }
        }


        WebElement submitButton = driver.findElement(By.xpath("//input[@id='edit-submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);


        wait.until(ExpectedConditions.elementToBeClickable(submitButton));


        try {
            submitButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }

      
        WebElement successMessage = driver.findElement(By.xpath("//h1[normalize-space()='Thank you for your submission!']"));
        Assertions.assertEquals("Thank you for your submission!", successMessage.getText());
    }

    @AfterAll
    public void tearDown() {
        driver.quit();
    }
}
