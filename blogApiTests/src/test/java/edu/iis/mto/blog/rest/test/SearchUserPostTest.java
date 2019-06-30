package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

public class SearchUserPostTest extends FunctionalTests {

    private static final String API_START = "/blog/user/";
    private static final String API_END = "/post";


    @Test
    public void searchForPostOfUser_returnPostWithLikeAmount() {
        String userId = "1";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(1))
                .and()
                .body("likesCount", Matchers.hasItems(1))
                .when()
                .get(API_START + userId + API_END);
    }

    @Test
    public void searchForPostOfUserWithNoPosts_returnNothing() {
        String userId = "2";
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
                .get(API_START + userId + API_END);
    }

    @Test
    public void searchForPostOfRemovedUser_returnBadRequest() {
        String userId = "3";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(API_START + userId + API_END);
    }

    @Test
    public void
    searchForPostOfUserWithMOreThenOnePosts_returnProperAmount() {
        String userId = "4";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(2))
                .when()
                .get(API_START + userId + API_END);
    }

    @Test
    public void searchForPostOfUser_postHasNoLike() {
        String userId = "5";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("size()", Matchers.is(1))
                .and()
                .body("likesCount", Matchers.hasItems(0))
                .when()
                .get(API_START + userId + API_END);
    }
}
