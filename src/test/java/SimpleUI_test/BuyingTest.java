package SimpleUI_test;

import SimpleUI.LoginPage;
import Support.AdditionalLogger;
import Support.TestExention;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;

import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static org.openqa.selenium.OutputType.BYTES;

public class BuyingTest {
    /*
    Предустановки для успешного прохождения теста и генерации Allure отчёта.
    Также используем библиотеку Faker для рандомных данных.
     */
    WebDriver driver;
    Integer ProductId = 1;
    Faker faker = new Faker();
    String RandomName = faker.name().firstName();
    String RandomSurname = faker.name().lastName();
    String RandomAddress = faker.address().fullAddress();
    final String BASE_URL = "https://www.saucedemo.com/";

    @RegisterExtension
    TestExention watcher = new TestExention();

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("src/main/resources/extension_1_2_12_0.crx"));
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("safebrowsing.enabled", true);
        options.setExperimentalOption("prefs", chromePrefs);

        driver = new EventFiringDecorator(new AdditionalLogger()).decorate(new ChromeDriver(options));

        setWebDriver(driver);
        Configuration.timeout = 15000;
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @Test
    @Epic("Swag Labs")
    @Feature("Shopping")
    @DisplayName("Buying clothes")
    public void BuyingTShorts() throws AWTException, InterruptedException {
        /*
        При помощи паттерна проектирования Builder собираем тест.
        В процессе выполнения теста, отработавшие классы будут
        возвращать нужный/следующий класс(в конце метода установлен return page(Нужный_класс.class)).
        p.s. В методе SelectProduct нужно выбрать id от 1 до 6, установлена валидация.
         */
        new LoginPage()
                .SignIn()
                .SelectProduct(ProductId)
                .Checkout(ProductId)
                .UserInfo(RandomName, RandomSurname, RandomAddress)
                .Overview()
                .Completed(driver);
    }

    /*
    После прохождения теста driver будет выключен.
    Также установлены пост-настройки для генерации отчёта и если тест упадёт, то мы получим логи из консоли браузера
    и скриншот(на каком шаге упал тест).
     */
    @AfterEach
    void killBrowser() {
        watcher.setScreenStream(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(BYTES)));
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);

        for (LogEntry log : logEntries) {
            Allure.addAttachment("Логи из консоли браузера", log.getMessage());
        }
        driver.quit();
    }
}
