package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.mindera.school.spaceshiprent.controller.SpaceshipController.PATH_GET_SPACESHIP_BY_ID;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SpaceshipControllerTest extends BaseControllerTest {

    @Test
    void test_getspaceshipById_shouldReturn200(){
        //GIVEN
        SpaceshipEntity spaceShip = getMockedEntity();
        when(spaceshipRepository.findById(5L))
                .thenReturn(Optional.of(spaceShip));

        //WHEN
        final var response = given()
                .headers(getApiAuthenticatedHeader())
                .when()
                .get(PATH_GET_SPACESHIP_BY_ID, 5L)
                .then().extract().response();

        //THEN
        verify(spaceshipRepository,times(1))
                .findById(anyLong());

        SpaceShipDetailsDto expected = getSpaceShipDetailsDto(spaceShip);
        assertEquals(expected, response.getBody().as(SpaceShipDetailsDto.class));
    }

    @Test
    void test_getspaceShipById_shouldReturn404(){
        //GIVEN
        when(spaceshipRepository.findById(5L))
                .thenReturn(Optional.empty());

        //WHEN
        final var response = given()
                .headers(getApiAuthenticatedHeader())
                .when()
                .get(PATH_GET_SPACESHIP_BY_ID, 5L)
                .then().extract().response();

        //THEN
        verify(spaceshipRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode(),
                "status code");
        assertEquals("SpaceshipNotFoundException",
                response.getBody().as(SpaceshipRentError.class).getException(),
                "exception name");
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

    private SpaceShipDetailsDto getSpaceShipDetailsDto(SpaceshipEntity entity){
        return SpaceShipDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .registerNumber(entity.getRegisterNumber())
                .priceDay(entity.getPriceDay())
                .build();
    }



}
