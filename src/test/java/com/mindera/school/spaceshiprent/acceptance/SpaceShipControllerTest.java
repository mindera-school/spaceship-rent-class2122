package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.MockedData;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceshipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
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

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateSpaceshipDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedSpaceshipEntity;
import static com.mindera.school.spaceshiprent.MockedData.getSpaceshipDetailsDto;
import static com.mindera.school.spaceshiprent.MockedData.getSpaceshipEntityList;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_SPACESHIPS;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_SPACESHIP_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_POST_SPACESHIP;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_SPACESHIP_BY_ID;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.SPACESHIP_NOT_FOUND;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceShipControllerTest {

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class CreateSpaceship {
        @Test
        void test_createSpaceship_shouldReturn200() {
            // arrange
            SpaceshipEntity spaceShip = getMockedSpaceshipEntity();
            CreateOrUpdateSpaceshipDto dto = getCreateOrUpdateSpaceshipDto();
            Long id = 1L;

            when(spaceshipRepository.save(Mockito.any(SpaceshipEntity.class)))
                    .thenReturn(spaceShip);

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_POST_SPACESHIP)
                    .then().extract().response();

            // assert
            verify(spaceshipRepository, times(1))
                    .save(Mockito.any(SpaceshipEntity.class));

            final var expected = getSpaceshipDetailsDto(spaceShip);
            final var actual = response.getBody().as(SpaceshipDetailsDto.class);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class GetAllSpaceships {
        @Test
        void test_getAll_shouldReturn200() {
            // arrange
            when(spaceshipRepository.findAll()).thenReturn(getSpaceshipEntityList());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_SPACESHIPS)
                    .then().extract().response();

            // assert
            verify(spaceshipRepository, times(1))
                    .findAll();

            final var actual = response.getBody().as(SpaceshipDetailsDto[].class)[0];
            final var expected = getSpaceshipEntityList().stream().map(MockedData::getSpaceshipDetailsDto).toArray();
            assertEquals(expected[0], actual);
        }
    }

    @Nested
    class GetSpaceshipByID {
        @Test
        void test_getSpaceShipById_shouldReturn200() {
            // arrange
            SpaceshipEntity spaceShip = getMockedSpaceshipEntity();
            Long id = 1L;
            when(spaceshipRepository.findById(id))
                    .thenReturn(Optional.of(spaceShip));

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_SPACESHIP_BY_ID, id)
                    .then().extract().response();

            // assert
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());

            SpaceshipDetailsDto expected = getSpaceshipDetailsDto(spaceShip);
            assertEquals(expected, response.getBody().as(SpaceshipDetailsDto.class));
        }

        @Test
        void test_getSpaceShipById_shouldReturn404() {
            //GIVEN
            when(spaceshipRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            //WHEN
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_SPACESHIP_BY_ID, 9L)
                    .then().extract().response();

            //THEN
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode(), "status code");

            final String actualMessage = response.jsonPath().getString("message");
            assertEquals(String.format(SPACESHIP_NOT_FOUND, 9), actualMessage, "exception");
        }
    }

    @Nested
    class UpdateSpaceship {

        @Test
        void test_updateSpaceshipById_shouldReturn200() {
            // arrange
            CreateOrUpdateSpaceshipDto dto = getCreateOrUpdateSpaceshipDto();
            SpaceshipEntity entity = getMockedSpaceshipEntity();

            when(spaceshipRepository.save(Mockito.any(SpaceshipEntity.class)))
                    .thenReturn(entity);
            when(spaceshipRepository.findById(anyLong()))
                    .thenReturn(Optional.of(entity));

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_SPACESHIP_BY_ID, 1L)
                    .then().extract().response();

            // assert
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());
            verify(spaceshipRepository, times(1))
                    .save(Mockito.any(SpaceshipEntity.class));

            assertEquals(getSpaceshipDetailsDto(entity), response.getBody().as(SpaceshipDetailsDto.class));
        }

        @Test
        void test_updateSpaceshipById_shouldReturn404() {
            // arrange
            final CreateOrUpdateSpaceshipDto dto = getCreateOrUpdateSpaceshipDto();

            final Long fakeId = 9L;

            when(spaceshipRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_SPACESHIP_BY_ID, fakeId)
                    .then().extract().response();

            // assert
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final String expectedMessage = String.format(SPACESHIP_NOT_FOUND, fakeId);
            final String actualMessage = response.getBody().jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }
    }
}
