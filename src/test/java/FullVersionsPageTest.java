import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.autyzmsoft.TestUtils;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FullVersionsPageTest extends BaseTest {

    @BeforeMethod
    private void bringUpFullVersionsPage() {
        fullVersionsPage = homePage.gotoFullVersionsPage();
    }

    //FV_01
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

    //FV_02
    /***
     * Passed if Order Details page appears.
     * ddt is used as there are 2 buttons that clicking on them should have same effect
     */
    @Test(dataProviderClass = TestsData.class, dataProvider = "multiplayer")
    public void testClickOrderButtonsAfterChoosingItems(String[] run) {
        List<WebElement> cbList = fullVersionsPage.getAllCheckboxesList();
        //clicking on randomly chosen checkbox:
        int maxVal = cbList.size();
        int rnd = new Random().nextInt(maxVal);
        WebElement cb = cbList.get(rnd);
        cb.click();
        //Moving to OrderDetailsPage:
        WebElement orderBtn = this.determineButtonToClick(run[1]);
        orderBtn.click();
        //Verifying page title:
        WebElement pageTitle = driver.findElement(By.xpath(TestUtils.PAGE_TITLES_LOCATION));
        String pageTitleText = pageTitle.getText();
        boolean testOK = pageTitleText.equals("SZCZEGÓŁY ZAMÓWIENIA");
        Assert.assertTrue(testOK, "Order Details Page did not appear or wrong page title!");
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


}
