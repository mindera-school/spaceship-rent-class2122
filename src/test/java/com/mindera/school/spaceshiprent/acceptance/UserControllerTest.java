package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.MockedData;
import com.mindera.school.spaceshiprent.components.EmailSender;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
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

import java.util.List;
import java.util.Optional;

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateUserDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedData.getUserDetailsDto;
import static com.mindera.school.spaceshiprent.MockedData.getUserList;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_CREATE_USER;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_SPACESHIPS;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_USERS;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_USER_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_USER_BY_ID;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.USER_ALREADY_EXISTS;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.USER_NOT_FOUND;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    class CreateUser {
        @Test
        void test_createUser_shouldReturn200() {
            // arrange
            final var entity = getMockedUserEntity();

            when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(entity);
            when(userRepository.findByEmail(Mockito.any(String.class)))
                    .thenReturn(Optional.empty());

            doNothing().when(sender).send(isA(String.class), isA(String.class));

            // act
            final var response = given()
                    .port(port)
                    .body(getCreateOrUpdateUserDto())
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_CREATE_USER)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1)).findByEmail(anyString());
            verify(userRepository, times(1)).save(any(UserEntity.class));
            verify(sender, times(1)).send(anyString(), anyString());

            assertEquals(HttpStatus.OK.value(), response.statusCode());

            final var expected = getUserDetailsDto(entity);
            final var actual = response.getBody().as(UserDetailsDto.class);

            assertEquals(expected, actual);
        }

        @Test
        void test_createUser_shouldReturn400() {
            // arrange
            final var entity = getMockedUserEntity();

            when(userRepository.findByEmail(entity.getEmail()))
                    .thenReturn(Optional.of(entity));

            // act
            final var response = given()
                    .port(port)
                    .body(getCreateOrUpdateUserDto())
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_CREATE_USER)
                    .then().contentType(ContentType.JSON).extract().response();

            // assert
            verify(userRepository, times(1)).findByEmail(anyString());

            assertEquals(HttpStatus.BAD_REQUEST.value(), response.statusCode());

            final String actualMessage = response.jsonPath().getString("message");

            assertEquals(USER_ALREADY_EXISTS, actualMessage);
        }
    }

    @Nested
    class GetUserById {
        @Test
        void test_getUserById_shouldReturn200() {
            // arrange
            UserEntity entity = getMockedUserEntity();
            when(userRepository.findById(5L))
                    .thenReturn(Optional.of(entity));

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_USER_BY_ID, 5L)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());

            UserDetailsDto expected = getUserDetailsDto(entity);
            assertEquals(expected, response.getBody().as(UserDetailsDto.class));
        }

        @Test
        void test_getUserById_shouldReturn404() {
            // arrange
            when(userRepository.findById(5L))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_USER_BY_ID, 5L)
                    .then().contentType(ContentType.JSON).extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode(), "status code");

            final String expectedMessage = String.format(USER_NOT_FOUND, 5L);
            final String actualMessage = response.jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage, "error message");
        }
    }

    @Nested
    class GetAllUsers {
        @Test
        void test_getAllUsers_shouldReturn200() {
            // arrange
            when(userRepository.findAll())
                    .thenReturn(getUserList());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_USERS)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1)).findAll();

            final var expected = getUserList().stream().map(MockedData::getUserDetailsDto).toArray();

            assertEquals(expected[0], response.getBody().as(UserDetailsDto[].class)[0]);
        }

        @Test
        public void test_getAll_shouldReturnEmpty() {
            // arrange
            final Long id = 5L;

            when(userRepository.findAll())
                    .thenReturn(List.of());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_USERS)
                    .then().extract().response();

            final var actual = response.getBody().jsonPath().getList("", RentDetailsDto.class);

            assertEquals(List.of(), actual);
            assertEquals(0, actual.size());
        }
    }

    @Nested
    class UpdateUser {
        @Test
        void test_updateUserById_shouldReturn200() {
            // arrange
            UserEntity entity = getMockedUserEntity();
            CreateOrUpdateUserDto dto = getCreateOrUpdateUserDto();

            when(userRepository.findById(anyLong()))
                    .thenReturn(Optional.of(entity));
            when(userRepository.save(any(UserEntity.class)))
                    .thenReturn(entity);

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_USER_BY_ID, 5L)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());
            verify(userRepository, times(1))
                    .save(Mockito.any(UserEntity.class));

            UserDetailsDto expected = getUserDetailsDto(entity);
            assertEquals(expected, response.getBody().as(UserDetailsDto.class));
        }

        @Test
        void test_updateUserById_shouldReturn404() {
            // arrange
            UserEntity entity = getMockedUserEntity();
            CreateOrUpdateUserDto dto = getCreateOrUpdateUserDto();

            when(userRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_USER_BY_ID, 5L)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());

            UserDetailsDto expected = getUserDetailsDto(entity);

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final String expectedMessage = String.format(USER_NOT_FOUND, 5L);
            final String actualMessage = response.jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }
    }

}
