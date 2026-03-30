package praktikum.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;

@Feature("Профиль пользователя")
public class ProfilePage {

    //@FindBy(xpath = "//h1[contains(@class, 'h1') and contains(text(), 'Мой профиль')]")
    @FindBy(xpath = "//h1[contains(@class, 'h1') and contains(text(), 'Мой профиль')]")
    private SelenideElement profileTitle;

    //@FindBy(xpath = "//div[@class='grid_threeColumns__ldn5D']//div[@class='about']//h2[@class='h2']")
    @FindBy(xpath = "//div[contains(@class, 'about')]//h2[contains(@class, 'h2')]")
    //@FindBy(xpath = "//div[contains(@class, 'grid_threeColumns')]//div[contains(@class, 'about')]//h2[contains(@class, 'h2')]")
    private SelenideElement titleAd;

    //@FindBy(xpath = "//div[@class='grid_threeColumns__ldn5D']//div[@class='price']//h2[@class='h2']")
    @FindBy(xpath = "//div[contains(@class, 'price')]//h2[contains(@class, 'h2')]")
    private SelenideElement priceAd;

    @FindBy(xpath = "//button[@class='editButton' and @type='button']")
    private SelenideElement buttonUpdateAd;

    public ProfilePage() {
        page(this);
    }

    @Step("Проверяем, что заголовок объявления видим и содержит текст '{expectedTitle}'")
    public void getTitleAd(String expectedTitle) {
        titleAd.shouldBe(visible).shouldHave(text(expectedTitle));
    }

    @Step("Проверяем, что цена объявления видна и содержит значение '{expectedPrice}'")
    public void getPriceAd(String expectedPrice) {
        priceAd.shouldBe(visible).shouldHave(partialText(expectedPrice));
    }

    @Step("Нажимаем кнопку редактирования объявления")
    public void clickButtonUpdateAd() {
        buttonUpdateAd.click();
    }

    @Step("Проверяем заголовок профиля")
    public void getProfileTitle(String expectedTitle) {
         profileTitle.shouldBe(visible).shouldHave(text(expectedTitle));
    }
}
