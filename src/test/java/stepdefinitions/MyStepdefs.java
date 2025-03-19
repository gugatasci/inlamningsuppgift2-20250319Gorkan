package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MyStepdefs{
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Given("the user is on the registration page")
    public void userIsOnRegistrationPage() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("the user enters valid details")
    public void userEntersValidDetails() {
        enterCommonDetails("Test", "User", "testuser@example.com", "Password123", "Password123");
        driver.findElement(By.id("terms")).click();
    }

    @When("the user submits the form")
    public void userSubmitsForm() {
        driver.findElement(By.id("submit")).click();
    }

    @Then("the account should be created successfully")
    public void accountCreatedSuccessfully() {
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
        Assert.assertTrue(successMsg.isDisplayed());
    }

    @When("the user enters details with missing last name")
    public void userEntersMissingLastName() {
        enterCommonDetails("Test", "", "testuser@example.com", "Password123", "Password123");
        driver.findElement(By.id("terms")).click();
    }

    @Then("an error message for missing last name should be displayed")
    public void errorMessageForMissingLastName() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lastNameError")));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @When("the user enters details with mismatching passwords")
    public void userEntersMismatchingPasswords() {
        enterCommonDetails("Test", "User", "testuser@example.com", "Password123", "DifferentPassword");
        driver.findElement(By.id("terms")).click();
    }

    @Then("an error message for password mismatch should be displayed")
    public void errorMessageForPasswordMismatch() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordMismatchError")));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @When("the user enters details but does not accept terms and conditions")
    public void userDoesNotAcceptTerms() {
        enterCommonDetails("Test", "User", "testuser@example.com", "Password123", "Password123");
    }

    @Then("an error message for not accepting terms and conditions should be displayed")
    public void errorMessageForNotAcceptingTerms() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("termsError")));
        Assert.assertTrue(errorMsg.isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void enterCommonDetails(String firstName, String lastName, String email, String password, String confirmPassword) {
        driver.findElement(By.id("firstName")).sendKeys(firstName);
        if (!lastName.isEmpty()) {
            driver.findElement(By.id("lastName")).sendKeys(lastName);
        }
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmPassword")).sendKeys(confirmPassword);
    }
}
