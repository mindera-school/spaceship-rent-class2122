package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.controller.UserController;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Value("${local.server.port}")
    int port;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    void test_getUserById_shouldReturn200() {
        // ARRANGE
        final var entity = getMockedUserEntity(5L, 23);
        when(userRepository.findById(5L))
                .thenReturn(Optional.of(entity));
        String path = "/users/5";

        // ACT
        final var response = given()
                .when()
                .get(UserController.PATH_GET_USER_BY_ID, "5")
                .then()
                .extract()
                .response();

        // ASSERT
        verify(userRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        final var expected = getUserDetailsDto(entity);
        final var actual = response.getBody().as(UserDetailsDto.class);
        assertEquals(expected, actual);
    }

    @Test
    void test_getUserById_shouldReturn404() {
        // ARRANGE
        when(userRepository.findById(5L))
                .thenReturn(Optional.empty());

        // ACT
        final var response = given()
                .when()
                .get(UserController.PATH_GET_USER_BY_ID, "5").peek()
                .then()
                .extract()
                .response();

        // ASSERT
        verify(userRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(),
                response.getStatusCode(),
                "status code");
    }

    @Test
    final void test_createUser_shouldReturn200() {
        //ARRANGE
        final var entity = getMockedUserEntity(1L, 22);

        when(userRepository.save(any())).thenReturn(entity);

        //ACT
        final var response = given()
                .body(getMockedCreateOrUpdateUserDto(22))
                .contentType(ContentType.JSON)
                .when()
                .post(UserController.PATH_CREATE_USER)
                .then()
                .extract()
                .response();

        //ASSERT
        verify(userRepository, times(1)).save(any());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        final var expected = getUserDetailsDto(entity);
        final var actual = response.getBody().as(UserDetailsDto.class);

        assertEquals(expected, actual);

    }

    @Test
    final void test_createUser_shouldReturn400() {
        //ARRANGE

        when(userRepository.save(any())).thenReturn(Optional.empty());

        //ACT
        final var response = given()
                .body(getMockedCreateOrUpdateUserDto(19))
                .contentType(ContentType.JSON)
                .when()
                .post(UserController.PATH_CREATE_USER)
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    final void test_getAllUsers_shouldReturn200(){
        // ARRANGE
        final var entity = getMockedUserEntity(1L, 22);

        when(userRepository.findAll()).thenReturn(List.of(entity));

        // ACT
        final var response = given()
                .when()
                .get(UserController.PATH_GET_ALL_USERS)
                .then()
                .extract()
                .response();

        // ASSERT
        verify(userRepository, times(1)).findAll();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        final  var expected = List.of(getUserDetailsDto(entity));
        final var actual = response.jsonPath().getList("", UserDetailsDto.class);

        assertEquals(expected, actual);
    }

    @Test
    final void test_getAllUsers_shouldReturnEmptyList(){
        // ARRANGE
        when(userRepository.findAll()).thenReturn(List.of());

        // ACT
        final var response = given()
                .when()
                .get(UserController.PATH_GET_ALL_USERS)
                .then()
                .extract()
                .response();

        // ASSERT
        verify(userRepository, times(1)).findAll();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        final  var expected = List.of();
        final var actual = response.jsonPath().getList("", UserDetailsDto.class);

        assertEquals(expected, actual);
    }

    @Test
    final void test_updateUser_shouldReturn200() {
        //ARRANGE

        final var updatedEntity = getMockedUserEntity(1L, 22);

        when(userRepository.findById(1L)).thenReturn(Optional.of(getMockedUserEntity(1L, 25)));

        when(userRepository.save(any())).thenReturn(updatedEntity);

        //ACT
        final var response = given()
                .body(getMockedCreateOrUpdateUserDto(22))
                .contentType(ContentType.JSON)
                .when()
                .put(UserController.PATH_UPDATE_USER_BY_ID, "1")
                .then()
                .extract()
                .response();

        //ASSERT
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        final var expected = getUserDetailsDto(updatedEntity);
        final var actual = response.getBody().as(UserDetailsDto.class);

        assertEquals(expected, actual);

    }

    @Test
    final void test_updateUser_shouldReturn400() {
        //ARRANGE

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(getMockedUserEntity(1L, 22)));

        when(userRepository.save(any())).thenReturn(Optional.empty());

        //ACT
        final var response = given()
                .body(getMockedCreateOrUpdateUserDto(20))
                .contentType(ContentType.JSON)
                .when()
                .put(UserController.PATH_UPDATE_USER_BY_ID, "1")
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    final void test_updateUser_shouldReturn404() {
        //ARRANGE

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //ACT
        final var response = given()
                .body(getMockedCreateOrUpdateUserDto(22))
                .contentType(ContentType.JSON)
                .when()
                .put(UserController.PATH_UPDATE_USER_BY_ID, "1")
                .then()
                .extract()
                .response();

        //ASSERT

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    private UserEntity getMockedUserEntity(Long id, int age) {
        return UserEntity.builder()
                .id(id)
                .name("Quim")
                .age(age)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("Password#123")
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

    private CreateOrUpdateUserDto getMockedCreateOrUpdateUserDto(int age) {
        return CreateOrUpdateUserDto.builder()
                .name("Quim")
                .age(age)
                .licenseNumber("1238127LSC")
                .ssn(123456789L)
                .planet("Terra")
                .password("Password#123")
                .email("email@email.com")
                .userType(UserType.CUSTOMER)
                .build();
    }
}