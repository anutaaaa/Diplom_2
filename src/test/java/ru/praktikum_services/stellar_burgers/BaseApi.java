package ru.praktikum_services.stellar_burgers;
import io.restassured.RestAssured;
import org.junit.Before;

public class BaseApi {
    public BaseApi() {
    }
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }
}