package praktikum;

import io.cucumber.java.en.*;
import io.qameta.allure.*;
import praktikum.page.LoginPage;
import praktikum.page.MainPage;
import praktikum.utils.UserData;

import static com.codeborne.selenide.Selenide.open;

@Feature("Авторизация пользователя")
public class LoginSteps extends BasicLogic {
    private LoginPage loginPage;
    private MainPage mainPage;

    @Given("Пользователь находится на странице авторизации")
    @Step("Переход на страницу авторизации")
    @Description("Пользователь открывает главную страницу, нажимает «Вход и регистрация», затем авторизуется")
    public void user_on_login_page() {
        register();
        mainPage = open(Config.BASE_URL, MainPage.class);
        mainPage.clickButtonLogin();
        loginPage = new LoginPage();
        loginPage.waitForPageToLoad();
        loginPage.getTitle("Войти");
    }

    @When("Пользователь авторизуется в системе с валидными данными")
    @Step("Авторизация пользователя с валидными данными")
    @Description("Заполнение формы авторизации email и пароля, нажатие кнопки «Войти»")
    public void user_login() {
        loginPage.login(userEmail, UserData.PASSWORD);
        loginPage.formLoginNoVisible();
    }
}
