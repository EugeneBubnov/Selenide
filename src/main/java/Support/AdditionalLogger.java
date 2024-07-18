package Support;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalLogger implements WebDriverListener {
    private static Logger logger = LoggerFactory.getLogger(AdditionalLogger.class);
    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        Allure.step("Ищем элемент по локатору: " + locator);
    }

//    @Override
//    public void beforeQuit(WebDriver driver) {
//        Allure.addAttachment("Screenshot",
//                new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
//    }
}
