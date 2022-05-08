package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.MockedData;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static com.mindera.school.spaceshiprent.MockedData.getMockedCreateOrUpdateRent;
import static com.mindera.school.spaceshiprent.MockedData.getMockedRentEntity;
import static com.mindera.school.spaceshiprent.MockedData.getMockedSpaceshipEntity;
import static com.mindera.school.spaceshiprent.MockedData.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedData.getRentDetailsDto;
import static com.mindera.school.spaceshiprent.MockedData.getRentEntityList;
import static com.mindera.school.spaceshiprent.MockedData.getSpaceshipWithRents;
import static com.mindera.school.spaceshiprent.MockedData.getUserWithRents;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENTS;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENTS_BY_CUSTOMER;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENTS_BY_SPACESHIP;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_GET_RENT_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_POST_RENT;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_PICK_UP_DATE;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_RENT_BY_ID;
import static com.mindera.school.spaceshiprent.controller.Paths.PATH_UPDATE_RETURN_DATE;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.RENT_NOT_FOUND;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.SPACESHIP_NOT_FOUND;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.SPACESHIP_UNAVAILABLE;
import static com.mindera.school.spaceshiprent.exception.ErrorMessageConstants.USER_NOT_FOUND;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @MockBean
    private RentRepository rentRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class CreateRent {
        @Test
        void test_createRent_shouldReturn200() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.save(Mockito.any(RentEntity.class)))
                    .thenReturn(entity);

            when(userRepository.findById(dto.getCustomerId()))
                    .thenReturn(Optional.of(getMockedUserEntity()));

            when(spaceshipRepository.findById(dto.getSpaceshipId()))
                    .thenReturn(Optional.of(getMockedSpaceshipEntity()));

            when(rentRepository.checkRentAvailability(any(), any(), anyLong()))
                    .thenReturn(true);

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_POST_RENT)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .save(any());
            verify(userRepository, times(1))
                    .findById(anyLong());
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());
            verify(rentRepository, times(1))
                    .checkRentAvailability(any(), any(), anyLong());

            assertEquals(getRentDetailsDto(entity), response.getBody().as(RentDetailsDto.class));
        }

        @Test
        void test_createRent_shouldReturn400_spaceshipNotAvailable() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();

            when(userRepository.findById(dto.getCustomerId()))
                    .thenReturn(Optional.of(getMockedUserEntity()));

            when(spaceshipRepository.findById(dto.getSpaceshipId()))
                    .thenReturn(Optional.of(getMockedSpaceshipEntity()));

            when(rentRepository.checkRentAvailability(any(), any(), anyLong()))
                    .thenReturn(false);

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_POST_RENT)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());
            verify(rentRepository, times(1))
                    .checkRentAvailability(any(), any(), anyLong());

            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

            final String actualMessage = response.getBody().jsonPath().getString("message");
            final String expectedMessage = String.format(SPACESHIP_UNAVAILABLE,
                    dto.getSpaceshipId(),
                    dto.getExpectedPickupDate(),
                    dto.getExpectedReturnDate()
            );

            assertEquals(expectedMessage, actualMessage);
        }

        @Test
        void test_createRent_shouldReturn404_userNotFound() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();

            when(userRepository.findById(dto.getCustomerId()))
                    .thenReturn(Optional.empty());
            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_POST_RENT)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());
            verify(spaceshipRepository, times(0))
                    .findById(anyLong());
            verify(rentRepository, times(0))
                    .checkRentAvailability(any(), any(), anyLong());
            verify(rentRepository, times(0))
                    .save(any());

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final String actualMessage = response.getBody().jsonPath().getString("message");
            final String expectedMessage = String.format(USER_NOT_FOUND,
                    dto.getCustomerId()
            );

            assertEquals(expectedMessage, actualMessage);
        }

        @Test
        void test_createRent_shouldReturn404_spaceshipNotFound() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();

            when(userRepository.findById(dto.getCustomerId()))
                    .thenReturn(Optional.of(getMockedUserEntity()));

            when(spaceshipRepository.findById(dto.getSpaceshipId()))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .body(dto)
                    .contentType(ContentType.JSON)
                    .when()
                    .post(PATH_POST_RENT)
                    .then().extract().response();

            // assert
            verify(userRepository, times(1))
                    .findById(anyLong());
            verify(spaceshipRepository, times(1))
                    .findById(anyLong());
            verify(rentRepository, times(0))
                    .checkRentAvailability(any(), any(), anyLong());

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final String actualMessage = response.getBody().jsonPath().getString("message");
            final String expectedMessage = String.format(SPACESHIP_NOT_FOUND,
                    dto.getSpaceshipId()
            );

            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Nested
    class GetAllRents {

        @Test
        public void test_getAllRents_shouldReturn200() {
            // arrange
            when(rentRepository.findAll())
                    .thenReturn(getRentEntityList());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENTS)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findAll();

            final var expected = getRentEntityList().stream().map(MockedData::getRentDetailsDto).toArray();
            final var actual = response.getBody().as(RentDetailsDto[].class);

            assertEquals(expected[0], actual[0]);
        }

        @Test
        public void test_getAllRents_shouldReturnEmpty() {
            // arrange
            when(rentRepository.findAll())
                    .thenReturn(new ArrayList<>());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENTS)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findAll();

            final var expected = getRentEntityList().stream().map(MockedData::getRentDetailsDto).toArray();
            final var actual = response.getBody().as(RentDetailsDto[].class);

            assertEquals(expected[0], actual[0]);
        }
    }

    @Nested
    class GetRentById {
        @Test
        public void test_getRentById_shouldReturn200() {
            // arrange
            RentEntity entity = getMockedRentEntity();
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.of(entity));

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENT_BY_ID, 1L)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findById(anyLong());

            RentDetailsDto expected = getRentDetailsDto(entity);
            assertEquals(expected, response.getBody().as(RentDetailsDto.class));
        }

        @Test
        public void test_getRentById_shouldReturn404() {
            // arrange
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .when()
                    .get(PATH_GET_RENT_BY_ID, 1L)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findById(anyLong());

            assertEquals(HttpStatus.NOT_FOUND.value(),
                    response.getStatusCode(),
                    "status code");

            final String expectedMessage = String.format(RENT_NOT_FOUND, 1L);
            final String actualMessage = response.getBody().jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Nested
    class UpdateRent {
        @Test
        void test_updateRent_shouldReturn200() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(anyLong()))
                    .thenReturn(Optional.of(entity));

            when(rentRepository.checkRentAvailability(any(), any(), anyLong()))
                    .thenReturn(true);

            when(rentRepository.save(Mockito.any(RentEntity.class)))
                    .thenReturn(entity);

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .when()
                    .put(PATH_UPDATE_RENT_BY_ID, 1L)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findById(anyLong());

            verify(rentRepository, times(1))
                    .checkRentAvailability(any(), any(), anyLong());

            verify(rentRepository, times(1))
                    .save(Mockito.any(RentEntity.class));

            final var actual = response.getBody().as(RentDetailsDto.class);

            assertEquals(dto.getExpectedReturnDate(), actual.getExpectedReturnDate());
            assertEquals(dto.getExpectedPickupDate(), actual.getExpectedPickupDate());
            assertEquals(dto.getDiscount(), actual.getDiscount());
        }

        @Test
        void test_updateRent_shouldReturn400_spaceshipUnavailable() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(anyLong()))
                    .thenReturn(Optional.of(entity));

            when(rentRepository.checkRentAvailability(any(), any(), anyLong()))
                    .thenReturn(false);

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .when()
                    .put(PATH_UPDATE_RENT_BY_ID, 1L)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findById(anyLong());

            verify(rentRepository, times(1))
                    .checkRentAvailability(any(), any(), anyLong());

            verify(rentRepository, times(0))
                    .save(Mockito.any(RentEntity.class));

            assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());

            final var actualMessage = response.getBody().jsonPath().getString("message");
            final var expectedMessage = String.format(SPACESHIP_UNAVAILABLE, dto.getSpaceshipId(),
                    dto.getExpectedPickupDate(),
                    dto.getExpectedReturnDate());

            assertEquals(expectedMessage, actualMessage);
        }

        @Test
        void test_updateRent_shouldReturn404_rentNotFound() {
            // arrange
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();
            final Long id = 1L;
            when(rentRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .body(dto)
                    .when()
                    .put(PATH_UPDATE_RENT_BY_ID, id)
                    .then().extract().response();

            // assert
            verify(rentRepository, times(1))
                    .findById(anyLong());

            verify(rentRepository, times(0))
                    .checkRentAvailability(any(), any(), anyLong());

            verify(rentRepository, times(0))
                    .save(Mockito.any(RentEntity.class));

            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var actualMessage = response.getBody().jsonPath().getString("message");
            final var expectedMessage = String.format(RENT_NOT_FOUND, id);

            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Nested
    class GetRentsByCostumerId {
        @Test
        void test_getRentByCostumerId_shouldReturn2OO() {
            // arrange
            final Long id = 5L;

            when(userRepository.findById(id))
                    .thenReturn(Optional.of(getUserWithRents()));

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENTS_BY_CUSTOMER, 5L)
                    .then().extract().response();

            // assert
            final var expected = getRentEntityList().stream()
                    .map(MockedData::getRentDetailsDto)
                    .toArray();

            final var actual = response.getBody().as(RentDetailsDto[].class);
            assertEquals(expected[0], actual[0]);
        }

        @Test
        void test_getRentByCostumerId_shouldReturn4OO() {
            // arrange
            final Long id = 5L;

            when(userRepository.findById(id))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENTS_BY_CUSTOMER, 5L)
                    .then().extract().response();

            // assert
            final var expectedMessage = String.format(USER_NOT_FOUND, id);
            final var actualMessage = response.getBody().jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Nested
    class GetRentsBySpaceshipId {
        @Test
        void test_getRentBySpaceshipId_shouldReturn2OO() {
            // arrange
            final Long id = 5L;

            when(spaceshipRepository.findById(id))
                    .thenReturn(Optional.of(getSpaceshipWithRents()));

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENTS_BY_SPACESHIP, 5L)
                    .then().extract().response();

            // assert
            final var expected = getRentEntityList().stream()
                    .map(MockedData::getRentDetailsDto)
                    .toArray();

            final var actual = response.getBody().as(RentDetailsDto[].class);
            assertEquals(expected[0], actual[0]);
        }

        @Test
        void test_getRentBySpaceshipId_shouldReturn404() {
            // arrange
            final Long id = 5L;

            when(spaceshipRepository.findById(id))
                    .thenReturn(Optional.empty());

            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .get(PATH_GET_RENTS_BY_SPACESHIP, 5L)
                    .then().extract().response();

            // assert
            final var expectedMessage = String.format(SPACESHIP_NOT_FOUND, id);
            final var actualMessage = response.getBody().jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }
    }

    @Nested
    class UpdatePickUpDate {
        @Test
        void test_updatePickUpDate_shouldReturn200() {
            // arrange
            final Long id = 1L;
            when(rentRepository.findById(id))
                    .thenReturn(Optional.of(getMockedRentEntity()));
            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_PICK_UP_DATE, id)
                    .then().extract().response();

            // assert
            final var expected = getRentDetailsDto(getMockedRentEntity());
            expected.setPickDate(LocalDate.now());
            assertEquals(expected, response.getBody().as(RentDetailsDto.class));
        }

        @Test
        void test_updatePickUpDate_shouldReturn404() {
            // arrange
            final Long id = 1L;
            when(rentRepository.findById(id))
                    .thenReturn(Optional.empty());
            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_PICK_UP_DATE, id)
                    .then().extract().response();

            // assert
            final var expectedMessage = String.format(RENT_NOT_FOUND, id);
            final var actualMessage = response.getBody().jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }

    }

    @Nested
    class UpdateReturnDate {
        @Test
        void test_updatePickUpDate_shouldReturn200() {
            // arrange
            final Long id = 1L;
            when(rentRepository.findById(id))
                    .thenReturn(Optional.of(getMockedRentEntity()));
            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_RETURN_DATE, id)
                    .then().extract().response();

            // assert
            final var expected = getRentDetailsDto(getMockedRentEntity());
            expected.setReturnDate(LocalDate.now());
            assertEquals(expected, response.getBody().as(RentDetailsDto.class));
        }

        @Test
        void test_updateReturnDate_shouldReturn404() {
            // arrange
            final Long id = 1L;
            when(rentRepository.findById(id))
                    .thenReturn(Optional.empty());
            // act
            final var response = given()
                    .port(port)
                    .contentType(ContentType.JSON)
                    .when()
                    .put(PATH_UPDATE_RETURN_DATE, id)
                    .then().extract().response();

            // assert
            final var expectedMessage = String.format(RENT_NOT_FOUND, id);
            final var actualMessage = response.getBody().jsonPath().getString("message");

            assertEquals(expectedMessage, actualMessage);
        }
    }
}
