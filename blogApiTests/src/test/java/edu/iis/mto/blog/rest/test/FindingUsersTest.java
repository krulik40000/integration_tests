package edu.iis.mto.blog.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class FindingUsersTest {

    private static final String FINDING_USER = "/blog/user/find?searchString=";

    @Test
    public void finding_should_return_all_not_removed_users() {
        JSONObject jsonObj = new JSONObject();

        String searchString = "";

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size()", is(3))
                   .when()
                   .get(FINDING_USER + searchString);
    }

    @Test
    public void finding_removed_user_should_return_empty_list_of_users(){
        JSONObject jsonObj = new JSONObject();

        String searchString = "4";

        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size()", is(0))
                   .when()
                   .get(FINDING_USER + searchString);
    }
}
