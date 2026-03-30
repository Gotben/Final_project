package praktikum.api.profile;

import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import praktikum.api.user.UserSteps;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("API: Управление объявлениями")
@Feature("Бизнес-логика работы с объявлениями")
public class ProfileSteps {
    private ProfileApi profileApi;
    private ValidatableResponse response;

    public ProfileSteps(UserSteps userSteps) {
        this.profileApi = new ProfileApi(userSteps);
    }

    @Step("Создание объявления")
    public ProfileSteps adCreate() {
        response = profileApi.adCreate().then();
        return this;
    }

    @Step("Проверка успешного создания объявления")
    public AdCreateResponse checkAdCreate() {
        response.assertThat().statusCode(SC_CREATED);
        AdCreateResponse adCreateResponseApi = response.extract().body().as(AdCreateResponse.class);
        return adCreateResponseApi;
    }

    @Step("Удаление объявления")
    public ProfileSteps deleteAd(String token, Integer idAd) {
        response = profileApi.deleteAd(token, idAd).then();
        return this;
    }

    @Step("Проверка успешного удаления объявления")
    public ProfileSteps checkAdDelete() {
        response.assertThat().statusCode(SC_OK);
        AdDeleteResponse adDeleteResponseApi = response.extract().body().as(AdDeleteResponse.class);
        assertEquals("Объявление удалено успешно", adDeleteResponseApi.getMessage(), "Сообщение об успешном удалении не совпадает");
        return this;
    }

    @Step("Получение объявлений у пользователя")
    public ProfileSteps userAdsGet(String token) {
        response = profileApi.getUserAd(token).then();
        return this;
    }

    @Step("Проверка успешного получения объявлений пользователя")
    public AdsUserResponse checkAdsUserGet() {
        response.assertThat().statusCode(SC_OK);
        AdsUserResponse adsUserResponseApi = response.extract().body().as(AdsUserResponse.class);
        return adsUserResponseApi;
    }

    @Step("Получаем ID первого объявления из списка")
    public Integer getIdAd() {
        AdsUserResponse adsUserResponse = checkAdsUserGet();
        if (adsUserResponse.getOffers() != null && !adsUserResponse.getOffers().isEmpty()) {
            return adsUserResponse.getOffers().get(0).getId();
        } else {
            throw new IllegalStateException("Список объявлений пуст — невозможно получить idAd");
        }
    }
}
