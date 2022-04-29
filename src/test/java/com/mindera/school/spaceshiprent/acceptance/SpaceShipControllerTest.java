package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceShipControllerTest {

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @MockBean
    private UserRepository userRepository;

    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void test_createSpaceship_shouldReturn200() {
        //GIVEN
        final var spaceShip = getMockedEntity();
        final var user = getMockedUserEntity();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(spaceshipRepository.save(spaceShip)).thenReturn(Optional.of(spaceShip));


        //WHEN
        final var response = given()
                .body(getCreateOrUpdateSpaceshipDto(spaceShip))
                .contentType(ContentType.JSON)
                .when()
                .post("/spaceships").peek()
                .then()
                .extract()
                .response();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final var actual = response.body().as(SpaceShipDetailsDto.class);
        final var expected = getSpaceShipDetailsDto(spaceShip);
        assertEquals(expected, actual);
    }

    @Test
    public void test_createSpaceship_shouldReturn400() {
        //GIVEN
        final var spaceShip = getMockedEntity();
        final var user = getMockedUserEntity();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(spaceshipRepository.save(any(SpaceshipEntity.class))).thenReturn(getMockedEntity());
        final var response = given()
                .body(getCreateOrUpdateSpaceshipDto(spaceShip))
                .contentType(ContentType.JSON)
                .when()
                .post("/spaceships").peek()
                .then()
                .extract()
                .response();

        //THEN
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
        final var actual = response.body().as(SpaceShipDetailsDto.class);
        final var expected = getSpaceShipDetailsDto(spaceShip);
        assertEquals(expected, actual);
    }


    @Test
    public void test_getspaceShipById_shouldReturn200() {
        //GIVEN
        final var spaceShip = getMockedEntity();
        when(spaceshipRepository.findById(5L)).thenReturn(Optional.of(spaceShip));

        //WHEN
        final var response = given()
                .when()
                .get("/spaceships/{id}", "5")
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final var expected = getSpaceShipDetailsDto(spaceShip);
        final var actual = response.body().as(SpaceShipDetailsDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void test_getspaceShipById_shouldReturn404() {

        //GIVEN
        when(spaceshipRepository.findById(5L)).thenReturn(Optional.empty());

        //WHEN
        final var response = given()
                .when()
                .get("/spaceships/{id}", "5")
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void test_getAllSpaceShips_shouldReturn200() {

        //GIVEN
        SpaceshipEntity spaceShip1 = getMockedEntity();
        SpaceshipEntity spaceShip2 = getMockedEntity();

        when(spaceshipRepository.findAll())
                .thenReturn(Arrays.asList(spaceShip1, spaceShip2));
        String path = "/spaceships";

        //WHEN
        final var response = given()
                .when()
                .get(path)
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final var expected = Arrays.asList(getSpaceShipDetailsDto(spaceShip1), getSpaceShipDetailsDto(spaceShip2));
        final var actual = response.jsonPath().getList("", SpaceShipDetailsDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void test_getAllSpaceShips_shouldReturnEmptyList() {

        //GIVEN
        when(spaceshipRepository.findAll())
                .thenReturn(List.of());

        //WHEN
        final var response = given()
                .when()
                .get("/spaceships")
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final var expected = List.of();
        final var actual = response.jsonPath().getList("", SpaceShipDetailsDto.class);
        assertEquals(expected, actual);
    }




    private SpaceshipEntity getMockedEntity() {
        return SpaceshipEntity.builder()
                .id(5L)
                .name("nave")
                .brand("mercedes")
                .model("x5")
                .registerNumber(10)
                .priceDay(12)
                .build();
    }
    private UserEntity getMockedUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .name("Manel")
                .age(20)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("Password123")
                .email("email@email.com")
                .build();
    }


    private SpaceShipDetailsDto getSpaceShipDetailsDto(SpaceshipEntity entity) {
        return SpaceShipDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .registerNumber(entity.getRegisterNumber())
                .priceDay(entity.getPriceDay())
                .build();
    }

    private CreateOrUpdateSpaceshipDto getCreateOrUpdateSpaceshipDto(SpaceshipEntity entity) {
        return CreateOrUpdateSpaceshipDto.builder()
                .name(entity.getName())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .registerNumber(entity.getRegisterNumber())
                .priceDay(entity.getPriceDay())
                .build();
    }
}