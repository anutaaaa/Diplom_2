package praktikum;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserHelper {

    public static Response loginUser(UserCredentials userCredentials) {
        return given()
                .header("Content-type", "application/json")
                .body(userCredentials)
                .when()
                .post(Constants.LOGIN_ENDPOINT);
    }

    public static Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(Constants.REGISTER_ENDPOINT);
    }
    public static Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(Constants.USER_ENDPOINT);
    }

    public static Response updateUserWithoutAuth(String accessToken, UserUpdate userUpdate) {
        return given()
                .header("Content-type", "application/json")
                .body(userUpdate)
                .when()
                .patch(Constants.USER_ENDPOINT);
    }

    public static Response updateUserWithAuth(String accessToken, UserUpdate userUpdate) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(userUpdate)
                .when()
                .patch(Constants.USER_ENDPOINT);
    }
}
