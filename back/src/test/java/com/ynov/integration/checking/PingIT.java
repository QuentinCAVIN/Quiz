package com.ynov.integration.checking;

import com.ynov.dto.Ping;
import com.ynov.dto.UserDto;
import com.ynov.helper.ApiResponse;
import com.ynov.helper.TestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
        ApiResponse<Ping> response = TestHelper.testGetUrl("/api/ping", Ping.class);

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.body()).isPresent()  // Vérifie que le corps est présent
                .get()  // Récupère la valeur de l'Optional
                .satisfies(ping -> {
                    assertThat(ping.status()).isEqualTo("OK");
                    assertThat(ping.details()).isEqualTo("OK");
                });
    }
}

