package praktikum.api.user;

import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class UserSteps {
    private UserApi userApi = new UserApi();
    private ValidatableResponse response;

    @Step("Регистрация пользователя")
    public UserSteps registerUser(UserRegisterRequest user) {
        response = userApi.registerUser(user).then();
        return this;
    }

    @Step("Проверка успешной регистрации (возвращает accessToken)")
    public UserRegisterResponse checkRegisterUser() {
        response.assertThat().statusCode(SC_CREATED);
        UserRegisterResponse userRegisterResponseApi = response.extract().body().as(UserRegisterResponse.class);
        assertNotNull(userRegisterResponseApi.getUser(),"Ответ сервера должен содержать объект user");
        assertNotNull(userRegisterResponseApi.getAccess_token(),"Ответ сервера должен содержать объект access_token");
        return userRegisterResponseApi;
    }

    public String getAccessToken() {
        return checkRegisterUser().getAccess_token().getAccess_token();
    }
}
