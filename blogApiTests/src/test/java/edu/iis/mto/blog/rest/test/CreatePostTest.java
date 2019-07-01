package edu.iis.mto.blog.rest.test;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreatePostTest extends FunctionalTests {


    @Test
    public void blogPostByConfirmedUserReturnsCode201() {
        JSONObject jsonObj = new JSONObject().put("entry", "test post");
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_CREATED)
                   .when()
                   .post("/blog/user/1/post");
    }
    
    @Test
    public void blogPostByNewUserReturnsCode403() {
        JSONObject jsonObject = new JSONObject().put("entry", "stub blog post");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObject.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post("/blog/user/2/post");
    }
    
    @Test
    public void blogPostByRemovedUserReturnsCode403() {
        JSONObject jsonObject = new JSONObject().put("entry", "stub blog post");
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObject.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .when()
                .post("/blog/user/4/post");
    }

    
}
