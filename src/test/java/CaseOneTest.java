/* This is the case study from company that called "Sahibinden"*/

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Contains scenario one's tasks.
 * <p>
 * To test Sign Up screen for Corporate Membership.
 *
 * @author Oğuzhan Taşgın
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaseOneTest {

    /**
     * Static WebDriver object.
     */
    private static WebDriver driver;

    /**
     *Static Domain Name
     */
    private final static String DOMAIN = "https://www.sahibinden.com";

    /**
     * Creates static WebDriverWait object.
     */
    private static WebDriverWait wait;

    /**
     * <p>Setups chrome WebDriver.</p>
     * Runs once before the test scenario
     */
    @BeforeClass
    public static void setUp() {
        Path projectPath = Paths.get(System.getProperty("user.dir"));
        Path driverPath = Paths.get(projectPath.toString(), "src", "test", "resources", "geckodriver");

        System.setProperty("webdriver.gecko.driver", driverPath.toString());

        driver = new FirefoxDriver();
        driver.navigate().to(DOMAIN);
        wait = new WebDriverWait(driver, 3);
    }

    /**
     * <p>Main test method. </p>
     * Controls page status.
     */
    @Test
    public void test1CheckTitle() {
        // Check Title of Website
        String title = driver.getTitle();
        System.out.println(title);
        Assert.assertTrue("Title should start with Sahibinden", title.startsWith("Sahibinden"));
    }

    @Test
    public void test2CheckHomePageNot404() {
        // Avoid from 404 Error

        String pageSource = driver.getPageSource();
        Assert.assertFalse(pageSource.contains("404"));
    }

    @Test
    public void test3CheckRegisterPageNot404() {
        // Click on register page and check existence of page. Avoid from 404 Error
        driver.findElement(By.className("register-text")).click();

        String pageSource = driver.getPageSource();
        Assert.assertFalse(pageSource.contains("404"));
    }

    @Test
    public void test4CheckCaptchaErrorMessage() {
        //Selected corporate radio button.
        WebElement radioCorporate = driver.findElement(By.id("corporate"));
        performClickOperation(radioCorporate);

        // Filling required fields

        //Name field
        fillText("name", "Oğuzhan");

        //Surname field
        fillText("surname", "Taşgın");

        //E-mail field
        fillText("email", "oguzhantasgin@icloud.com");

        //Password field
        fillText("password", "oguzhan12345");

        //Mobile field
        fillText("mobile", "5534666107");

        //Category field
        Select dropListCategory = new Select(driver.findElement(By.id("category")));

        dropListCategory.selectByVisibleText("Emlak");

        driver.findElement(By.id("closeCookiePolicy")).click();

        //Company type field
        WebElement radioBusinessType = driver.findElement(By.id("limitedCompany"));

        performClickOperation(radioBusinessType);

        //Company name field
        fillText("limitedCompanyName", "/Bad Company Ltd.");

        //City field.
        wait.until(ExpectedConditions.elementToBeClickable(By.id("city")));

        Select dropListCity = new Select(driver.findElement(By.id("city")));
        dropListCity.selectByIndex(1);

        //Town field.
        wait.until(ExpectedConditions.elementToBeClickable(By.id("town")));

        Select dropListTown = new Select(driver.findElement(By.id("town")));
        dropListTown.selectByIndex(1);

        //Quarter field, chosen by index.
        wait.until(ExpectedConditions.elementToBeClickable(By.id("quarter")));

        Select dropListQuarter = new Select(driver.findElement(By.id("quarter")));
        dropListQuarter.selectByIndex(2);

        //Address field
        fillText("address", "Next to the road");


        //Tax office field, chosen by index.
        wait.until(ExpectedConditions.elementToBeClickable(By.id("taxOffice")));

        Select dropListTaxOffice = new Select(driver.findElement(By.id("taxOffice")));

        //Tax number field
        dropListTaxOffice.selectByIndex(0);
        fillText("taxNumber", "1234567890");

        //Phone numbers fields.
        fillText("phone", "2122121212");

        fillText("phone2", "2122222222");

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
    private void fillText(String htmlId, String inputValue) {
        driver.findElement(By.id(htmlId)).sendKeys(inputValue);
    }

}
