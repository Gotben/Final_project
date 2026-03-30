package praktikum;

import io.cucumber.java.en.*;
import io.qameta.allure.*;
import praktikum.page.LoginPage;
import praktikum.page.MainPage;
import praktikum.utils.UserData;

import static com.codeborne.selenide.Selenide.open;

@Feature("Регистрация пользователя")
public class RegisterSteps extends BasicLogic {
    private LoginPage loginPage;
    private MainPage mainPage;

    String email = UserData.getRandomEmail();
    String password = UserData.PASSWORD;


    @Given("Пользователь находится на странице регистрации")
    @Step("Переход на страницу регистрации")
    @Description("Пользователь открывает главную страницу, нажимает «Вход и регистрация», затем «Нет аккаунта» для перехода к форме регистрации")
    public void user_on_register_page() {
        register();
        mainPage = open(Config.BASE_URL, MainPage.class);
        mainPage.clickButtonLogin();
        loginPage = new LoginPage();
        loginPage.clickButtonNoAccount();
        loginPage.waitForPageToLoad();
        loginPage.getTitle("Зарегистрироваться");
    }

    @When("Пользователь регистрируется в системе с валидными данными")
    @Step("Регистрация пользователя с валидными данными")
    @Description("Заполнение формы регистрации email, паролем и его подтверждением, нажатие кнопки «Создать аккаунт»")
    public void user_register() {
        loginPage.register(email, password, password);
        loginPage.formLoginNoVisible();
    }

    @When("Пользователь регистрируется в системе с данными уже зарегистрированного пользователя")
    @Step("Попытка регистрации с существующими учётными данными")
    @Description("Проверка реакции системы на попытку создать аккаунт с email, который уже привязан к существующему аккаунту")
    public void user_register_with_existing_credentials() {
        loginPage.register(userEmail, password, password);
    }

    @Then("Отображается сообщение об ошибке регистрации")
    @Step("Проверка отображения сообщения об ошибке регистрации")
    @Description("Проверяется, что при неудачной регистрации система показывает пользователю сообщение с описанием проблемы (например, «Email уже используется»)")
    public void check_error_msg_displayed() {
        loginPage.errorMsgVisible("Ошибка");
        loginPage.checkColorErrorMsg();
    }
}
