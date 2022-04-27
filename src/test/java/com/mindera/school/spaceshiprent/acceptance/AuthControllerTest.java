package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.dto.auth.CredentialsDto;
import com.mindera.school.spaceshiprent.dto.auth.LoginResponseDto;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.mindera.school.spaceshiprent.MockedObjects.getMockedUserEntityWithEncryptedPassword;
import static com.mindera.school.spaceshiprent.controller.AuthController.PATH_LOGIN;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class AuthControllerTest extends BaseControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    class LoginTest {

        @Test
        void test_login_success() {
            // ARRANGE
            final var email = "email@email.com";
            final var password = "password";
            final var entity = getMockedUserEntityWithEncryptedPassword(password, passwordEncoder);
            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(entity));
            final var credentials = CredentialsDto.builder()
                    .email(email)
                    .password(password)
                    .build();

            // ATC
            final var response = given()
                    .when()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(credentials)
                    .post(PATH_LOGIN)
                    .then()
                    .extract().response();

            // ASSERT
            assertEquals(200, response.getStatusCode());

            final var actual = response.as(LoginResponseDto.class);

            assertEquals(entity.getId(), actual.getPrincipal().getId(), "User id");
            assertNotNull(actual.getToken(), "Token");
        }

        @Test
        void test_login_wrongEmail_fail() {
            // ARRANGE
            final var invalidEmail = "invalid@email.com";
            final var password = "password";
            when(userRepository.findByEmail(invalidEmail))
                    .thenReturn(Optional.empty());
            final var credentials = CredentialsDto.builder()
                    .email(invalidEmail)
                    .password(password)
                    .build();

            // ATC
            final var response = given()
                    .when()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(credentials)
                    .post(PATH_LOGIN)
                    .then()
                    .extract().response();

            // ASSERT
            assertEquals(401, response.getStatusCode());

            final var actual = response.as(SpaceshipRentError.class);

            assertEquals("WrongCredentialsException", actual.getException(), "Exception name");
        }

        @Test
        void test_login_wrongPassword_fail() {
            // ARRANGE
            final var email = "email@email.com";
            final var password = "password";
            final var invalidPassword = "invalidPassword";
            final var entity = getMockedUserEntityWithEncryptedPassword(password, passwordEncoder);
            when(userRepository.findByEmail(email))
                    .thenReturn(Optional.of(entity));
            final var credentials = CredentialsDto.builder()
                    .email(email)
                    .password(invalidPassword)
                    .build();

            // ATC
            final var response = given()
                    .when()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(credentials)
                    .post(PATH_LOGIN)
                    .then()
                    .extract().response();

            // ASSERT
            assertEquals(401, response.getStatusCode());

            final var actual = response.as(SpaceshipRentError.class);

            assertEquals("WrongCredentialsException", actual.getException(), "Exception name");
        }

    }

}
