package praktikum.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

@Feature("Функционал главной страницы")
public class MainPage {

    @FindBy(xpath = "//button[text()='Вход и регистрация']")
    private SelenideElement buttonLogin;

    @FindBy(xpath = "//button[normalize-space(text())='Выйти']")
    private SelenideElement buttonExit;

    @FindBy(xpath = "//button[@class='circleSmall']")
    private SelenideElement buttonProfile;

    @FindBy(xpath = "//button[normalize-space(text())='Разместить объявление']")
    private SelenideElement buttonAd;

    @FindBy(xpath = "//input[@name='name' and @type='text']")
    private SelenideElement inputSearch;

    @FindBy(xpath = "//button[normalize-space(text())='Удалить']")
    private SelenideElement buttonDeleteAd;

    @FindBy(xpath = "//div[@class='card']")
    private SelenideElement cardAd;

    public MainPage() {
        page(this);
    }

    @Step("Переход к форме входа и регистрации")
    public void clickButtonLogin() {
        buttonLogin.click();
    }

    @Step("Проверка наличия кнопки выхода из профиля")
    public void getButtonExitVisible(String expectedText) {
        buttonExit.shouldBe(visible).shouldHave(text(expectedText));
    }

    @Step("Проверка отображения иконки профиля")
    public boolean isButtonProfileVisible() {
        return buttonProfile.is(visible);
    }

    @Step("Нажатие кнопки 'Разместить объявление'")
    public void clickButtonAd() {
        buttonAd.click();
    }

    @Step("Кликаем по иконке профиля")
    public void clickButtonProfile() {
        buttonProfile.click();
    }

    @Step("Вводим запрос в поле поиска и нажимаем Enter")
    public void searchAd(String name) {
        inputSearch.setValue(name);
        inputSearch.pressEnter();
    }

    @Step("Кликаем по карточке объявления")
    public void clickCardAd() {
        cardAd.click();
    }

    @Step("Нажимаем кнопку 'Удалить' на странице объявления")
    public void clickButtonDeleteAd() {
        buttonDeleteAd.click();
    }

    @Step("Удаляем объявление")
    public void deleteAd(String name) {
        searchAd(name);
        clickCardAd();
        clickButtonDeleteAd();
    }
}
