package praktikum.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.page;

@Feature("Авторизация и регистрация")
public class LoginPage {

    @FindBy(xpath = "//div[@class='popUp_titleRow__M7tGg']")
    private SelenideElement title;

    @FindBy(xpath = "//div[@class='input_inputDefault__UmPK0']")
    private SelenideElement inputEmailContainer;

    @FindBy(xpath = "//input[@placeholder='Введите Email']")
    private SelenideElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Пароль']")
    private SelenideElement inputPassword;

    @FindBy(xpath = "//input[@placeholder='Повторите пароль']")
    private SelenideElement inputSubmitPassword;

    @FindBy(xpath = "//button[normalize-space(text())='Войти']")
    private SelenideElement buttonSubmit;

    @FindBy(xpath = "//button[normalize-space(text())='Нет аккаунта']")
    private SelenideElement buttonNoAccount;

    @FindBy(xpath = "//button[normalize-space(text())='Создать аккаунт']")
    private SelenideElement buttonCreateAccount;

    @FindBy(xpath = "//button[normalize-space(text())='Уже есть аккаунт']")
    private SelenideElement buttonAlreadyHaveAccount;

    @FindBy(xpath = "//form[@class='popUp_shell__LuyqR']")
    private SelenideElement formLogin;

    @FindBy(xpath = "//span[normalize-space(text())='Ошибка']")
    private SelenideElement errorMsg;

    public LoginPage() {
        page(this);
    }

    @Step("Ввод email в поле ввода: {email}")
    public void setInputEmail(String email) {
        inputEmail.setValue(email);
    }

    @Step("Ввод пароля в поле ввода")
    public void setInputPassword(String password) {
        inputPassword.setValue(password);
    }

    @Step("Подтверждение пароля в поле подтверждения")
    public void setInputSubmitPassword(String password) {
        inputSubmitPassword.setValue(password);
    }

    @Step("Нажатие кнопки «Войти» для авторизации")
    public void clickButtonSubmit() {
        buttonSubmit.click();
    }

    @Step("Нажатие кнопки «Создать аккаунт» для регистрации")
    public void clickCreateAccount() {
        buttonCreateAccount.click();
    }

    @Step("Нажатие кнопки «Нет аккаунта»")
    public void clickButtonNoAccount() {
        buttonNoAccount.click();
    }

    @Step("Выполнение авторизации с email: {email}")
    @Story("Авторизация пользователя")
    @Description("Полный сценарий авторизации: ввод email и пароля, нажатие кнопки «Войти»")
    public void login(String email, String password) {
        setInputEmail(email);
        setInputPassword(password);
        clickButtonSubmit();
    }

    @Step("Выполнение регистрации с email: {email}")
    @Story("Регистрация нового пользователя")
    @Description("Полный сценарий регистрации: ввод email, пароля и его подтверждения, нажатие кнопки «Создать аккаунт»")
    public void register(String email, String password, String submitPassword) {
        setInputEmail(email);
        setInputPassword(password);
        setInputSubmitPassword(submitPassword);
        clickCreateAccount();
    }

    @Step("Ожидание видимости заголовка страницы")
    public void waitForPageToLoad() {
        title.shouldBe(visible);
    }

    @Step("Получение текста заголовка формы")
    public void getTitle(String expectedTitle) {
        title.shouldBe(visible).shouldHave(text(expectedTitle));
    }

    @Step("Проверка, что форма входа закрыта")
    public void formLoginNoVisible(){
        formLogin.shouldNotBe(visible);
    }

    @Step("Проверка видимости сообщения об ошибке")
    public void errorMsgVisible(String expectedText) {
        errorMsg.shouldBe(visible).shouldHave(text(expectedText));
    }

    @Step("Проверка цвета сообщения об ошибке")
    public void checkColorErrorMsg() {
        errorMsg.shouldHave(cssValue("color", "rgba(255, 105, 114, 1)"));
    }
}
