package HomeWork4;

import HomeWork3.BaseApiTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

class ImgurApiTest extends BaseApiTest {

    private String currentDeleteHash;

    public ImgurApiTest() throws IOException {
    }

    //Создадим в классе с тестами новый объект спецификации ответа:
    ResponseSpecification responseSpecification = null;

    //В @BeforeEach определим основные проверки для респонса:
    @BeforeEach
    void beforeTestResponse() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();
    }

    //Создадим в классе с тестами новый объект спецификации запроса:
    RequestSpecification requestSpecification = null;

    //
    @BeforeEach
    void beforeTestRequest() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization", getToken())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY)
                .build();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = getBaseUri();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Тест-кейс №1 на совпадение значения поля id и значения поля title из Postman коллекции")
    void testGetIdContainsIncorrectTitle() throws IOException {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .body("data.id", is("m3Wpbhn"))
                .spec(responseSpecification);
    }

    //попробуем зaгрузить картиночку
    @Test
    @DisplayName("Тест-кейс №2  проверка загрузки картинки")
    void testImageUpload() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .when()
                .header(new Header("content-type", "multipart/form-data"))
                .multiPart("image", new File("./src/main/resources/res.jpg"))
                .expect()
                .statusCode(200)
                .body("data.id", is(notNullValue()))
                .body("data.deletehash", is(notNullValue()))
                /*.spec(responseSpecification)*/
                .log().all()
                .when()
                .post("3/upload")
                .jsonPath()
                .getString("data.deletehash"); //записываем
    }

    @Test
    @DisplayName("Тест-кейс №3 проверка статус кода 200 GET-запроса" +
            " Image из Postman коллекции при совпадении ширины картинки")
    void statusCode() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.width", is(320))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №4 проверка совпадения размера картинки")
    void sizeImage() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.size", is(21383))
                .log()
                .all()
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №5 проверка текста ссылки картинки")
    void linkText() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.link", is("https://i.imgur.com/m3Wpbhn.jpg"))
                .log()
                .all()
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №6 проверка корректности статуса HTTP/1.1 200 OK")
    void statusLine() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.link", is("https://i.imgur.com/m3Wpbhn.jpg"))
                .log()
                .all()
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);

    }

    @Test
    @DisplayName("Тест-кейс №7 проверка корректности значения datetime")
    void dateTime() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.datetime", is(1630158786))
                .log()
                .all()
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №8 проверка корректности значения height")
    void dateHeight() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.height", is(307))
                .log()
                .all()
                .statusLine("HTTP/1.1 200 OK")
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №9 проверка типа картинки")
    void dateType() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.type", is("image/jpeg"))
                .log()
                .all()
                .statusLine("HTTP/1.1 200 OK")
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Тест-кейс №10 проверка описания картинки")
    void dateDescription() throws Exception {
        given()
                .spec(requestSpecification)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.description", is(nullValue()))
                .log()
                .all()
                .when()
                .get("3/image/{imageHash}", getimageHash())
                .prettyPeek()
                .then()
                .spec(responseSpecification);
    }
}