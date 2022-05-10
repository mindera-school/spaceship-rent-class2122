package com.mindera.school.spaceshiprent.unit.converter;

import com.mindera.school.spaceshiprent.converter.SpaceshipConverter;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    public void test_convertEntityToSpaceshipDetailsDto_shouldReturnSuccess() {
        // GIVEN
        SpaceshipEntity spaceshipEntity = getMockedEntity();

        // WHEN
        SpaceShipDetailsDto detailsDto = spaceshipConverter.convertToSpaceShipDetailsDto(spaceshipEntity);

        // THEN
        assertEquals(getSpaceshipDetailsDto(spaceshipEntity), detailsDto);
    }

    @Test
    public void test_convertDtoToEntity_shouldHaveTheSameValues() {
        // GIVEN
        CreateOrUpdateSpaceshipDto spaceshipDto = getCreateOrUpdateSpaceshipDto();
        SpaceshipEntity mockedEntity = getMockedEntity();
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



    private SpaceshipEntity getMockedEntity() {
        return SpaceshipEntity.builder()
                .id(6L)
                .name("Normandy II")
                .brand("Lambaguini")
                .model("Punto")
                .registerNumber(123456789)
                .priceDay(20)
                .build();
    }

    private CreateOrUpdateSpaceshipDto getCreateOrUpdateSpaceshipDto() {
        return CreateOrUpdateSpaceshipDto.builder()
                .name("Normandy II")
                .brand("Lambaguini")
                .model("Punto")
                .registerNumber(123456789)
                .priceDay(20)
                .build();
    }

    private SpaceShipDetailsDto getSpaceshipDetailsDto(SpaceshipEntity spaceshipEntity) {
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