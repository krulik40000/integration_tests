package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import java.net.URI;

public class LikePostTest {

    private static final String LIKE_OTHER_USER_POST ="/blog/user/1/like/2" ;
    private static final String NEW_USER_LIKE_POST ="/blog/user/2/like/1";
    private static final String LIKE_OWN_POST = "/blog/user/1/like/1";

    @Test
    public void confirmed_user_should_be_able_to_like_other_user_post() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(LIKE_OTHER_USER_POST);
    }

    @Test
    public void user_should_not_be_able_to_like_own_post() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_NOT_FOUND)
                   .when()
                   .post(LIKE_OWN_POST);
    }

    @Test
    public void new_user_should_not_be_able_to_like_post() {
        JSONObject jsonObj = new JSONObject();

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_NOT_FOUND)
                   .when()
                   .post(NEW_USER_LIKE_POST);
    }

}
