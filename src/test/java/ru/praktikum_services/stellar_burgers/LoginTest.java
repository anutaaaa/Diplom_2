package ru.praktikum_services.stellar_burgers;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import praktikum.RandomHelper;
import praktikum.User;
import praktikum.UserCredentials;
import praktikum.UserHelper;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseApi{
    User user = RandomHelper.getRandomUserWithAllParams();
    String accessToken;

    @Test
    public void loginWithAllParams() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        UserHelper.loginUser(UserCredentials.from(user))
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("refreshToken", notNullValue())
                .body("accessToken", notNullValue())
                .statusCode(200);
    }

    @Test
    public void loginWithIncorrectEmail() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        User incorrectEmail = new User(user.getName(), RandomStringUtils.randomAlphabetic(20) + "@yandex.ru", user.getPassword());
        UserHelper.loginUser(UserCredentials.from(incorrectEmail))
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(401);
    }

    @Test
    public void loginWithIncorrectPassword() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        User incorrectPassword = new User(user.getName(), user.getEmail(), RandomStringUtils.randomAlphabetic(20));
        UserHelper.loginUser(UserCredentials.from(incorrectPassword))
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(401);
    }

    @After
    public void tearDown() {
        UserHelper.deleteUser(accessToken);
        }
    }
