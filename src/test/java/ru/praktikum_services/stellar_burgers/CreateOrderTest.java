package ru.praktikum_services.stellar_burgers;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import praktikum.*;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.*;

public class CreateOrderTest extends BaseApi{
    User user = RandomHelper.getRandomUserWithAllParams();
    Order order;
    String accessToken;
    int ingredientList;
    List<String> ingredients;
    List<String> allIngredients;


    @Test
    public void createOrderWithIngredientsAndAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        allIngredients = OrderHelper.getIngredients();
        ingredientList = RandomHelper.sizeForIngredientList(allIngredients.size());
        ingredients = allIngredients.subList(0, ingredientList);
        order = new Order(ingredients);
        OrderHelper.createOrderWithAuth(accessToken, order)
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order.number", notNullValue())
                .statusCode(200);
    }

    @Test
    public void createOrderWithoutIngredientsWithAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        order = new Order(ingredients);
        OrderHelper.createOrderWithAuth(accessToken, order)
                .then().assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"))
                .statusCode(400);
    }


    @Test
    public void createOrderIncorrectIngredientWithAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        ingredients = new ArrayList<>();
        ingredients.add(RandomStringUtils.randomAlphabetic(33));
        order = new Order(ingredients);
        OrderHelper.createOrderWithAuth(accessToken, order)
                .then()
                .assertThat()
                .statusCode(500);
    }

    @Test
    public void createOrderWithIngredientsWithoutAuth() {
        Response response = UserHelper.createUser(user);
        accessToken = response.then().extract().path("accessToken").toString();
        allIngredients = OrderHelper.getIngredients();
        ingredientList = RandomHelper.sizeForIngredientList(allIngredients.size());
        ingredients = allIngredients.subList(0, ingredientList);
        order = new Order(ingredients);
        OrderHelper.createOrderWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @After
    public void tearDown() {
        UserHelper.deleteUser(accessToken);
}
}
