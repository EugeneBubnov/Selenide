package SimpleUI;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class ProductsPage {
    //Создадим переменную для подсчёта суммы купленных товаров, счётчик корзины и цену.
    protected Float TotalPriceGrid = 0.00F;
    protected Integer CartCounter = 0;
    protected String PriceFromGrid;
    protected String NameFromGrid;

    SelenideElement cart_counter = $(By.xpath("//span[@class='shopping_cart_badge']"));

    //Получаем список товаров в массиве
    ElementsCollection ProductsList = $$(By.xpath("//div[@data-test='inventory-list']/div"));

    @Step("Выбрать продукт")
    public CartPage SelectProduct(Integer id) {
        for (int i = 1; i <= ProductsList.size(); i++) {
            //Проверка на пустой список.
            if (ProductsList.isEmpty() || id > ProductsList.size()) {
                throw new RuntimeException("Список продуктов пустой, либо выбран несуществующий id");
            } else {
                SelenideElement element = $(By.xpath("(//div[@class='inventory_item_name '])[" + i + "]"));
                SelenideElement price = $(By.xpath("(//div[@data-test='inventory-list']/div)[" + i + "]" +
                        "//div[@class='inventory_item_price']"));
                SelenideElement add_button = $(By.xpath("(//div[@data-test='inventory-item-description']" +
                        "/div//following-sibling::button[.='Add to cart'])[" + i + "]"));
                if (id == i) {
                    //Логируем в консоль, то что выбрали и цену.
                    System.out.println("Выбран товар: " + element.getText() +
                            "\nЦена: " + price.getText() + "\n");
                    add_button.click();
                    CartCounter += 1;
                    //Далее проверяем счётчик у значка корзины
                    if ((cart_counter.getText().equals(CartCounter.toString()))) {
                        System.out.println("Счётчик отработал успешно.");
                    } else {
                        throw new RuntimeException("Счётчик не сработал.");
                    }
                }
            }
        }
        return page(CartPage.class);
    }
}
