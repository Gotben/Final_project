package praktikum.api;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.response.Response;
import praktikum.Config;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {
    private final static String UTF_8 = "UTF-8";
    private final static String TYPE_TEXT_PLANE = "text/plain";
    public static RequestSpecification baseRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URL)
                .addHeader("Content-type", "application/json; charset=utf-8")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response doGetRequest(String path, String token) {
        return given()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .get(path)
                .thenReturn();
    }

    protected Response doPostRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path)
                .thenReturn();
    }
    protected Response doDeleteRequest(String path, String token, Integer idAd) {
        return given()
                .spec(baseRequestSpec())
                .pathParam("idAd", idAd)
                .header("Authorization", token)
                .delete(path)
                .thenReturn();
    }

    protected Response updateProfileMultipart(String path, String token, String name, String category, String condition, String city, String description, String price) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Токен не может быть null или пустым");
        }

        MultiPartSpecBuilder namePart = new MultiPartSpecBuilder(name)
                .controlName("name")
                .mimeType(TYPE_TEXT_PLANE)
                .charset(UTF_8); // можно юзать константы

        MultiPartSpecBuilder categoryPart = new MultiPartSpecBuilder(category)
                .controlName("category")
                .mimeType(TYPE_TEXT_PLANE)
                .charset(UTF_8);

        MultiPartSpecBuilder conditionPart = new MultiPartSpecBuilder(condition)
                .controlName("condition")
                .mimeType(TYPE_TEXT_PLANE)
                .charset(UTF_8);

        MultiPartSpecBuilder cityPart = new MultiPartSpecBuilder(city)
                .controlName("city")
                .mimeType(TYPE_TEXT_PLANE)
                .charset(UTF_8);

        MultiPartSpecBuilder descriptionPart = new MultiPartSpecBuilder(description)
                .controlName("description")
                .mimeType(TYPE_TEXT_PLANE)
                .charset(UTF_8);

        MultiPartSpecBuilder pricePart = new MultiPartSpecBuilder(price)
                .controlName("price")
                .mimeType(TYPE_TEXT_PLANE)
                .charset(UTF_8);

        return given()
                .log().all()
                .spec(baseRequestSpec())
                .header("Authorization", token)
                .contentType("multipart/form-data")
                .multiPart(namePart.build())
                .multiPart(categoryPart.build())
                .multiPart(conditionPart.build())
                .multiPart(cityPart.build())
                .multiPart(descriptionPart.build())
                .multiPart(pricePart.build())
                .post(path)
                .thenReturn();
    }
}
