package edu.iis.mto.blog.rest.test;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreatePostTest extends FunctionalTests {

    private static final String USER_API = "/blog/user/1/post";

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
                   .post(USER_API);
    }

    
}
