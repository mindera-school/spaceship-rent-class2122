package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest extends BaseControllerTest {

    @Test
    void test_getUserById_shouldReturn200() {
        // GIVEN
        UserEntity entity = getMockedEntity();
        when(userRepository.findById(5L))
                .thenReturn(Optional.of(entity));
        String path = "/users/5";

        // WHEN
        final var response = given()
                .headers(getApiAuthenticatedHeader())
                .when()
                .get(path)
                .then()
                .extract().response();

        // THEN
        verify(userRepository, times(1))
                .findById(anyLong());

        UserDetailsDto expected = getUserDetailsDto(entity);
        assertEquals(expected, response.getBody().as(UserDetailsDto.class));
    }

    @Test
    void test_getUserById_shouldReturn404() {
        // GIVEN
        when(userRepository.findById(5L))
                .thenReturn(Optional.empty());
        String path = "/users/5";

        // WHEN
        final var response = given()
                .headers(getApiAuthenticatedHeader())
                .when()
                .get(path)
                .then()
                .extract().response();

        // THEN
        verify(userRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode(),
                "status code");
        assertEquals("UserNotFoundException",
                response.getBody().as(SpaceshipRentError.class).getException(),
                "exception name");
    }


    private UserEntity getMockedEntity() {
        return UserEntity.builder()
                .id(5L)
                .name("Rafa")
                .age(20)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("Password123")
                .email("email@email.com")
                .build();
    }

    private UserDetailsDto getUserDetailsDto(UserEntity entity) {
        return UserDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .ssn(entity.getSsn())
                .licenseNumber(entity.getLicenseNumber())
                .planet(entity.getPlanet())
                .userType(entity.getUserType())
                .email(entity.getEmail())
                .build();
    }

}
