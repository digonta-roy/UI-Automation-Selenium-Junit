import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FormFillup {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    void setUp() {
        // --- basic Chrome setup (add WebDriverManager if you prefer) ---
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-breakpad");
        // options.addArguments("--headless=new");   // ← enable if you run headless
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Guest form – happy path")
    void fillFields() {
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");

        // --- basic text fields ---
        wait.until(ExpectedConditions.elementToBeClickable(By.id("first_name"))).sendKeys("Digonta");
        driver.findElement(By.id("last_name")).sendKeys("Roy");
        driver.findElement(By.id("user_email")).sendKeys("digonta98@gmail.com");
        driver.findElement(By.id("user_pass")).sendKeys("Test@^&*12");


        driver.findElement(By.cssSelector("input[name='radio_1665627729'][value='Male']")).click();


        WebElement dobInput = driver.findElement(By.cssSelector("input[data-label='Date of Birth']"));
        dobInput.click();
        driver.findElement(By.cssSelector("span.flatpickr-day[aria-label='July 16, 2025']")).click();


        driver.findElement(By.name("input_box_1665629217")).sendKeys("Bangladeshi");
        driver.findElement(By.name("phone_1665627880")).sendKeys("(123) 456-7890");


        new Select(driver.findElement(By.id("country_1665629257")))
                .selectByVisibleText("Bangladesh");

        driver.findElement(By.id("privacy_policy_1665633140")).click();


        driver.findElement(By.cssSelector("button[type='submit']")).click();


        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.ur-message.ur-success-message")));


        Assertions.assertEquals("Digonta", driver.findElement(By.id("first_name")).getAttribute("value"));
        Assertions.assertEquals("Roy", driver.findElement(By.id("last_name")).getAttribute("value"));
        Assertions.assertEquals("digonta98@gmail.com", driver.findElement(By.id("user_email")).getAttribute("value"));
        Assertions.assertEquals("2025-07-16", dobInput.getAttribute("value"));
        Assertions.assertEquals("Bangladeshi",
                driver.findElement(By.name("input_box_1665629217")).getAttribute("value"));
        Assertions.assertEquals("(123) 456-7890",
                driver.findElement(By.name("phone_1665627880")).getAttribute("value"));
        Assertions.assertEquals("User successfully registered.", successMsg.getText().trim());
    }
}
