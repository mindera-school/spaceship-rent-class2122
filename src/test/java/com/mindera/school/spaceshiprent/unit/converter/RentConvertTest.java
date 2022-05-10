package com.mindera.school.spaceshiprent.unit.converter;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.mindera.school.spaceshiprent.MockedData.getMockedCreateOrUpdateRent;
import static com.mindera.school.spaceshiprent.MockedData.getMockedRentEntity;
import static com.mindera.school.spaceshiprent.MockedData.getRentDetailsDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RentConvertTest {

    private RentConverter converter;

    @BeforeEach
    public void setup() {
        this.converter = new RentConverter();
    }

    @Nested
    class ConvertToSpaceshipDetailsDto {
        @Test
        void test_convertEntityToSpaceshipDetailsDto_shouldReturnSuccess() {
            // GIVEN
            RentEntity entity = getMockedRentEntity();

            // WHEN
            RentDetailsDto detailsDto = converter.convertToRentDetailsDto(entity);

            // THEN
            assertEquals(getRentDetailsDto(entity), detailsDto);
        }
    }

    @Nested
    class ConvertToEntity {
        @Test
        void test_convertDtoToEntity_shouldHaveTheSameValues() {
            // GIVEN
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();
            RentEntity mockedEntity = getMockedRentEntity();
            mockedEntity.setId(null);

            //  WHEN
            RentEntity rentEntity = converter.convertToEntity(dto);

            // THEN
            assertEquals(mockedEntity.getExpectedPickupDate(), rentEntity.getExpectedPickupDate(), "expected pick up date");
            assertEquals(mockedEntity.getExpectedReturnDate(), rentEntity.getExpectedReturnDate(), "return ");
            assertEquals(mockedEntity.getDiscount(), rentEntity.getDiscount(), "discount");
        }

        @Test
        void convertToEntity_shouldNotHaveId() {
            // GIVEN
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();

            // WHEN
            RentEntity rentEntity = converter.convertToEntity(dto);

            //THEN
            assertNull(rentEntity.getId());
        }
    }
}
