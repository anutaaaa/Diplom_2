package praktikum;

import io.restassured.response.Response;
import java.util.List;

import static io.restassured.RestAssured.given;


public class OrderHelper {

    public static List<String> getIngredients() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(Constants.INGREDIENTS_ENDPOINT)
                .then()
                .extract()
                .path("data._id");
    }

    public static Response getOrderWithoutAuth() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(Constants.ORDERS_ENDPOINT);

    }

    public static Response getOrderWithAuth(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .get(Constants.ORDERS_ENDPOINT);

    }

    public static Response createOrderWithoutAuth(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(Constants.ORDERS_ENDPOINT);
    }

    public static Response createOrderWithAuth(String accessToken, Order order) {
        return  given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(Constants.ORDERS_ENDPOINT);
    }
}
