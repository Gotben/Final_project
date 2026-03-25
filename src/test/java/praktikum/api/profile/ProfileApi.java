package praktikum.api.profile;

import io.qameta.allure.*;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.api.BaseHttpClient;
import praktikum.api.user.UserSteps;
import praktikum.utils.UserData;

@Epic("API: Управление объявлениями")
@Feature("Профиль пользователя: создание, получение и удаление объявлений")
public class ProfileApi extends BaseHttpClient {
    private final static String POST_AD_CREATE = "/api/create-listing";
    private final static String DELETE_AD = "/api/listings/{idAd}";
    private final static String GET_USER_AD = "/api/profile/listings/1";
    private UserSteps userSteps;

    public ProfileApi(UserSteps userSteps) {
        this.userSteps = userSteps;
    }

    @Step("Создание нового объявления")
    public Response adCreate() {
        String name = UserData.nameAd;
        String token = "Bearer " + userSteps.getAccessToken();
        String category = "Авто";
        String condition = "Новый";
        String city = "Москва";
        String description = "Описание товара";
        String price = "444";
        return updateProfileMultipart(POST_AD_CREATE, token, name, category, condition, city, description, price);
    }

    @Step("Удаление объявления по ID")
    public Response deleteAd(String token, Integer idAd) {
        return doDeleteRequest(DELETE_AD, token, idAd);
    }

    @Step("Получение списка объявлений пользователя")
    public Response getUserAd(String token) {
        return doGetRequest(GET_USER_AD, token);
    }
}
