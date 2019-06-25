package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

public class SearchUserTest extends FunctionalTests {

    private static final String USER_API = "/blog/user/find?searchString=";

    @Test
    public void findUserByFirstName() {
        String user = "John";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(1))
                .when()
                .get(USER_API + user);
    }

    @Test
    public void findUserByLastName() {
        String user = "Steward";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(1))
                .when()
                .get(USER_API + user);
    }

    @Test
    public void findUserByEmail() {
        String user = "aaa@domain.com";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(1))
                .when()
                .get(USER_API + user);
    }

    @Test public void shouldNotFindRemovedUser() {
        String user = "Removed";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(0))
                .when()
                .get(USER_API + user);
    }
}
