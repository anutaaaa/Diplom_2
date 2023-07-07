package ru.praktikum_services.stellar_burgers;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import praktikum.*;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.Matchers.equalTo;

public class UpdateDataTest extends BaseApi{
    User user = RandomHelper.getRandomUserWithAllParams();
    UserUpdate userUpdate = RandomHelper.getRandomUserWithAllParamsNew();
    String accessToken;


    @Test
    public void updateDataWithoutAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        UserHelper.updateUserWithoutAuth(accessToken, userUpdate)
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }

    @Test
    public void updateDataWithAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        UserHelper.updateUserWithAuth(accessToken, userUpdate)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("user", notNullValue())
                .statusCode(200);
    }


    @After
    public void tearDown() {
        UserHelper.deleteUser(accessToken);
    }
}