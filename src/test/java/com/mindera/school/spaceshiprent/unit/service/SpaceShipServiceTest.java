package com.mindera.school.spaceshiprent.unit.service;


import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpaceShipServiceTest {

    private SpaceShipServiceImpl spaceShipService;

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @BeforeEach
    public void setup() {
        this.spaceShipService = new SpaceShipServiceImpl(
                new SpaceshipConverter(), spaceshipRepository);
    }

    @Test
    public void test_getSpaceShipById_shouldReturnSuccess() {
        //GIVEN
        Long spaceShipId = 5L;

        SpaceshipEntity entity = getMockedEntity();

        when(spaceshipRepository.findById(spaceShipId))
                .thenReturn(Optional.of(entity));

        //WHEN
        SpaceShipDetailsDto response = spaceShipService.getSpaceShipById(spaceShipId);

        //THEN
        assertEquals(getSpaceShipDetailsDto(entity),response);

    }

    @Test
    public void test_getSpaceShipById_shouldReturn_NotFound(){
        //GIVEN
        when(spaceshipRepository.findById(5L))
                .thenReturn(Optional.empty());

        //WHEN
        Executable action = () -> spaceShipService.getSpaceShipById(5L);

        //THEN
        assertThrows(SpaceshipNotFoundException.class, action);
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

        private SpaceShipDetailsDto getSpaceShipDetailsDto(SpaceshipEntity spaceshipEntity){
            return SpaceShipDetailsDto.builder()
                    .id(spaceshipEntity.getId())
                    .name(spaceshipEntity.getName())
                    .brand(spaceshipEntity.getBrand())
                    .model(spaceshipEntity.getModel())
                    .registerNumber(spaceshipEntity.getRegisterNumber())
                    .priceDay(spaceshipEntity.getPriceDay())
                    .build();
        }
    }

