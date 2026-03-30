package praktikum;

import com.codeborne.selenide.Selenide;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import praktikum.page.MainPage;
import praktikum.api.profile.Offer;
import praktikum.api.profile.ProfileSteps;
import praktikum.api.user.UserSteps;

import java.util.List;

public class CommonSteps extends BasicLogic {

    MainPage mainPage;
    private ProfileSteps profileSteps;
    private UserSteps userSteps;

    @Before
    @Step("Подготовка окружения: настройка RestAssured")
    @Description("Добавляет поддержку java.time.Instant через JavaTimeModule для корректной работы с датами в JSON.")
    public static void setUp() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        RestAssured.config = RestAssured.config()
                .objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }

    @After
    @Step("Очистка данных после теста")
    @Description("Проверяет наличие токена и активных объявлений. Удаляет первое объявление, если оно есть. Закрывает браузер.")
    public void tearDown() {
        try {
            token = getTokenFromLocalStorage();
            if (token != null && !token.isEmpty()) {
                String userToken = "Bearer " + token;
                userSteps = new UserSteps();
                profileSteps = new ProfileSteps(userSteps);
                List<Offer> offers = profileSteps.userAdsGet(userToken).checkAdsUserGet().getOffers();
                if (offers != null && !offers.isEmpty()) {
                    Integer id = profileSteps.userAdsGet(userToken).checkAdsUserGet().getOffers().get(0).getId();
                    if (id != null) {
                        profileSteps.deleteAd(userToken, id).checkAdDelete();
                    }
                } else {
                    System.out.println("Нет объявлений для удаления — список пуст.");
                }
            } else {
                System.out.println("Токен не найден — пропускаем удаление объявления.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении объявления: " + e.getMessage());
        } finally {
            Selenide.closeWebDriver();
        }
    }

    @Then("Пользователь видит главную страницу")
    @Step("Проверка отображения элементов главной страницы после регистрации")
    @Story("Успешная регистрация нового пользователя")
    @Description("Проверяется видимость кнопки «Выйти» и иконки профиля — это подтверждает, что пользователь авторизован и находится на главной странице")
    public void check_user_register() {
        mainPage = new MainPage();
        mainPage.getButtonExitVisible("Выйти");
        mainPage.isButtonProfileVisible();
    }
}
