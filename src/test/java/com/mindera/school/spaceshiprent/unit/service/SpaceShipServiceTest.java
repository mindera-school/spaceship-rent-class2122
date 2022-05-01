package com.mindera.school.spaceshiprent.unit.service;


import com.mindera.school.spaceshiprent.MockedData;
import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.service.spaceship.SpaceShipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateSpaceshipDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedSpaceshipEntity;
import static com.mindera.school.spaceshiprent.MockedData.getSpaceshipDetailsDto;
import static com.mindera.school.spaceshiprent.MockedData.getSpaceshipEntityList;
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

    @Nested
    class GetSpaceshipById {
        @Test
        public void test_getSpaceShipById_shouldReturnSuccess() {
            //GIVEN
            Long spaceShipId = 1L;

            SpaceshipEntity entity = getMockedSpaceshipEntity();

            when(spaceshipRepository.findById(spaceShipId))
                    .thenReturn(Optional.of(entity));

            //WHEN
            SpaceShipDetailsDto response = spaceShipService.getSpaceShipById(spaceShipId);

            //THEN
            assertEquals(getSpaceshipDetailsDto(entity), response);

        }

        @Test
        public void test_getSpaceShipById_shouldReturn_NotFound() {
            //GIVEN
            when(spaceshipRepository.findById(5L))
                    .thenReturn(Optional.empty());

            //WHEN
            Executable action = () -> spaceShipService.getSpaceShipById(5L);

            //THEN
            assertThrows(SpaceshipNotFoundException.class, action);
        }
    }

    @Nested
    class CreateSpaceship {
        @Test
        public void test_createSpaceShip_shouldReturn_Success() {
            SpaceshipEntity entity = getMockedSpaceshipEntity();

            when(spaceshipRepository.save(Mockito.any(SpaceshipEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

            SpaceShipDetailsDto response = spaceShipService.createSpaceShip(getCreateOrUpdateSpaceshipDto());
            response.setId(getSpaceshipDetailsDto(entity).getId());

            assertEquals(getSpaceshipDetailsDto(entity), response);
        }
    }

    @Nested
    class GetAllSpaceships {
        @Test
        public void test_getAllSpaceships_shouldReturn_Success() {
            when(spaceshipRepository.findAll()).thenReturn(getSpaceshipEntityList());

            List<SpaceShipDetailsDto> spaceShipList = getSpaceshipEntityList().stream().map(MockedData::getSpaceshipDetailsDto).collect(Collectors.toList());

            List<SpaceShipDetailsDto> response = spaceShipService.getAllSpaceShips();

            assertEquals(spaceShipList, response);
        }
    }

    @Nested
    class UpdateSpaceshipById {
        @Test
        public void test_updateSpaceshipById_shouldReturn_Success() {
            SpaceshipEntity entity = getMockedSpaceshipEntity();

            when(spaceshipRepository.findById(1L)).thenReturn(Optional.of(entity));
            when(spaceshipRepository.save(Mockito.any(SpaceshipEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

            SpaceShipDetailsDto response = spaceShipService.updateSpaceShipById(1L, getCreateOrUpdateSpaceshipDto());
            response.setId(getSpaceshipDetailsDto(entity).getId());

            assertEquals(getSpaceshipDetailsDto(entity), response);
        }

        @Test
        public void test_updateSpaceshipById_shouldReturn_Error() {
            when(spaceshipRepository.findById(1L)).thenReturn(Optional.empty());

            Executable action = () -> spaceShipService.updateSpaceShipById(1L, getCreateOrUpdateSpaceshipDto());

            assertThrows(SpaceshipNotFoundException.class, action);
        }
    }

}

