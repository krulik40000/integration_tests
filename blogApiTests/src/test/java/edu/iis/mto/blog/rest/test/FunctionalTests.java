package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import org.junit.Test;

public class FunctionalTests {


    private static final String API_START = "/blog/user/";
    private static final String POST_API_END = "/post";

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        } else {
            RestAssured.port = Integer.valueOf(port);
        }

        String basePath = System.getProperty("server.base");
        if (basePath == null) {
            basePath = "";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if (baseHost == null) {
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;
    }

    @Test
    public void blogPostByConfirmedUser_returnCode201(){
        String userId = "1";
        JSONObject jsonObj = new JSONObject().put("entry", "blog post");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post(API_START + userId + POST_API_END);
    }

    @Test
    public void blogPostByNewUser_returnCode403(){
        String userId = "2";
        JSONObject jsonObj = new JSONObject().put("entry", "blog post");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post(API_START + userId + POST_API_END);
    }

    @Test
    public void blogPostByRemovedUser_returnCode403(){
        String userId = "3";
        JSONObject jsonObj = new JSONObject().put("entry", "blog post");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post(API_START + userId + POST_API_END);
    }

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
