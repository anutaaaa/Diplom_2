package ru.praktikum_services.stellar_burgers;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import praktikum.OrderHelper;
import praktikum.RandomHelper;
import praktikum.User;
import praktikum.UserHelper;

import static org.hamcrest.Matchers.*;

public class GetOrderTest extends BaseApi {
     User user = RandomHelper.getRandomUserWithAllParams();
     String accessToken;


    @Test
    public void getUserOrdersWithoutAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        OrderHelper.getOrderWithoutAuth()
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }

    @Test
    public void getUserOrdersWithAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        OrderHelper.getOrderWithAuth(accessToken)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .statusCode(200);
    }

    @After
    public void tearDown() {
        UserHelper.deleteUser(accessToken);
    }

}