package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.components.EmailSender;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateUserDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedData.getUserDetailsDto;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_CREATE_USER;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailSender sender;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class GetUserById {
        @Test
        void test_getUserById_shouldReturn200() {
            // GIVEN
            UserEntity entity = getMockedUserEntity();
            when(userRepository.findById(5L))
                    .thenReturn(Optional.of(entity));
            String path = "/users/5";

            // WHEN

            /*ResponseEntity<UserDetailsDto> response = given()
                    .body(getCreateOrUpdateUserDto())

                    path,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    UserDetailsDto.class);*/

            // THEN
            verify(userRepository, times(1))
                    .findById(anyLong());

            UserDetailsDto expected = getUserDetailsDto(entity);
            //assertEquals(expected, response.getBody());
        }

        @Test
        void test_getUserById_shouldReturn404() {
            // GIVEN
            when(userRepository.findById(5L))
                    .thenReturn(Optional.empty());
            String path = "/users/5";

            // WHEN
           /* ResponseEntity<SpaceshipRentError> response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    SpaceshipRentError.class);
*/
            // THEN
            verify(userRepository, times(1))
                    .findById(anyLong());

           /* assertEquals(HttpStatus.NOT_FOUND,
                    response.getStatusCode(),
                    "status code");
            assertEquals("UserNotFoundException",
                    Objects.requireNonNull(response.getBody()).getException(),
                    "exception name");*/
        }
    }

    @Nested
    class CreateUser {
        @Test
        void test_createUser_shouldReturn200() {
            // arrange
            final var entity = getMockedUserEntity();

            when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(entity);

            doNothing().when(sender).send(isA(String.class));

            // act
            final var response = given()
                    .port(port)
                    .body(getCreateOrUpdateUserDto())
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_CREATE_USER)
                    .then().extract().response();

            // assert
            assertEquals(HttpStatus.OK.value(), response.statusCode());

            final var expected = getUserDetailsDto(entity);
            final var actual = response.getBody().as(UserDetailsDto.class);

            assertEquals(expected, actual);
        }

    }

}
