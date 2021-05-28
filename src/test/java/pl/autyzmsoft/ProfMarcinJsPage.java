package pl.autyzmsoft;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProfMarcinJsPage {
    private WebDriver driver;

    public ProfMarcinJsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "klawisz")
    private List<WebElement> klawiszePM;

    @FindBy(id = "pctArea")
    private WebElement picturePM;

    @FindBy(id = "hintArea")
    private WebElement txtUnderPicturePM;

    @FindBy(id = "bDalej")
    private WebElement greenButtonPM;


    /***
     * Returns buttons with words as generated on ProfMarcin screen
     */
    public List<WebElement> getButtonsList() {
        return klawiszePM;
    }

    /***
     * Probing the picture until it becomes visible (==no longer of background color)<br>
     * May take some time;<br>When timeout - returns false
     */
    public boolean isPictureVisible() {
        boolean isVisible = false;
        final int MAX_PROBES = 10; //to prevent infinite loop
        int probe = 0;
        //Waiting for the picture to change its style to become visible:
        while (!isVisible && (probe<MAX_PROBES)) {
            TestUtils.mySleep(300);
            String style = picturePM.getAttribute("style");
            //have it really changed its style?:
            isVisible = !(style.contains(("tlo.webp")));
            probe++;
        }
        return isVisible;
    }

    /***
     * Returns the name of the picture (which is the word to be guessed)
     */
    public String getWordDescribingThePicture() {
        //First, we have to wait for the picture to appear, before that there is only a picture with background color
        if (this.isPictureVisible()) {
            String style = picturePM.getAttribute("style");
            //example of the style above: background-image: url("zasoby/jabłko.webp");
            String word = style.substring(style.indexOf("/") + 1, style.indexOf("."));
            return word;
        }
        return null;
    }

    public String getWordUnderPicture() {
        return txtUnderPicturePM.getText();
    }

    public WebElement getGreenButton() {
        return greenButtonPM;
    }
}








