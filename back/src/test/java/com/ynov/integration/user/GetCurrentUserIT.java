package com.ynov.integration.user;

import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class GetCurrentUserIT {
    @Test
    @TestSecurity(user = "UIDuser")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getUsersMeShouldReturnCurrentAuthenticatedUser() {
        TestHelper.createUserInDB();
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body("username", equalTo("user"))
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "UIDuserAbsentInDB")
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getUsersMeShouldNotReturnUserAbsentInDB() {
        RestAssured
                .when()
                .get("/api/users/me")

                .then().body(is(emptyOrNullString()))
                .statusCode(404);
    }
}
