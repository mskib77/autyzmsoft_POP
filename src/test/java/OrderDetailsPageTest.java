import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.autyzmsoft.OrderDetailsPage;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class OrderDetailsPageTest extends BaseTest {

    /***
     * Causes Order Details (Szczególy Zamówienia) page to appear on screen before each test.
     * In order to do so a "mock" order is made on Full Versions page. The order
     * is made by randomly choosing a few product checkboxes.
     */
    @BeforeMethod
    private void bringUpOrderDetailsPage() {
        fullVersionsPage = homePage.gotoFullVersionsPage();
        //choosing random number of a few checkboxes to check/click, then clicking them:
        int toClick = new Random().nextInt(5)+1;
        clickRandomCheckboxes(toClick);
        //this will bring Order Details page on screen:
        fullVersionsPage.getOrderButton2().click();
        //creating the model of the Order Detail page:
        orderDetailsPage = new OrderDetailsPage(driver);
    }

    /***
     * Test whether the total amount from Order Details page is correctly passed to DotPay system
     * Passed if both amounts are equal.
     * ddt is used to enforce running the test more than once.
     */
    @Test(dataProviderClass = TestsData.class, dataProvider = "multiplayer")
    public void testIsAmountCorrectlyPassed(String[] run) {
        final String CORRECT_EMAIL = "mskib77@gmail.com";
        int total = orderDetailsPage.getTotal();
        orderDetailsPage.fillEmails(CORRECT_EMAIL);
        dotPayPage = orderDetailsPage.clickBuyButton();
        int amount = dotPayPage.getAmount();
        boolean test_ok = (amount == total);
        Assert.assertTrue(test_ok,"Amount passed from Order Details page ("+total+") does not equal the amount on Dotpay ("+amount+")");
    }

    /***
     * Auxiliary. Checks/clicks random checkboxes.
     * @param howMany: how many checkboxes to click
     */
    private void clickRandomCheckboxes(int howMany) {
        List<WebElement> cbList = fullVersionsPage.getAllCheckboxesList();
        int maxVal = cbList.size();
        //Parameter sanitization:
        if (howMany<1 || howMany>maxVal)
            throw new IllegalArgumentException("parameter how many out of range ("+howMany+") Should be 1.."+maxVal);
        Random gen = new Random();
        HashSet<Integer> setOfChosen = new HashSet<>();
        int counter = 0;
        //Ensuring that all generated int are different:
        while (counter < howMany) {
            int rnd = gen.nextInt(maxVal);
            if (!setOfChosen.contains(rnd)) {
                setOfChosen.add(rnd);
                counter++;
            }
        }
        //Clicking the chosen checkboxes;
        for (Integer rnd : setOfChosen) {
            cbList.get(rnd).click();
        }
    }


}
