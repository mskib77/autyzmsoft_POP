package pl.autyzmsoft;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.commons.validator.routines.UrlValidator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(partialLinkText = "Pobieranie")
    private WebElement doPobraniaLink;

    @FindBy(partialLinkText = "PEŁNE WERSJE")
    private WebElement pelneWersjeLink;

    @FindBy(id = "catapultCookie")
    private WebElement cookieBtn;

    @FindBy(tagName = "a")
    private List<WebElement> links;

    @FindBy(tagName = "img")
    private List<WebElement> images;

    // Links to start settings screens of some Java Script application:

    @FindBy(linkText = "wersje online")    //to hover over to uncover ;)
    private WebElement wersjeOnline;

    @FindBy(linkText = "prof.Marcin")  //to start prof.Marcin settings page
    private WebElement profMarcin;

    @FindBy(linkText = "LiczyKropka") //to start LiczyKropka settings page
    private WebElement liczyKropka;

    /* ---end of -- Links to start settings screens ------------ */


    public void dismissCookies() {
        cookieBtn.click();
    }

    public DownloadPage gotoDownloadPage() {
        doPobraniaLink.click();
        return new DownloadPage(driver);
    }

    public FullVersionsPage gotoFullVersionsPage() {
        pelneWersjeLink.click();
        return new FullVersionsPage(driver);
    }

    /***
     * Starting Liczykropka js application
     */
    public LiczykropkaJsPage goToLiczykropkaJs() {
       this.startJsAppSettingsScreen(liczyKropka);
       driver.findElement(By.id("bStartuj")).click();
       return new LiczykropkaJsPage(driver);
    }

    /***
     * Starting profMarcin js application
     */
    public ProfMarcinJsPage goToProfMarcinJs() {
        this.startJsAppSettingsScreen(profMarcin);
        driver.findElement(By.id("b_start_id")).click();
        return new ProfMarcinJsPage(driver);
    }

    /***
     * Opens the first screen of Java Script app given in the @linkToApp parameter
     * Used to start the js applications from this screen by finding and clicking the button "Startuj"
     * @param linkToApp
     */
    private void startJsAppSettingsScreen(WebElement linkToApp) {
        this.hoverOver(wersjeOnline);   //uncovering menu items
        this.hoverOver(linkToApp);      //unnecessary, but better visual effect ;)
        //Page needs to be reloaded, otherwise selenium can't locate elements:
        String url = linkToApp.getAttribute("href");
        this.driver.get(url);
    }

    private void hoverOver(WebElement linkToHover) {
          Actions action = new Actions(driver);
          action.moveToElement(linkToHover).perform();
    }

    /**
     * Returns String list of urls found on Home Page
     */
    public List<String> getClickableLinks() {
        List<String> linksToClick = new ArrayList<String>();

        List<WebElement> allLinks = new ArrayList<>();
        allLinks.addAll(links);
        //images can also be links, so we add them:
        allLinks.addAll(images);
        //getting 'real' links only:
        UrlValidator urlValidator = new UrlValidator();
        for (WebElement e : allLinks) {
            if (e.getAttribute("href") != null) {
                String urlStr = e.getAttribute("href");
                if (urlValidator.isValid(urlStr)) {  //only syntax; filtering out a few 'strange' urls + javascripts etc. ;)
                    linksToClick.add(urlStr);
                }
            }
        }

        //Duplicates removal:
        Set<String> set = new HashSet<>(linksToClick);
        linksToClick.clear();
        linksToClick.addAll(set);
        //
        return linksToClick;
    }

}
