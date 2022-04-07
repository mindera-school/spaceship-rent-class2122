package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.controller.SpaceShipController;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpaceShipControllerTest {

    @MockBean
    private SpaceshipRepository spaceShipRepository;

    @MockBean
    private SpaceShipController controllerSpaceShip;

    @MockBean
    private SpaceShipServiceImpl spaceShipService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_createSpaceShip_shouldReturnUserDetailsDTO() {

        SpaceshipEntity entity = getMockedEntity();
      when(controllerSpaceShip.createSpaceship(getSpaceShipDetailsDto(entity))).thenReturn(ResponseEntity.ok(getMockedDto(entity)));

        String path = "/spaceships";

        ResponseEntity<SpaceShipDetailsDto> response = restTemplate
                .exchange(path, HttpMethod.POST, HttpEntity.EMPTY,
                SpaceShipDetailsDto.class);

        verify(controllerSpaceShip, times(1))
                .createSpaceship(getSpaceShipDetailsDto(entity));

        SpaceShipDetailsDto expected = getMockedDto(entity);

        assertEquals(expected,response.getBody());

    }


    public SpaceshipEntity getMockedEntity() {
        return SpaceshipEntity.builder()
                .id(2L)
                .name("Kitty")
                .brand("Hello")
                .model("cat")
                .registerNumber(12345)
                .priceDay(75)
                .rentEntity(anyList())
                .build();
    }


    public SpaceShipDetailsDto getMockedDto(SpaceshipEntity entity) {
        return SpaceShipDetailsDto.builder()
                .name(entity.getName())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .registerNumber(entity.getRegisterNumber())
                .priceDay(entity.getPriceDay())
                .build();
    }

    public CreateOrUpdateSpaceshipDto getSpaceShipDetailsDto(SpaceshipEntity entity) {
        CreateOrUpdateSpaceshipDto rui = new CreateOrUpdateSpaceshipDto();
        rui.setName(entity.getName());
        rui.setBrand(entity.getBrand());
        rui.setModel(entity.getModel());
        rui.setRegisterNumber(entity.getRegisterNumber());
        rui.setPriceDay(entity.getPriceDay());
        return rui;
    }
}