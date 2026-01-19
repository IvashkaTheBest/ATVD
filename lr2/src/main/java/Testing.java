import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Testing {

    private WebDriver chromeDriver;

    private static final String baseUrl = "https://uk.wikipedia.org/wiki/%D0%93%D0%BE%D0%BB%D0%BE%D0%B2%D0%BD%D0%B0_%D1%81%D1%82%D0%BE%D1%80%D1%96%D0%BD%D0%BA%D0%B0";

    @BeforeClass(alwaysRun = true)
    public void setUp(){

        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();


        chromeOptions.addArguments("--start-fullscreen");

        //щоб стара версія селеніума працювала
        chromeOptions.addArguments("--remote-allow-origins=*");

        this.chromeDriver = new ChromeDriver(chromeOptions);
        this.chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @BeforeMethod
    public void preconditions(){
        chromeDriver.get(baseUrl);
    }

    @AfterClass
    public void tearDown(){
        chromeDriver.quit();
    }

    @Test
    public void testWikipediaSearch() {

        //пошук інпута по назві search
        WebElement searchInput = chromeDriver.findElement(By.xpath("//input[@name='search']"));
        Assert.assertNotNull(searchInput);

        // введення назви альбому групи Deftones
        searchInput.sendKeys("Around the Fur");

        //перевіряю чи ввівся текст
        String typedText = searchInput.getAttribute("value");
        Assert.assertEquals(typedText, "Around the Fur", "Текст у полі введення не співпадає з уведеним");

        //трохи почекати треба щоб завершився динамічний пошук, який онволює DOM і не дає знайти кнопку
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //знаходжу кнопку пошук та клікаю по ній
        WebElement searchButton = chromeDriver.findElement(By.className("cdx-search-input__end-button"));
        searchButton.click();

        //знаходжу чи є в заголовку сторінки слово з назви альбому
        WebElement articleHeader = chromeDriver.findElement(By.id("firstHeading"));
        String headerText = articleHeader.getText();
        Assert.assertTrue(headerText.contains("Fur"), "Заголовок не містить слово Fur");

        System.out.println("Тест успішний!");
    }
}

