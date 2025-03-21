package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MyStepdefs {
    private WebDriver driver;
    private WebDriverWait wait;

    // Element Locators
    private static final By DATE_FIELD = By.xpath("//*[@id=\"dp\"]");
    private static final By FIRST_NAME_FIELD = By.id("member_firstname");
    private static final By LAST_NAME_FIELD = By.id("member_lastname");
    private static final By EMAIL_FIELD = By.id("member_emailaddress");
    private static final By PASSWORD_FIELD = By.xpath("//*[@id=\"signupunlicenced_password\"]");
    private static final By CONFIRM_PASSWORD_FIELD = By.xpath("//*[@id=\"signupunlicenced_confirmpassword\"]");
    private static final By TERMS_CHECKBOX = By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]");
    private static final By JOIN_BUTTON = By.cssSelector("#signup_form > div.form-actions.noborder > input");
    private static final By SUCCESS_MESSAGE = By.xpath("/html/body/div/div[2]/div/h2");
    private static final By LAST_NAME_ERROR = By.id("lastNameError");
    private static final By PASSWORD_MISMATCH_ERROR = By.id("passwordMismatchError");
    private static final By TERMS_ERROR = By.id("termsError");

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Given("the user is on the registration page")
    public void userIsOnRegistrationPage() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("the user enters valid details")
    public void userEntersValidDetails() {
        enterCommonDetails("Test", "User", "testuser@example.com", "Password123", "Password123");
        driver.findElement(DATE_FIELD).sendKeys("12/03/1970");

        driver.findElement(TERMS_CHECKBOX).click();
    }

    @When("the user submits the form")
    public void userSubmitsForm() {
        wait.until(ExpectedConditions.elementToBeClickable(JOIN_BUTTON)).click();
    }

    @Then("the account should be created successfully")
    public void accountCreatedSuccessfully() {
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
        Assert.assertEquals("CREATE AN ACCOUNT", successMsg.getText().trim());
    }

    @When("the user enters details with missing last name")
    public void userEntersMissingLastName() {
        enterCommonDetails("Test", "", "testuser@example.com", "Password123", "Password123");
        driver.findElement(TERMS_CHECKBOX).click();
    }

    @Then("an error message for missing last name should be displayed")
    public void errorMessageForMissingLastName() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(LAST_NAME_ERROR));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @When("the user enters details with mismatching passwords")
    public void userEntersMismatchingPasswords() {
        enterCommonDetails("Test", "User", "testuser@example.com", "Password123", "DifferentPassword");
        driver.findElement(TERMS_CHECKBOX).click();
    }

    @Then("an error message for password mismatch should be displayed")
    public void errorMessageForPasswordMismatch() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_MISMATCH_ERROR));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @When("the user enters details but does not accept terms and conditions")
    public void userDoesNotAcceptTerms() {
        enterCommonDetails("Test", "User", "testuser@example.com", "Password123", "Password123");
    }

    @Then("an error message for not accepting terms and conditions should be displayed")
    public void errorMessageForNotAcceptingTerms() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(TERMS_ERROR));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @After
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Exception during driver quit: " + e.getMessage());
        }
    }

    private void enterCommonDetails(String firstName, String lastName, String email, String password, String confirmPassword) {
        driver.findElement(FIRST_NAME_FIELD).clear();
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);

        if (!lastName.isEmpty()) {
            WebElement lastNameField = driver.findElement(LAST_NAME_FIELD);
            lastNameField.clear();
            lastNameField.sendKeys(lastName);
        }

        driver.findElement(EMAIL_FIELD).clear();
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_FIELD).clear();
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(CONFIRM_PASSWORD_FIELD).clear();
        driver.findElement(CONFIRM_PASSWORD_FIELD).sendKeys(confirmPassword);
    }
}
