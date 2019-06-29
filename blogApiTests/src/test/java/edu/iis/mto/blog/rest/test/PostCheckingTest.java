package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;

public class PostCheckingTest extends FunctionalTests {
    private static final String USER_WITH_ONE_POST = "/blog/user/1/post";
    private static final String USER_WITHOUT_POSTS = "/blog/user/2/post";
    private static final String USER_WITH_STATUS_REMOVED = "/blog/user/3/post";
    private static final String USER_WITH_LIKED_POST = "/blog/user/4/post";

    @Test
    public void checkingValidUserWithOnePostReturnsCorrectValue() {
        JSONObject jsonObj = new JSONObject();

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
                .get(USER_WITH_ONE_POST);
    }

    @Test
    public void checkingUserWithoutPostsReturnsCorrectValue() {
        JSONObject jsonObj = new JSONObject();

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
                .get(USER_WITHOUT_POSTS);
    }

    @Test
    public void checkingPostsCountOfRemovedUserResultsIn400BadRequest() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .get(USER_WITH_STATUS_REMOVED);

    }

    @Test
    public void checkingPostWithoutLikesReturnsCorrectValue() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("likesCount", Matchers.hasItem(0))
                .when()
                .get(USER_WITH_ONE_POST);
    }

    @Test
    public void checkingPostWithOneLikeShouldReturnCorrectValue() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("likesCount", Matchers.hasItem(1))
                .when()
                .get(USER_WITH_LIKED_POST);
    }

}
