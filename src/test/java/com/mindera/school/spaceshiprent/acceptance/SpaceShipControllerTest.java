package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceShipControllerTest {

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_getspaceShipById_shouldReturn200(){
        //GIVEN
        SpaceshipEntity spaceShip = getMockedEntity();
        when(spaceshipRepository.findById(5L))
                .thenReturn(Optional.of(spaceShip));
        String path = "/spaceships/5";

        //WHEN
        ResponseEntity<SpaceShipDetailsDto> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                SpaceShipDetailsDto.class);

        //THEN
        verify(spaceshipRepository,times(1))
                .findById(anyLong());

        SpaceShipDetailsDto expected = getSpaceShipDetailsDto(spaceShip);
        assertEquals(expected, response.getBody());

    }

    @Test
    public void test_getspaceShipById_shouldReturn404(){
        //GIVEN
        when(spaceshipRepository.findById(5L))
                .thenReturn(Optional.empty());
        String path = "/spaceships/5";

        //WHEN
        ResponseEntity<SpaceshipRentError> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                SpaceshipRentError.class);

        //THEN
        verify(spaceshipRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND,
                response.getStatusCode(),
                "status code");
        assertEquals("SpaceshipNotFoundException",
                Objects.requireNonNull(response.getBody()).getException(),
                "exception name");
    }

    @Test
    public void test_getAllRents_shouldReturn200() {

        // GIVEN
        SpaceshipEntity entity1 = getMockedEntity();
        SpaceshipEntity entity2 = getMockedEntity();

        when(spaceshipRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));
        String path = "/rents";

        // WHEN
        ResponseEntity<List<SpaceShipDetailsDto>> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<SpaceShipDetailsDto>>() {
                });

        // THEN
        verify(spaceshipRepository, times(1))
                .findAll();

        List<RentDetailsDto> expected = Arrays.asList(getRentDetailsDTO(entity1), getRentDetailsDTO(entity2));
        assertEquals(expected, response.getBody());

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
