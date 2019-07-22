/* This is the case study from company that called "Sahibinden"*/

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/**
 * Contains scenario one's tasks.
 * <p>
 * To test Sign Up screen for Corporate Membership.
 *
 * @author Oğuzhan Taşgın
 */
public class CaseOneTest {

    /**
     * Static WebDriver object.
     */
    private static WebDriver driver;

    /**
     *Creates static WebDriverWait object.
     */
    private static WebDriverWait wait;

    /**
     * <p>Setups chrome WebDriver.</p>
     * <p>
     * Runs once before the test scenario
     */
    @BeforeClass
    public static void setUp() {

        System.setProperty("webdriver.gecko.driver", "/Users/oguzhantasgin/Downloads/geckodriver");
        driver = new FirefoxDriver();
        driver.navigate().to("https://www.sahibinden.com");
        wait = new WebDriverWait(driver, 5);
    }

    /**
     * <p>Main test method. </p>
     * Controls page status.
     */
    @Test
    public void testCaseOne() {

        //Check Title of Website
        Assert.assertTrue("Title should start with Sahibinden", driver.getTitle().startsWith("Sahibinden"));

        //Click on register page and check existence of page. Avoid from 404 Error
        driver.findElement(By.className("register-text")).click();

        String pageSource = driver.getPageSource();

        if (pageSource.contains("404")) {

            Assert.assertTrue("404 Not Found Error", true);
            endProcess();

        } else {

            //User interaction with JS Executor
            WebElement radioCorporate = driver.findElement(By.id("corporate"));

            performClickOperation(radioCorporate);

            //Filling required fields

            //Name field
            insertText("name", "Oğuzhan");

            //Surname field
            insertText("surname", "Taşgın");

            //E-mail field
            insertText("email", "oguzhantasgin@icloud.com");

            //Password field
            insertText("password", "oguzhan12345");

            //Mobile field
            insertText("mobile", "5534666107");

            //Category field
            Select dropListCategory = new Select(driver.findElement(By.id("category")));

            dropListCategory.selectByVisibleText("Emlak");

            driver.findElement(By.id("closeCookiePolicy")).click();

            //Company type field
            WebElement radioBusinessType = driver.findElement(By.id("limitedCompany"));

            performClickOperation(radioBusinessType);

            //Company name field
            insertText("limitedCompanyName", "/Bad Company Ltd.");

            //City field.
            wait.until(ExpectedConditions.elementToBeClickable(By.id("city")));

            Select dropListCity = new Select(driver.findElement(By.id("city")));

            dropListCity.selectByIndex(calculateSizeOfDropDownList(dropListCity) - 3);

            //Town field.
            wait.until(ExpectedConditions.elementToBeClickable(By.id("town")));

            Select dropListTown = new Select(driver.findElement(By.id("town")));

            dropListTown.selectByIndex(calculateSizeOfDropDownList(dropListTown) - 1);

            //Quarter field, chosen by index.
            wait.until(ExpectedConditions.elementToBeClickable(By.id("quarter")));

            Select dropListQuarter = new Select(driver.findElement(By.id("quarter")));

            dropListQuarter.selectByIndex(calculateSizeOfDropDownList(dropListQuarter) - 1);

            //Address field
            insertText("address", "Next to the road");

            //Tax office field, chosen by index.
            Select dropListTaxOffice = new Select(driver.findElement(By.id("taxOffice")));

            dropListTaxOffice.selectByIndex(calculateSizeOfDropDownList(dropListTaxOffice) - 1);

            //Tax number field
            insertText("taxNumber", "1234567890");

            //Phone numbers fields.
            insertText("phone", "2122121212");

            insertText("phone2", "2122222222");

            //Licence Agreement check box control.
            WebElement checkBoxAgree = driver.findElement(By.id("endUserLicenceAgreement"));

            performClickOperation(checkBoxAgree);

            //SignUp button action.
            WebElement signUp = wait.until(ExpectedConditions.elementToBeClickable(By.id("signUpButton")));

            signUp.submit();

            //Assert captcha error label
            WebElement labelError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".captcha-area > label:nth-child(3)")));

            Assert.assertEquals(labelError.getText(), "Güvenlik kodunu kontrol edip tekrar deneyiniz.");

        }


    }

    /**
     * <p>Method that works once after main test is over.</p>
     * <p>
     * Runs once after the test scenario
     */
    @AfterClass
    public static void cleanUp() {

        driver.close();

    }

    /**
     * <p>Closes driver.</p>
     */
    private void endProcess() {

        driver.close();

    }

    /**
     * <p>Selects radio button as real user</p>
     *
     * @param webElement the object to process by javascript executor.
     */
    private void performClickOperation(WebElement webElement) {


        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", webElement);

    }

    /**
     * <p>Insert data to text fields</p>
     *
     * @param htmlId     Html id.
     * @param inputValue Text to fill required field.
     */
    private void insertText(String htmlId, String inputValue) {

        driver.findElement(By.id(htmlId)).sendKeys(inputValue);


    }

    /**
     * <p>Calculates DropDown list's size.</p>
     *
     * @param select Selected DropDown object.
     * @return Returns size of list.
     */

    private int calculateSizeOfDropDownList(Select select) {

        List<WebElement> list = select.getOptions();

        return list.size();

    }
}
