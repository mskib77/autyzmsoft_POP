import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.autyzmsoft.OrderDetailsPage;
import pl.autyzmsoft.TestUtils;

import java.util.List;

public class OrderDetailsPageTest extends BaseTest {

    @BeforeMethod
    private void bringUpOrderDetailsPage() {
        fullVersionsPage = homePage.gotoFullVersionsPage();
        List<WebElement> cbList = fullVersionsPage.getAllCheckboxesList();
        for (WebElement cb : cbList) {
            cb.click();
        }
        fullVersionsPage.getOrderButton1().click();
        orderDetailsPage = new OrderDetailsPage(driver);
    }

    @Test
    public void dummy() {
        System.out.println("suma:"+orderDetailsPage.getTotal());
        TestUtils.mySleep(3000);
        orderDetailsPage.fillEmails("mskib77@gmail.com");
        TestUtils.mySleep(3000);
        orderDetailsPage.clickBuyButton();
        TestUtils.mySleep(3000);
    }

}
