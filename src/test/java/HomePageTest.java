import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import pl.autyzmsoft.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePageTest extends BaseTest {

    //HP_01
    @Test
    public void testDownloadPageAppears() {
        homePage.gotoDownloadPage();
        WebElement pageTitle = driver.findElement(By.xpath(TestUtils.PAGE_TITLES_LOCATION));
        String pageTitleText = pageTitle.getText();
        boolean testOK = pageTitleText.equals("DO POBRANIA");
        Assert.assertTrue(testOK, "Download Page did not appear or wrong Download Page Title!");
    }

    //HP_02
    @Test
    public void testFullVersionsPageAppears() {
        homePage.gotoFullVersionsPage();
        WebElement pageTitle = driver.findElement(By.xpath(TestUtils.PAGE_TITLES_LOCATION));
        String pageTitleText = pageTitle.getText();
        boolean testOK = pageTitleText.equals("PEŁNE WERSJE");
        Assert.assertTrue(testOK, "Full Versions Page did not appear or wrong Download Page Title!");
    }

    //HP_03
    /**
    * Checking whether all links on the Home Page are active.
    */
    @Test
    public void testAllLinksAreActive() {
        List<String> clickableLinks = homePage.getClickableLinks();
        boolean testOk = true;
        List<String> inactiveLinks = new ArrayList<>();
        for (String urlStr : clickableLinks) {
            boolean statusOk = TestUtils.getLinkStatus(urlStr);
            if (!statusOk) {
                testOk = false;
                inactiveLinks.add(urlStr);
            }
        }
        Assert.assertTrue(testOk,"Inactive links on Home Page detected:"+inactiveLinks.toString());
    }

    //HP_04
    /***
     * Test whether javascript application LiczyKropka opens
     * Passed if:
     * 1. 5 buttons appear AND
     * 2. a big number appears
     */
    @Test
    public void testLiczykropkaJsOpens(){
        final int NUM_BUTTONS = 5;  //how many buttons should appear

        liczykropkaJsPage = homePage.goToLiczykropkaJs();

        //Testing condition No 1:
        boolean test_1_ok = false;
        try {
            int num = liczykropkaJsPage.getButtonsList().size();
            test_1_ok = (num == NUM_BUTTONS);
        } catch (Exception e) {
            Reporter.log(e.getMessage());
        }

        //Testing condition No 2:
        int number = liczykropkaJsPage.getNumber();
        boolean test_2_ok = (number != -1);

        //Determining the reason of negative test (if any):
        List<String> reasons = new ArrayList<>();
        if (!test_1_ok) reasons.add("Number of buttons different than "+NUM_BUTTONS+" or buttons did not appear.");
        if (!test_2_ok) reasons.add("Big number did not appear on the screen or it is invalid.");

        boolean test_ok = (test_1_ok && test_2_ok);

        if (!test_ok) {
            TestUtils.screenShot(driver, "testLiczykropkaJsOpens");
        }

        Assert.assertTrue(test_ok, "Error in liczykropka.js "+reasons.toString());
    }

    //HP_05
    /***
     * What happens after we clicked the correct button in Liczykropka js application?
     * Passed if:
     * 1. All buttons with numbers except the proper one(s) are disabled AND
     * 2. Big green button with @ sign appears
     */

    //HP_05
    @Test(dependsOnMethods = {"testLiczykropkaJsOpens"},dataProviderClass = TestsData.class, dataProvider = "multiplayer")
    public void testClickingCorrectButtonInLiczykropkaJs(String[] parameters) {
        System.out.println(parameters[0]+" "+parameters[1]);
        liczykropkaJsPage = homePage.goToLiczykropkaJs();
        new WebDriverWait(driver, TestUtils.WAIT_TIME).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("klawisz-klasa")));
        //Clicking proper button:
        int number = liczykropkaJsPage.getNumber();
        if (number == -1)
            throw new ProblemWithNumberException("Problem with number to be guessed!");
        //Have to wait till buttons are visible so they can be clicked (to be present is not enough...):
        TestUtils.mySleep(3000);
        WebElement properBtn = liczykropkaJsPage.getButtonWithNumber(number);
        if (properBtn == null)
            throw new NoProperButtonException("Proper button could not be found!");
        properBtn.click();
        TestUtils.mySleep(1000); //unnecessary but for better visual effect ;)

        //Testing condition No 1:
        boolean test_1_ok = true;
        List<WebElement> btnsList = liczykropkaJsPage.getButtonsList();
        for (WebElement btn : btnsList) {
            int bValue = Integer.parseInt(btn.getAttribute("wartosc"));
            if (bValue != number) {
                if ( btn.getAttribute("disabled").equals("false") ) {
                    test_1_ok = false;
                    break;
                }
            }
            if (bValue == number) {
                if ( btn.getAttribute("disabled") != null ) {
                    test_1_ok = false;
                    break;
                }
            }
        }

        //Testing condition No 2:
        WebElement greenBtn = liczykropkaJsPage.getGreenButton();
        String style = greenBtn.getAttribute("style");
        boolean test_2_ok = greenBtn.isDisplayed();

        //Determining the reason(s) of negative test (if any):
        List<String> reasons = new ArrayList<>();
        if (!test_1_ok) reasons.add("Buttons improperly disabled.");
        if (!test_2_ok) reasons.add("Green button did not appear.");

        boolean test_ok = test_1_ok && test_2_ok;

        if (!test_ok)
            TestUtils.screenShot(driver, "testClickingCorrectButtonInLiczykropkaJS");

        Assert.assertTrue(test_ok, "Test: testClickingCorrectButtonInLiczykropkaJS(): Errors detected: "+reasons.toString()+" See screenshot.");
    }

    //HP_06
    /***
     * Test whether javascript application profMarcin opens
     * Passed if:
     * 1. 4 buttons appear AND
     * 2. a picture appear
     */
    @Test
    public void testProfMarcinJsOpens(){
        final int NUM_BUTTONS = 4;  //how many buttons should appear

        profMarcinJsPage = homePage.goToProfMarcinJs();

        //Testing condition No 1:
        boolean test_1_ok = false;
        try {
            int num = profMarcinJsPage.getButtonsList().size();
            test_1_ok = (num == NUM_BUTTONS);
        } catch (Exception e) {
            Reporter.log(e.getMessage());
        }

        //Testing condition No 2:
        boolean test_2_ok = profMarcinJsPage.isPictureVisible();

        //Determining the reason of negative test (if any):
        List<String> reasons = new ArrayList<>();
        if (!test_1_ok) reasons.add("Number of buttons different than "+NUM_BUTTONS+" or buttons did not appear.");
        if (!test_2_ok) reasons.add("The picture did not appear");

        boolean test_ok = (test_1_ok && test_2_ok);

        if (!test_ok) {
            TestUtils.screenShot(driver, "testProfMarcinJsOpens");
        }

        Assert.assertTrue(test_ok, "Error in profMarcin.js "+reasons.toString()+" See screenshot.");
    }

    //HP_07
    /***
     * What happens after we clicked the correct button in profMarcin js application?
     * Passed if:
     * 1. All texts on buttons with improper words are printed in 'font-weigh: normal'; text on button(s) with proper
     *    word are printed in font-size > 100% AND
     * 2. There appear a text element under the picture. The element contains proper word AND
     * 3. Big green button with right arrow appears
     */
    @Test(dependsOnMethods = {"testProfMarcinJsOpens"}, dataProviderClass = TestsData.class, dataProvider = "multiplayer")
    public void testClickingCorrectButtonInProfmarcinJs(String[] parameters) {
        System.out.println(parameters[0]+" "+parameters[1]);
        profMarcinJsPage = homePage.goToProfMarcinJs();
        //Getting the picture name (== the correct word on the buttons)
        String word = profMarcinJsPage.getWordDescribingThePicture();

        TestUtils.mySleep(2000); //unnecessary, but for better visual effect ;)

        //Clicking on the button containing 'word':
        List<WebElement> btnsList = profMarcinJsPage.getButtonsList();
        for (WebElement btn : btnsList) {
            if (btn.getText().equals(word)) {
                btn.click();
                break;
            }
        }

        TestUtils.mySleep(2000); //unnecessary, but for better visual effect ;)

        //Testing condition No 1:
        boolean test_1_ok = true;
        for (WebElement btn : btnsList) {
            String style = btn.getAttribute("style").replaceAll("\\s","");
            if (!btn.getText().equals(word)) {
                if (!style.contains("font-weight:normal")) {
                    test_1_ok = false;
                    break;
                }
            }
            if (btn.getText().equals(word)) {
                if (!TestUtils.isFontSizeGreaterThan(style, 100)) {
                    test_1_ok = false;
                    break;
                }
            }
        }
        //Testing condition No 2:
        //text under the picture is always present, only its content changes from "" to actual word, so no need to try-catch :)
        String wordUnderPicture = profMarcinJsPage.getWordUnderPicture();
        boolean test_2_ok = wordUnderPicture.equals(word);

        //Testing condition No 3:
        //green button is always present, only its style changes, so no need to try-catch :)
        WebElement greenBtn = profMarcinJsPage.getGreenButton();
        String style = greenBtn.getAttribute("style");
        boolean test_3_ok = !style.contains("transparent");

        //Determining the reason(s) of negative test (if any):
        List<String> reasons = new ArrayList<>();
        if (!test_1_ok) reasons.add("Buttons improperly blocked.");
        if (!test_2_ok) reasons.add("Improper word or no word under the picture.");
        if (!test_2_ok) reasons.add("Green button did not appear.");

        boolean test_ok = (test_1_ok && test_2_ok && test_3_ok);

        if (!test_ok) {
            TestUtils.screenShot(driver, "testClickingCorrectButtonInProfmarcinJs");
        }

        Assert.assertTrue(test_ok, "Test: test_clicking_correct_button_in_profmarcin_js(): Errors detected:\\"
                + reasons.toString() + " See screenshot.");
    }

    private class NoProperButtonException extends RuntimeException {
        public NoProperButtonException(String msg) {
            super(msg);
        }
    }

    private class ProblemWithNumberException extends RuntimeException {
        public ProblemWithNumberException(String msg) {
            super(msg);
        }
    }


}
