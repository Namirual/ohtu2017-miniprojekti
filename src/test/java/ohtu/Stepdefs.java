package ohtu;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import java.util.List;
import ohtu.controller.KirjaVinkkiController;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Stepdefs {

    WebDriver driver;
    KirjaVinkkiController kontrol;

    public Stepdefs() {
        File file;
        if (System.getProperty("os.name").matches("Mac OS X")) {
            file = new File("lib/macgeckodriver");
        } else {
            file = new File("lib/geckodriver");
        }
        String absolutePath = file.getAbsolutePath();
        System.setProperty("webdriver.gecko.driver", absolutePath);
        this.driver = new FirefoxDriver();
        kontrol = new KirjaVinkkiController();

    }

    @Given("^user is sellected command heakirjat$")
    public void user_is_sellected_command_heakirjat() throws Throwable {
        driver.get("http://localhost:8080");
        WebElement element = driver.findElement(By.partialLinkText("listaa kirjavinkit"));
        element.click();
        Thread.sleep(500);

    }

    @Given("^command lisaakirja is selected$")
    public void command_lisaakirja_is_selected() throws Throwable {
        driver.get("http://localhost:8080");
        WebElement element = driver.findElement(By.partialLinkText("lisaa kirja"));
        element.click();
        Thread.sleep(200);
    }

    @Given("^user has selected command Muokkaa kirjaa$")
    public void command_muokkaakirjaa_is_selected() throws Throwable {
        driver.get("http://localhost:8080/vinkit");
        WebElement element = driver.findElement(By.partialLinkText("muokkaa"));
        element.click();
        Thread.sleep(200);
    }

    @Given("^user tries to edit a non-existing book$")
    public void edit_non_existing_book() throws Throwable {
        driver.get("http://localhost:8080/99999/muokkaa");
    }

    @Given("^command merkitseluetuksi is selected$")
    public void mark_hint_to_read() throws Throwable {
        driver.get("http://localhost:8080/vinkit");
        WebElement element = driver.findElement(By.partialLinkText("merkitse luetuksi"));
        element.click();
        Thread.sleep(200);
    }
    
    @Given("^command merkitselukemattomaksi is selected$")
    public void mark_hint_to_unread() throws Throwable {
        driver.get("http://localhost:8080/vinkit");
        WebElement element = driver.findElement(By.partialLinkText("merkitse lukemattomaksi"));
        element.click();
        Thread.sleep(200);
    }

    @When("^user has entered an writer \"([^\"]*)\" and title \"([^\"]*)\"$")
    public void when_user_has_entered_an_writer_and_title(String writer, String tittle) throws Throwable {
        Thread.sleep(200);
        WebElement element = driver.findElement(By.name("kirjoittaja"));
        element.sendKeys(writer);
        Thread.sleep(200);
        element = driver.findElement(By.name("otsikko"));
        element.sendKeys(tittle);
        Thread.sleep(200);
        element = driver.findElement(By.cssSelector("input[type='submit']"));
        element.click();
        Thread.sleep(500);
    }

    @When("^user has selected command takaisin$")
    public void user_has_selected_command_takaisin() throws Throwable {
        WebElement element = driver.findElement(By.linkText("Takaisin"));
        element.click();
    }

    @When("^page has list of all books and command Takaisin sivulle is selected$")
    public void all_existing_books_are_listed() throws Throwable {
        pageHasContent("Lisätyt lukuvinkit");
        WebElement element = driver.findElement(By.id("listId"));
        List<WebElement> kirjaLista = driver.findElements(By.tagName("li"));
        element = driver.findElement(By.linkText("Takaisin paasivulle"));
        element.click();

    }

    @Then("^new book is added$")
    public void new_book_is_added() throws Throwable {
        pageHasContent("Lisätty kirja Topologia I kirjoittajalta Jussi Väisälä! ");
    }
    
    @Then("^new book is not added and error is shown$")
    public void new_book_is_not_added() throws Throwable {
        pageHasContent("Kirjan nimi tai kirjailija ei voi olla tyhjä!");
    }

    @Then("^existing book is modified$")
    public void existing_book_is_modified() throws Throwable {
        pageHasContent("Muokattu kirja");

    }

    @Then("^user is redirect to mainpage$")
    public void user_is_redirect_to_mainpage() throws Throwable {
        Thread.sleep(300);
        WebElement element = driver.findElement(By.partialLinkText("lisaa kirja"));
        element = driver.findElement(By.partialLinkText("listaa kirjavinkit"));

    }

    @Then("^system sent message sent error message$")
    public void system_sent_message_sent_error_message() throws Throwable {
//         pageHasContent("Book Topologia I from writer Jussi Väisälä exist already in database");
    }

    @Then("^user will end up on the error page$")
    public void end_up_on_the_error_page() throws Throwable {
        pageHasContent("Tapahtui virhe");
    }

    @Then("^user return to brevious page$")
    public void user_can_return_brevious_page() throws Throwable {
        Thread.sleep(500);
        WebElement element = driver.findElement(By.partialLinkText("lisaa kirja"));
        element = driver.findElement(By.partialLinkText("listaa kirjavinkit"));
    }

    @Then("^the hint is marked as read$")
    public void the_hint_is_marked_as_read() throws Throwable {
        pageHasContent("merkitse lukemattomaksi");
    }
    
    @Then("^the hint is marked as unread$")
    public void the_hint_is_marked_as_unread() throws Throwable {
        pageHasContent("merkitse luetuksi");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

//    private void clickLinkWithText(String text) {
//        int trials = 0;
//        while (trials++ < 5) {
//            try {
//                WebElement element = driver.findElement(By.linkText(text));
//                element.click();
//                break;
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            }
//        }
//    }
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }
}
