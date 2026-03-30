package praktikum.api.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.api.BaseHttpClient;

public class UserApi extends BaseHttpClient {
    private final static String POST_USER_REGISTER = "/api/signup";

    @Step("Регистрация пользователя")
    public Response registerUser(UserRegisterRequest user) {
        return doPostRequest(POST_USER_REGISTER, user);
    }

}
