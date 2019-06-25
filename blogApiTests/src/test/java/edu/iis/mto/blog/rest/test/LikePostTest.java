package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

public class LikePostTest extends FunctionalTests {

    private static final String API_START = "/blog/user/";

    @Test
    public void likeBlogPostByConfirmedUser_returnCode201() {
        String userId = "1";
        String postId = "1";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(API_START + userId + "/like/" + postId);
    }

    @Test
    public void twoLikesByOneUser_stayOneLike() {
        String userId = "1";
        String postId = "2";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(API_START + userId + "/like/" + postId);
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(API_START + userId + "/like/" + postId);
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .when()
                .get("/blog/user/4/post")
                .then()
                .body("likesCount", Matchers.hasItem(1));
    }

    @Test
    public void likeBlogPostByNewUser_returnCode403() {
        String userId = "2";
        String postId = "1";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post(API_START + userId + "/like/" + postId);
    }

    @Test
    public void likeBlogPostByRemovedUser_returnCode403() {
        String userId = "3";
        String postId = "1";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post(API_START + userId + "/like/" + postId);
    }

    @Test
    public void likeBlogPostByOwner_returnCode403() {
        String userId = "4";
        String postId = "1";
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post(API_START + userId + "/like/" + postId);
    }
}

