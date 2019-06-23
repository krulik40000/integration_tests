package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

public class CreatePostTest {


    private static final String CONFIRMED_USER = "/blog/user/1/post";


    @Test
    public void creating_post_by_confirmed_user_should_create_post() {
        JSONObject jsonObj = new JSONObject().put("entry", "Test");

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_CREATED)
                   .when()
                   .post(CONFIRMED_USER);
    }
}
