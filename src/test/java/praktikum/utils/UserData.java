package praktikum.utils;

import java.util.UUID;

public class UserData {
    public static final String PASSWORD = "test";
    public static final String nameAd = "Объявление " + UUID.randomUUID().toString();

    public static String getRandomEmail() {
        return "test" + UUID.randomUUID().toString() + "@test.ru";
    }
}