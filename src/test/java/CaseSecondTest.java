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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Contains scenario one's tasks.
 * <p>
 * To test Sign Up screen for Corporate Membership.
 *
 * @author Oğuzhan Taşgın
 */
public class CaseSecondTest {

    /**
     * Static WebDriver object.
     */
    private static WebDriver driver;

    /**
     * Creates static WebDriverWait object.
     */
    private static WebDriverWait wait;

    /**
     *Static Domain Name
     */
    private final static String DOMAIN = "https://www.sahibinden.com";

    /**
     * <p>Setups chrome WebDriver.</p>
     * <p>
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
    public void testCaseSecond() {

        //Close Cookie Policy span.
        driver.findElement(By.id("closeCookiePolicy")).click();

        //Residence
        driver.findElement(By.xpath("//*[@id=\"container\"]/div[3]/div/aside/div[1]/nav/ul[3]/li[1]/ul/li[1]/a")).click();

        //For Sale
        driver.findElement(By.xpath("//*[@id=\"container\"]/div[1]/div[1]/div/div[2]/ul/div/div/li[1]/a")).click();

        //Apartment
        driver.findElement(By.xpath("//*[@id=\"searchCategoryContainer\"]/div/div/ul/li[1]/a")).click();

        //Open city list
        WebElement openCities = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchResultLeft-address\"]/dl/dd/ul/li[1]")));

        openCities.click();

        //Select city
        WebElement selectCity = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-id='34']")));

        selectCity.click();

        // Wait until loader is closed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading-80")));

        WebElement searchSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[4]/form/div/div[2]/div[23]/button")));

        searchSubmitButton.click();

        // Wait until address = "İstanbul (Tümü)" on page in filters.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"currentFilters\"]/li[2]/a")));

        WebElement moreSelectionLink = driver.findElement(By.xpath("//*[@id=\"moreSelectionLink\"]/a"));

        //Move to moreSelectionLink element.
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", moreSelectionLink);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("sticky-search-button fixed")));

        //Manuel scrolling. Avoid from span.
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-100)");

        //Click more selection
        moreSelectionLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"moreSelectionLink\"]/a")));

        moreSelectionLink.click();

        //
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cboxLoadedContent")));

        //Transportation
        WebElement transportation = driver.findElement(By.id("faceted-left-menu-Ulaşım"));

        //Move to transportation element.
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", transportation);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("faceted-left-menu-Ulaşım")));

        ((JavascriptExecutor) driver).executeScript("document.getElementById(\"faceted-left-menu-Ulaşım\").click()");

        //Select Eurasia Tunnel option
        WebElement eurasiaTunnel = wait.until(ExpectedConditions.elementToBeClickable(By.id("faceted-true-a106958")));

        eurasiaTunnel.click();

        WebElement detailedSearchSubmitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("doDetailedSearchButton")));

        detailedSearchSubmitButton.click();

        // Wait until loader is closed
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading-80")));

        //Wait until transportation = "Avrasya" on page in filters.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"currentFilters\"]/li[3]/a")));

        WebElement resultsTable = driver.findElement(By.id("searchResultsTable"));

        List<WebElement> tableRows = resultsTable.findElements(By.tagName("tr"));

        WebElement dataElement = tableRows.get(1).findElement(By.className("classifiedTitle"));

        wait.until(ExpectedConditions.elementToBeClickable(dataElement));

        //Selected first posting on table.
        dataElement.click();

        WebElement eurasiaInPosting = driver.findElement(By.xpath("/html/body/div[4]/div[4]/div[2]/div[2]/div[2]/div[2]/ul[6]/li[2]"));

        //Is Eurasia selected ?
        Assert.assertTrue(elementHasClass(eurasiaInPosting, "selected"));


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
     * @param element Selected element
     * @param className Class name which will be checked.
     * @return
     */
    public boolean elementHasClass(WebElement element, String className) {
        return element.getAttribute("class").contains(className);
    }


}
