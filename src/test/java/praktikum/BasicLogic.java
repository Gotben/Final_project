package praktikum;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.openqa.selenium.JavascriptExecutor;
import praktikum.api.profile.ProfileSteps;
import praktikum.api.user.UserRegisterRequest;
import praktikum.api.user.UserRegisterResponse;
import praktikum.api.user.UserSteps;
import praktikum.page.LoginPage;
import praktikum.page.MainPage;
import praktikum.utils.UserData;

public class BasicLogic {

    String email = UserData.getRandomEmail();
    String password = UserData.PASSWORD;
    String userEmail;
    String token;

    private LoginPage loginPage;
    private MainPage mainPage;

    private UserSteps userSteps = new UserSteps();
    private ProfileSteps profileSteps;

    @Step("Регистрация пользователя")
    @Description("Отправляет POST-запрос на регистрацию с уникальным email и стандартным паролем.")
    public void register() {
        UserRegisterRequest user = new UserRegisterRequest(email, password, password);
        UserRegisterResponse response = userSteps.registerUser(user).checkRegisterUser();
        userEmail = response.getUser().getEmail();
        token = "Bearer " + response.getAccess_token().getAccess_token();
    }

    @Step("Создание объявления через API")
    @Description("Регистрирует пользователя и публикует объявление с тестовыми данными.")
    public void adCreated() {
        register();
        profileSteps = new ProfileSteps(userSteps);
        profileSteps.adCreate().checkAdCreate();
    }

    @Step("Получение токена из localStorage браузера")
    @Description("Использует JavaScript для извлечения JWT-токена из localStorage. Возвращает null, если токен отсутствует.")
    protected String getTokenFromLocalStorage() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        Object token = js.executeScript("return localStorage.getItem('token');");
        return token == null ? null : token.toString();
    }
}


