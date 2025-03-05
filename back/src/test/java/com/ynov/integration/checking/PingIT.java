package com.ynov.integration.checking;

import com.ynov.dto.UserDto;
import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class PingIT {
    static UserDto user = UserDto.builder().username("user").email("user@gmail.com").build();

    @Test
    @TestSecurity(user = "UIDuser", roles = "viewer")// role inutile, je le laisse pour l'exemple.
    @JwtSecurity(claims = {
            @Claim(key = "email", value = "user@gmail.com")})
    public void getPingReturnPongWhenDatabaseIsRunning() {
        TestHelper.createUserInDB();
        RestAssured
                .given()

                .when()
                .get("/api/ping")

                .then()
                .statusCode(200)
                .body("status", equalTo("OK"))
                .body("details", equalTo("OK"));
    }
}
