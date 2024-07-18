package SimpleUI;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

//Страница авторизации, данные для входа спарсим при помощи xPath
public class LoginPage {
    //Входные данные
    String Login = "standard_user";
    String Password = "secret_sauce";

    //xPath-локаторы, по которым будем постепенно передвигаться и взаимодействовать с сайтом.
    private final SelenideElement login_field = $(By.xpath("//input[@id='user-name']"));
    private final SelenideElement pass_field = $(By.xpath("//input[@id='password']"));
    private final SelenideElement login_button = $(By.xpath("//input[@id='login-button']"));

    @Step("Залогиниться с существующими учётными данными")
    public ProductsPage SignIn() throws InterruptedException, AWTException {
        login_field.sendKeys(Login);
        pass_field.sendKeys(Password);
        login_button.click();
        /*
        Даём подгрузиться алерту.
        Далее запускаем робота и нажимаем Enter, чтобы убрать алерт с экрана.
        */
        Thread.sleep(Duration.ofSeconds(2));
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        //Возвращаем следующий класс по паттерну проектирования PageObject.
        return page(ProductsPage.class);
    }
}
