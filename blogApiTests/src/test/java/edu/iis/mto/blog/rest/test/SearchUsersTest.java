package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;

public class SearchUsersTest extends FunctionalTests {
    private static final String USER_BASE = "/blog/user/find?searchString=";

    @Test
    public void searchingValidUserByNameReturnsUser() {
        JSONObject jsonObj = new JSONObject();

        String name = "John";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(1))
                .when()
                .get(USER_BASE + name);
    }

    @Test
    public void searchingForNonExistingUserReturnsEmptyResponse() {
        JSONObject jsonObj = new JSONObject();

        String name = "noone";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(0))
                .when()
                .get(USER_BASE + name);
    }

    @Test
    public void searchingForUserWithStatusRemovedReturnsEmptyResponse() {
        JSONObject jsonObj = new JSONObject();

        String name = "poorhim";

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(0))
                .when()
                .get(USER_BASE + name);
    }
}
