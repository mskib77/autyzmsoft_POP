import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.autyzmsoft.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DownloadPageTest extends BaseTest {

    @BeforeMethod
    public void bringUpDownloadPage() {
        downloadPage = homePage.gotoDownloadPage();
    }

    /***
     * Passed if:
     * 1. Text "WYSLANO LINKI NA ADRES" appears AND
     * 2. Addressee's email appears as text on the screen. This address is the same as the address given in the form.
     */
    @Test(priority = 0, dataProviderClass = TestsData.class, dataProvider = "GoodEmailsGenerator")
    public void testGettingDownloadLinksWithCorrectEmail(String[] emails) {
        final String INFO_TEXT_OK = "WYSLANO LINKI NA ADRES"; //info text about email that has been sent correctly
        String emailToSend = emails[1];
        downloadPage.fillEmail(emailToSend);
        downloadPage.sendEmail();
        try {
            new WebDriverWait(driver, TestUtils.WAIT_TIME).until(ExpectedConditions.presenceOfElementLocated(By.id("links_sent_1")));
        } catch (TimeoutException e) {
            System.out.println(e.getMessage() + " in testGettingDownloadLinksWithCorrect_email()");
            Reporter.log(e.getMessage());
        }
        String infoText = downloadPage.getMailSentInfoText();
        String addressee = downloadPage.getAddresseeText();
        infoText = infoText.toUpperCase(Locale.ROOT);
        boolean ok_1 = infoText.equals(INFO_TEXT_OK);
        boolean ok_2 = addressee.equals(emailToSend);

        //Determining the reason of negative test (if any):
        List<String> reasons = new ArrayList<>();
        if (!ok_1) reasons.add("Lack of or Error in message about successfully sending email.");
        if (!ok_2) reasons.add("Email has been sent do different address than the one specified in the html form.");
        boolean test_ok = ok_1 && ok_2;
        Assert.assertTrue(test_ok, reasons.toString());
    }

    /***
     * Passed if:
     * Text "We wprowadzonych danych wystąpiły błędy" appears
     */
    @Test(priority = 1, dataProviderClass = TestsData.class,dataProvider = "BadEmailsGenerator")
    public void testGettingDownloadLinksWithIncorrect_email(String[] emails) {
        String infoTextErr = new String("We wprowadzonych danych wystąpiły błędy");
        downloadPage.fillEmail(emails[1]);
        downloadPage.sendEmail();
        try {
            new WebDriverWait(driver, TestUtils.WAIT_TIME).until(ExpectedConditions.presenceOfElementLocated(By.id("email_err_1")));
        } catch (TimeoutException e) {
            System.out.println(e.getMessage() + " in testGettingDownloadLinksWithIncorrect_email()");
            Reporter.log(e.getMessage());
        }
        String infoText = downloadPage.getErrorInfoText();
        infoText = infoText.toUpperCase(Locale.ROOT);
        infoTextErr = infoTextErr.toUpperCase(Locale.ROOT);
        boolean test_ok = infoText.contains(infoTextErr);
        if (!test_ok)
            TestUtils.screenShot(driver, "Improper reaction to erroneous email");
        Assert.assertTrue(test_ok, "Improper reaction to erroneous email address.See screenshot.");
    }

}