package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class FindingPostsTest {

    private static final String EXISTING_POST = "/blog/user/1/post";
    private static final String FINDING_REMOVED_USER_POST = "/blog/user/4/post";
    @Test
    public void finding_pots_of_existing_user_with_confirmed_status_should_return_valid_number_of_posts() {
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
                   .body("size()", is(1))
                   .when()
                   .get(EXISTING_POST);
    }
    @Test
    public void finding_removed_user_post_should_return_404_error_code() {
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
                   .get(FINDING_REMOVED_USER_POST);

    }
}
