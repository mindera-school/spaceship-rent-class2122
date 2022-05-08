package com.mindera.school.spaceshiprent.unit.converter;

import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceshipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateSpaceshipDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedSpaceshipEntity;
import static com.mindera.school.spaceshiprent.MockedData.getSpaceshipDetailsDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class SpaceshipConverterTest {

    private SpaceshipConverter spaceshipConverter;

    @BeforeEach
    public void setup() {
        this.spaceshipConverter = new SpaceshipConverter();
    }

    @Nested
    class ConvertToSpaceshipDetailsDto {
        @Test
        public void test_convertEntityToSpaceshipDetailsDto_shouldReturnSuccess() {
            // GIVEN
            SpaceshipEntity spaceshipEntity = getMockedSpaceshipEntity();

            // WHEN
            SpaceshipDetailsDto detailsDto = spaceshipConverter.convertToSpaceShipDetailsDto(spaceshipEntity);

            // THEN
            assertEquals(getSpaceshipDetailsDto(spaceshipEntity), detailsDto);
        }
    }

    @Nested
    class ConvertToEntity {
        @Test
        public void test_convertDtoToEntity_shouldHaveTheSameValues() {
            // GIVEN
            CreateOrUpdateSpaceshipDto spaceshipDto = getCreateOrUpdateSpaceshipDto();
            SpaceshipEntity mockedEntity = getMockedSpaceshipEntity();
            mockedEntity.setId(null);

            //  WHEN
            SpaceshipEntity spaceshipEntity = spaceshipConverter.convertToEntity(spaceshipDto);

            // THEN
            assertEquals(mockedEntity.getId(), spaceshipEntity.getId(), "id");
            assertEquals(mockedEntity.getName(), spaceshipEntity.getName(), "name");
            assertEquals(mockedEntity.getBrand(), spaceshipEntity.getBrand(), "brand");
            assertEquals(mockedEntity.getModel(), spaceshipEntity.getModel(), "model");
            assertEquals(mockedEntity.getRegisterNumber(), spaceshipEntity.getRegisterNumber(), "register number");
            assertEquals(mockedEntity.getPriceDay(), spaceshipEntity.getPriceDay(), "price per day");
        }

        @Test
        public void convertToEntity_shouldNotHaveId() {
            // GIVEN
            CreateOrUpdateSpaceshipDto spaceshipDto = getCreateOrUpdateSpaceshipDto();

            // WHEN
            SpaceshipEntity spaceshipEntity = spaceshipConverter.convertToEntity(spaceshipDto);

            //THEN
            assertNull(spaceshipEntity.getId());
        }
    }

}