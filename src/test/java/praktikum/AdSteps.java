package praktikum;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.*;
import io.qameta.allure.*;
import praktikum.page.AdPage;
import praktikum.page.LoginPage;
import praktikum.page.MainPage;
import praktikum.page.ProfilePage;
import praktikum.api.profile.Offer;
import praktikum.api.profile.ProfileSteps;
import praktikum.api.user.UserSteps;
import praktikum.utils.UserData;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Создание объявления")
public class AdSteps extends BasicLogic {
    private String uniqueAdName;
    private LoginPage loginPage;
    private MainPage mainPage;
    private AdPage adPage;
    private ProfilePage profilePage;
    private ProfileSteps profileSteps;
    private UserSteps userSteps;

    String name = UserData.nameAd;
    int newPrice = 777;

    @Given("Пользователь авторизован")
    @Step("Авторизация пользователя")
    @Description("Пользователь открывает главную страницу, нажимает «Вход и регистрация», затем авторизуется")
    public void user_login() {
        register();
        mainPage = new MainPage();
        mainPage = open(Config.BASE_URL, MainPage.class);
        mainPage.clickButtonLogin();
        loginPage = new LoginPage();
        loginPage.waitForPageToLoad();
        loginPage.getTitle("Войти");
        loginPage.login(userEmail, UserData.PASSWORD);
        loginPage.formLoginNoVisible();
    }

    @And("Пользователь находится на странице создания объявления")
    @Step("Переход на страницу создания объявления")
    @Description("После авторизации пользователь нажимает кнопку 'Разместить объявление'")
    public void user_on_ad_page() {
        mainPage.clickButtonAd();
        adPage = new AdPage();
        adPage.getTitle("Новое объявление");
    }

    @When("Пользователь создает объявление в категории {string}")
    @Step("Создание объявления в категории: {category}")
    @Description("Заполняет форму: название, категория, город, описание, цена. Нажимает 'Опубликовать'")
    public void user_create_ad(String category) {
        adPage.publishAd(name, category, "Санкт-Петербург", "Описание книги", 200);
    }

    @And("Пользователь переходит в профиль")
    @Step("Переход в профиль пользователя")
    @Description("Нажимает на иконку профиля для просмотра своих объявлений")
    public void open_profile() {
        mainPage.clickButtonProfile();
        profilePage = new ProfilePage();
        profilePage.getProfileTitle("Мой профиль");
    }

    @And("Проверяет объявление в профиле")
    @Step("Проверка отображения объявления в профиле")
    @Description("Убеждаемся, что опубликованное объявление отображается в списке объявлений пользователя")
    public void check_ad_in_profile() {
        profilePage = new ProfilePage();
        profilePage.getTitleAd(name);
    }

    @Given("Пользователь авторизован и у него есть созданное объявление")
    @Step("Пользователь авторизован и объявление уже создано")
    @Description("Выполняется создание объявления через API, затем пользователь авторизуется в интерфейсе")
    public void user_create_ad() {
        adCreated();
        mainPage = new MainPage();
        mainPage = open(Config.BASE_URL, MainPage.class);
        mainPage.clickButtonLogin();
        loginPage = new LoginPage();
        loginPage.waitForPageToLoad();
        loginPage.getTitle("Войти");
        loginPage.login(userEmail, UserData.PASSWORD);
        loginPage.formLoginNoVisible();
    }

    @When("Пользователь редактирует объявление")
    @Step("Редактирование объявления")
    @Description("Открывает объявление через профиль, нажимает 'Редактировать', изменяет цену")
    public void user_update_ad(){
        profilePage = new ProfilePage();
        profilePage.clickButtonUpdateAd();
        adPage = new AdPage();
        adPage.setInputPrice(newPrice);
    }

    @Then("Пользователь проверяет, что в объявлении отображается новая цена")
    @Step("Проверка отображения новой цены в профиле")
    @Description("После редактирования пользователь переходит в профиль и проверяет, что цена обновилась")
    public void check_new_price_in_ad() {
        mainPage = new MainPage();
        mainPage.clickButtonProfile();
        profilePage = new ProfilePage();
        profilePage.getPriceAd(String.valueOf(newPrice));
    }

    @When("Пользователь находит объявление и удаляет его")
    @Step("Удаление объявления через интерфейс")
    @Description("Выполняет поиск объявления по названию, открывает карточку, нажимает 'Удалить'")
    public void delete_ad() {
        mainPage = new MainPage();
        mainPage.deleteAd(name);
    }

    @And("Проверяем что у пользователя нет объявлений")
    @Step("Проверка отсутствия объявлений через UI")
    @Description("Выполняем поиск объявления по названию и убеждаемся, что оно не найдено")
    public void check_user_no_ad() {
        mainPage = new MainPage();
        mainPage.searchAd(name);
        mainPage.checkAdNotExists();
    }
}
