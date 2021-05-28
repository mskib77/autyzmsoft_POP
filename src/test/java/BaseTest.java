import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pl.autyzmsoft.HomePage;
import pl.autyzmsoft.LiczykropkaJsPage;
import pl.autyzmsoft.ProfMarcinJsPage;
import pl.autyzmsoft.TestUtils;

import java.util.concurrent.TimeUnit;

/***
 * Each test inherits methods and components of this class
 */
public class BaseTest {

    public WebDriver driver;

    protected HomePage homePage;
    protected LiczykropkaJsPage liczykropkaJsPage;
    protected ProfMarcinJsPage profMarcinJsPage;

    @BeforeMethod
    public void loadHomePage() {
        System.setProperty("webdriver.firefox.driver", "src/test/java/data/gecodriver");
        driver = new FirefoxDriver();
//        System.setProperty("webdriver.chrome.driver", "src/test/java/data/chromedriver");
//        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(TestUtils.WAIT_TIME, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(TestUtils.BASE_URL);
        homePage = new HomePage(driver);
        homePage.dismissCookies();
    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
