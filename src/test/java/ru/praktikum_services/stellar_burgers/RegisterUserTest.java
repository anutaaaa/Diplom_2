package ru.praktikum_services.stellar_burgers;

import io.restassured.response.Response;
import org.junit.Test;
import praktikum.RandomHelper;
import praktikum.User;
import praktikum.UserHelper;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUserTest extends BaseApi{
    User user = RandomHelper.getRandomUserWithAllParams();
    String accessToken;

    @Test
    public void registerUserWithAllParams() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        response.then()
                .assertThat()
                .body("success", equalTo(true))
                .body("refreshToken", notNullValue())
                .body("accessToken", notNullValue())
                .statusCode(200);
        UserHelper.deleteUser(accessToken);
    }

    @Test
    public void registrationOfExistingUser() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        UserHelper.createUser(user)
                .then()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .statusCode(403);
    }

    @Test
    public void registerUserWithoutName() {
        User userWithoutName = RandomHelper.getRandomWithoutName();
        UserHelper.createUser(userWithoutName)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }

    @Test
    public void registerUserWithoutEmail() {
        User userWithoutEmail = RandomHelper.getRandomUserWithoutEmail();
        UserHelper.createUser(userWithoutEmail)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);

    }

    @Test
    public void registerUserWithoutPassword() {
        User userWithoutPassword = RandomHelper.getRandomUserWithoutPassword();
        UserHelper.createUser(userWithoutPassword)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }

}