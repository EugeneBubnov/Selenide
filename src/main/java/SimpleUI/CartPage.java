package SimpleUI;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;

public class CartPage extends ProductsPage {
    //Создаём переменные для сравнения товара, который был добавлен в корзину из общего списка.
    String NameFromCart;
    String PriceFromCart;
    Float TotalPriceCart;
    String LastUrl = "https://www.saucedemo.com/inventory.html";
    private final SelenideElement cart_product_name = $(By.xpath("//div[@class='inventory_item_name']"));
    private final SelenideElement cart_product_price = $(By.xpath("//div[@class='inventory_item_price']"));
    private final SelenideElement checkout_button = $(By.xpath("//button[@name='checkout']"));

    //По единому id спарсим данные для дальнейшего сравнения цены и названия.
    public CartPage Checkout(Integer id) {
        SelenideElement element = $(By.xpath("(//div[@class='inventory_item_name '])[" + id + "]"));
        SelenideElement price = $(By.xpath("(//div[@data-test='inventory-list']/div)[" + id + "]" +
                "//div[@class='inventory_item_price']"));
        /*
        Получаем локатор цены и забираем из него значение, убирая знак доллара(+ преобразуем во Float.)
        Далее название выбранного товара из списка.
        И перейдём в корзину.
        */
        PriceFromGrid = price.getText().replace("$", "");
        TotalPriceGrid = Float.valueOf(PriceFromGrid);
        NameFromGrid = element.getText();
        cart_counter.click();
        /*
        Получаем цену и название товара, далее сравниваем.
         */
        NameFromCart = cart_product_name.getText();
        PriceFromCart = cart_product_price.getText().replace("$", "");
        TotalPriceCart = Float.valueOf(PriceFromCart);
        /*
        Если всё окей, то продолжим путь покупателя,
        если не окей, то бросит соответствующее исключение.
         */
        if (TotalPriceGrid.equals(TotalPriceCart) && NameFromGrid.equals(NameFromCart)) {
            System.out.println("good");
            checkout_button.click();
        } else {
            throw new RuntimeException("Данные из корзины не совпадают с заказом.");
        }

        System.out.println(TotalPriceGrid);
        cart_counter.click();

        //Поскольку действия остаются в рамках оформления заказа, оставляю тест в текущем классе.
        return this;
    }

    private final SelenideElement first_name = $(By.xpath("//input[@name='firstName']"));
    private final SelenideElement last_name = $(By.xpath("//input[@name='lastName']"));
    private final SelenideElement postal_code = $(By.xpath("//input[@name='postalCode']"));
    private final SelenideElement continue_button = $(By.xpath("//input[@name='continue']"));

    public CartPage UserInfo(String FirstName, String LastName, String PostalCode) {
        first_name.sendKeys(FirstName);
        last_name.sendKeys(LastName);
        postal_code.sendKeys(PostalCode);
        continue_button.click();
        return this;
    }

    private final SelenideElement finish_button = $(By.xpath("//button[@name='finish']"));

    public CartPage Overview() {
        /*
        Локаторы одни и те же на страницах, используем магию копипасты для наших 2 полей(если в ТЗ их больше,
        то можно и все проверить)
        Получаем цену и название товара, далее сравниваем.
         */
        NameFromCart = cart_product_name.getText();
        PriceFromCart = cart_product_price.getText().replace("$", "");
        TotalPriceCart = Float.valueOf(PriceFromCart);
        /*
        Если всё окей, то продолжим путь покупателя,
        если не окей, то бросит соответствующее исключение.
         */
        if (TotalPriceGrid.equals(TotalPriceCart) && NameFromGrid.equals(NameFromCart)) {
            System.out.println("good");
            finish_button.click();
        } else {
            throw new RuntimeException("Данные из корзины не совпадают с заказом.");
        }
        return this;
    }

    private final SelenideElement img_pony = $(By.xpath("//img[@class='pony_express']"));
    private final SelenideElement ty = $(By.xpath("//h2[.='Thank you for your order!']"));
    private final SelenideElement order_info = $(By.xpath("//div[.='Your order has been dispatched, and will arrive just as fast as the pony can get there!']"));
    private final SelenideElement empty_cart = $(By.xpath("//a[@class='shopping_cart_link']"));
    private final SelenideElement home_button = $(By.xpath("//button[.='Back Home']"));

    //Последний метод можно сделать void, поскольку тест заканчивается и возвращать ничего не нужно.
    public void Completed(WebDriver driver) {
        /*
        В данном методе проверяем наличие картинки и информационных сообщений о заказе.
        И на последок сверям текущий урл с условно "правильным".
        */
        img_pony.hover().isDisplayed();
        ty.hover().isDisplayed();
        order_info.hover().isDisplayed();
        empty_cart.is(Condition.empty);

        home_button.click();
        String CurrentUrl = driver.getCurrentUrl();
        if (LastUrl.equals(CurrentUrl)) {
            System.out.println("Exactly");
        } else {
            throw new RuntimeException("После клика по кнопке Finish не последовало редиректа," +
                    " свяжитесь с разработчиком");
        }
    }
}
