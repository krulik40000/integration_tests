package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class CreatePostTest extends FunctionalTests {

    private static final String API_START = "/blog/user/";
    private static final String API_END = "/post";

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
                .post(API_START + userId + API_END);
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
                .post(API_START + userId + API_END);
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
                .post(API_START + userId + API_END);
    }
}
