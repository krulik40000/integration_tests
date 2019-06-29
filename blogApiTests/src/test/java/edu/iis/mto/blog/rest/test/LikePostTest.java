package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;

public class LikePostTest  extends FunctionalTests {
    private static final String LIKED_TWICE_POST = "/blog/user/1/post";
    private static final String SELF_POST_LIKE = "/blog/user/1/like/1";

    private static final String OTHER_POST_LIKE = "/blog/user/1/like/2";
    private static final String ANOTHER_POST_LIKE = "/blog/user/4/like/1";

    private static final String POST_LIKE_BY_CREATED_USER = "/blog/user/2/like/1";
    private static final String POST_LIKE_BY_REMOVED_USER = "/blog/user/3/like/1";

    @Test
    public void likingPostsByUsersNotWithConfirmedStatusFailsWithBadRequest() {
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
                .post(POST_LIKE_BY_CREATED_USER);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post(POST_LIKE_BY_REMOVED_USER);
    }

    @Test
    public void likingPostByConfirmedUserReturnsOk() {
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
                .post(OTHER_POST_LIKE);
    }

    @Test
    public void selfLikingReturns400BadRequest() {
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
                .post(SELF_POST_LIKE);
    }


    @Test
    public void likingPostTwiceDoesNotChangeItsStatus() {
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
                .post(ANOTHER_POST_LIKE);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post(ANOTHER_POST_LIKE);

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .when()
                .get(LIKED_TWICE_POST)
                .then()
                .body("likesCount", Matchers.hasItem(1));
    }
}
