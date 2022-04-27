package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.controller.RentController;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mindera.school.spaceshiprent.MockedObjects.getMockedCreateRentDto;
import static com.mindera.school.spaceshiprent.MockedObjects.getMockedRentEntity;
import static com.mindera.school.spaceshiprent.MockedObjects.getMockedSpaceshipEntity;
import static com.mindera.school.spaceshiprent.MockedObjects.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedObjects.getRentDetailsDTO;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RentControllerTest extends BaseControllerTest {

    @Nested
    class CreateRentTest {
        @Test
        void test_createRent_shouldReturn200() {
            // ARRANGE
            final var entity = getMockedRentEntity(2L);

            when(userRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedUserEntity()));
            when(spaceshipRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedSpaceshipEntity()));

            when(rentRepository.save(any()))
                    .thenReturn(entity);

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .body(getMockedCreateRentDto())
                    .contentType(ContentType.JSON)
                    .when()
                    .post(RentController.PATH_CREATE_RENT)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var expected = getRentDetailsDTO(entity);
            final var actual = response.getBody().as(RentDetailsDto.class);

            assertEquals(expected, actual);
        }

        @Test
        void test_createRent_userNotFound_shouldReturn404() {
            // ARRANGE
            when(userRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .body(getMockedCreateRentDto())
                    .contentType(ContentType.JSON)
                    .when()
                    .post(RentController.PATH_CREATE_RENT)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var actual = response.getBody().as(SpaceshipRentError.class);

            assertEquals("UserNotFoundException", actual.getException());
        }

        @Test
        void test_createRent_spaceshipNotFound_shouldReturn404() {
            // ARRANGE
            when(userRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedUserEntity()));
            when(spaceshipRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .body(getMockedCreateRentDto())
                    .contentType(ContentType.JSON)
                    .when()
                    .post(RentController.PATH_CREATE_RENT)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var actual = response.getBody().as(SpaceshipRentError.class);

            assertEquals("SpaceshipNotFoundException", actual.getException(),
                    "Exception name");
        }

    }

    @Nested
    class GetRents {

        @Test
        void test_getAllRents_shouldReturn200() {
            // ARRANGE
            final var entity1 = getMockedRentEntity(1L);
            final var entity2 = getMockedRentEntity(2L);
            when(rentRepository.findAll())
                    .thenReturn(Arrays.asList(entity1, entity2));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENTS)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var expected = Arrays.asList(getRentDetailsDTO(entity1), getRentDetailsDTO(entity2));
            final var actual = Arrays.asList(response.getBody().as(RentDetailsDto[].class));

            assertEquals(expected, actual);
        }

        @Test
        void test_getAllRents_emptyList_shouldReturn200() {
            // ARRANGE
            when(rentRepository.findAll())
                    .thenReturn(List.of());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENTS)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var actual = Arrays.asList(response.getBody().as(RentDetailsDto[].class));

            assertTrue(actual.isEmpty(), "list size");
        }

    }

    @Nested
    class GetRentById {

        @Test
        void test_getRentById_shouldReturn200() {
            // ARRANGE
            final var entity = getMockedRentEntity(2L);
            when(rentRepository.findById(2L))
                    .thenReturn(Optional.of(entity));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_ID, 2L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var expected = getRentDetailsDTO(entity);
            final var actual = response.getBody().as(RentDetailsDto.class);

            assertEquals(expected, actual);
        }

        @Test
        void test_getRentById_shouldReturn404() {
            // ARRANGE
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_ID, 1L)
                    .then().extract().response();

            // THEN
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentNotFoundException", errorResponse.getException(),
                    "Exception name");
        }

    }

    @Nested
    class GetRentByCustomerId {

        @Test
        void test_getRentCustomerById_shouldReturn200() {
            // ARRANGE
            when(userRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedUserEntity()));

            final var entity1 = getMockedRentEntity(1L);
            final var entity2 = getMockedRentEntity(2L);
            when(rentRepository.findByUserId(1L))
                    .thenReturn(List.of(entity1, entity2));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_CUSTOMER_ID, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var expected = Arrays.asList(getRentDetailsDTO(entity1), getRentDetailsDTO(entity2));
            final var actual = Arrays.asList(response.getBody().as(RentDetailsDto[].class));

            assertEquals(expected, actual);
        }

        @Test
        void test_getRentByCustomerId_customerNotFound_shouldReturn404() {
            // ARRANGE
            when(userRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_CUSTOMER_ID, 1L)
                    .then().extract().response();

            // THEN
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("UserNotFoundException", errorResponse.getException(),
                    "Exception name");
        }

        @Test
        void test_getRentCustomerById_emptyList_shouldReturn200() {
            // ARRANGE
            when(userRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedUserEntity()));

            when(rentRepository.findByUserId(1L))
                    .thenReturn(List.of());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_CUSTOMER_ID, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var actual = Arrays.asList(response.getBody().as(RentDetailsDto[].class));

            assertTrue(actual.isEmpty(), "List size");
        }

    }

    @Nested
    class GetRentBySpaceshipId {

        @Test
        void test_getRentSpaceshipById_shouldReturn200() {
            // ARRANGE
            when(spaceshipRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedSpaceshipEntity()));

            final var entity1 = getMockedRentEntity(1L);
            final var entity2 = getMockedRentEntity(2L);
            when(rentRepository.findBySpaceshipId(1L))
                    .thenReturn(List.of(entity1, entity2));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_SPACESHIP_ID, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var expected = Arrays.asList(getRentDetailsDTO(entity1), getRentDetailsDTO(entity2));
            final var actual = Arrays.asList(response.getBody().as(RentDetailsDto[].class));

            assertEquals(expected, actual);
        }

        @Test
        void test_getRentBySpaceshipId_spaceshipNotFound_shouldReturn404() {
            // ARRANGE
            when(spaceshipRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_SPACESHIP_ID, 1L)
                    .then().extract().response();

            // THEN
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("SpaceshipNotFoundException", errorResponse.getException(),
                    "Exception name");
        }

        @Test
        void test_getRentSpaceshipById_emptyList_shouldReturn200() {
            // ARRANGE
            when(spaceshipRepository.findById(1L))
                    .thenReturn(Optional.of(getMockedSpaceshipEntity()));

            when(rentRepository.findBySpaceshipId(1L))
                    .thenReturn(List.of());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .get(RentController.PATH_GET_RENT_BY_SPACESHIP_ID, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var actual = Arrays.asList(response.getBody().as(RentDetailsDto[].class));

            assertTrue(actual.isEmpty(), "List size");
        }

    }

    @Nested
    class UpdateRentTest {

        @Test
        void test_updateRent_shouldReturn200() {
            // ARRANGE
            final var entity = getMockedRentEntity(1L);
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.of(entity));
            when(rentRepository.save(any()))
                    .thenReturn(entity);

            final var dto = getMockedCreateRentDto();

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .when()
                    .put(RentController.PATH_UPDATE_RENT, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var actual = response.getBody().as(RentDetailsDto.class);
            assertEquals(getRentDetailsDTO(entity), actual);
        }

        @Test
        void test_updateRent_notFound_shouldReturn404() {
            // ARRANGE
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.empty());

            final var dto = getMockedCreateRentDto();

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dto)
                    .when()
                    .put(RentController.PATH_UPDATE_RENT, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentNotFoundException", errorResponse.getException(),
                    "Exception name");
        }

    }

    @Nested
    class PickupRentTest {

        @Test
        void test_pickupRent_shouldReturn200() {
            // ARRANGE
            final var entity = getMockedRentEntity(1L);
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.of(entity));
            when(rentRepository.save(any()))
                    .thenReturn(entity);

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_PICKUP_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());

            final var actual = response.getBody().as(RentDetailsDto.class);
            assertEquals(getRentDetailsDTO(entity), actual);
        }

        @Test
        void test_pickupRent_notFound_shouldReturn404() {
            // ARRANGE
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_PICKUP_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentNotFoundException", errorResponse.getException(),
                    "Exception name");
        }

        @Test
        void test_pickupRent_rentAlreadyPickedUp_shouldReturn409() {
            // ARRANGE
            final var entity = getMockedRentEntity(1L);
            entity.setPickupDate(LocalDate.now());
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.of(entity));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_PICKUP_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentAlreadyPickedUpException", errorResponse.getException(),
                    "Exception name");
        }

    }

    @Nested
    class ReturnRentTest {

        @Test
        void test_returnRent_shouldReturn200() {
            // ARRANGE
            final var entity = getMockedRentEntity(1L);
            entity.setPickupDate(LocalDate.now());
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.of(entity));
            when(rentRepository.save(any()))
                    .thenReturn(entity);

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_RETURN_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        }

        @Test
        void test_returnRent_notFound_shouldReturn404() {
            // ARRANGE
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.empty());

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_RETURN_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentNotFoundException", errorResponse.getException(),
                    "Exception name");
        }

        @Test
        void test_returnRent_rentNotPickedUp_shouldReturn409() {
            // ARRANGE
            final var entity = getMockedRentEntity(1L);
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.of(entity));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_RETURN_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentNotPickedUpException", errorResponse.getException(),
                    "Exception name");
        }

        @Test
        void test_returnRent_rentAlreadyReturned_shouldReturn409() {
            // ARRANGE
            final var entity = getMockedRentEntity(1L);
            entity.setPickupDate(LocalDate.now());
            entity.setReturnDate(LocalDate.now());
            when(rentRepository.findByIdAndUserId(1L, 1L))
                    .thenReturn(Optional.of(entity));

            // ACT
            final var response = given()
                    .headers(getApiAuthenticatedHeader())
                    .when()
                    .patch(RentController.PATH_RETURN_RENT, 1L, 1L)
                    .then().extract().response();

            // ASSERT
            assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());

            final var errorResponse = response.getBody().as(SpaceshipRentError.class);

            assertEquals("RentAlreadyReturnedException", errorResponse.getException(),
                    "Exception name");
        }

    }
}
