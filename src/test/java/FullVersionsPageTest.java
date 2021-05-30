import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.autyzmsoft.TestUtils;

import org.openqa.selenium.JavascriptExecutor;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FullVersionsPageTest extends BaseTest {

    @BeforeMethod
    public void bringUpFullVersionsPage() {
        fullVersionsPage = homePage.gotoFullVersionsPage();
    }


    /***
     * Auxiliary; returns button 1 or 2 as specified by the 'number' parameter
     */
    private WebElement determineButtonToClick(String number) {
       WebElement btn;
       btn = fullVersionsPage.getOrderButton1();
       if (number.equals("2")) {
           btn = fullVersionsPage.getOrderButton2();
       }
        return btn;
    }

    /***
     * Passed if Alert window appears AND it contains proper informative text.
     * ddt is used as there are 2 buttons that clicking on them should have same effect
     */
    @Test(dataProviderClass = TestsData.class, dataProvider = "multiplayer")
    public void testClickOrderButtonsWithoutChoosingItems(String[] run) {
        final String ALERT_TEXT_OK = "NIE ZAZNACZYŁEŚ NICZEGO";
        WebElement orderBtn = this.determineButtonToClick(run[1]);
        orderBtn.click();
        boolean test_ok;
        try {
            new WebDriverWait(driver, TestUtils.WAIT_TIME).until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String tekst = alert.getText();
            tekst = tekst.toUpperCase(Locale.ROOT);
            test_ok = tekst.contains(ALERT_TEXT_OK);
            TestUtils.mySleep(1000); //unnecessary, for better visual effect ;)
            alert.accept();
        } catch (TimeoutException e) {
            test_ok = false;
            System.out.println("testClickOrderButtonsWithoutChoosingItems() "+e.getMessage());
            Reporter.log(e.getMessage());
        }
        Assert.assertTrue(test_ok, "Test failed. Alert window did not appear or improper text in the alert window!");
    }

    /***
     * Passed if Order Details page appears.
     * ddt is used as there are 2 buttons that clicking on them should have same effect
     */
//    @Test(dataProviderClass = TestsData.class, dataProvider = "multiplayer")
    @Test
    public void testClickOrderButtonsAfterChoosingItems() /*(String[] run)*/ {
        List<WebElement> cbList = fullVersionsPage.getAllCheckboxesList();
        //clicking on randomly chosen checkbox:
        int maxVal = cbList.size();
        int rnd = new Random().nextInt(maxVal);  //randint(0, maxVal);
        rnd=0;
        System.out.println(maxVal);
        System.out.println(rnd);
        WebElement cb = cbList.get(rnd);
        System.out.println("doszlem.....");
//        new Actions(driver).moveToElement(cb).perform(); //move_to_element(cb).perform()

//        Actions ac = new Actions(driver);
//        System.out.println("doszlem1..");
//        ac.moveToElement(fullVersionsPage.getOrderButton1()).perform(); //move_to_element(cb).perform()
//        System.out.println("doszlem2.....");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 150)");  //sometimes move_to_element is not enough to fully show the element
        cb.click();
        TestUtils.mySleep(3000);
    }


}
