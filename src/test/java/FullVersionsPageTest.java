import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.autyzmsoft.TestUtils;

public class FullVersionsPageTest extends BaseTest {

    @BeforeMethod
    public void bringUpFullVersionsPage() {
        fullVersionsPage = homePage.gotoFullVersionsPage();
    }

    /***
     * Passed if Alert window appears"""
     * ddt is used as there are 2 buttons that clicking on them should have same effect
     */
    @Test
    public void testClickOrderButtonsWithoutChoosingItems()/*(int btnNo)*/ {
        fullVersionsPage.getOrderButton2().click();
        TestUtils.mySleep(3000);
    }

    /*
       def test_click_order_buttons_without_choosing_items(self, button_number):


        btn = self._determine_button_to_click(button_number)
        btn.click()
        sleep(2)  # unnecessary, for better visual effect ;)

        try:
            WebDriverWait(self.driver, TestUtils.WAIT_TIME).until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            txt = alert.text
            test_ok = TestUtils.ALERT_TEXT.upper() in txt.upper()
            # dismissing the alert (unnecessary) :
            # alert.accept()
        except TimeoutException:
            test_ok = False

        self.assertTrue(test_ok, "Alert does not exist in page!")

     */

}
