package com.mindera.school.spaceshiprent.unit.converter;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class RentConverterTest {

    private RentConverter rentConverter;

    @BeforeEach
    public void setup() {
        this.rentConverter = new RentConverter();
    }

    @Test
    public void test_convert_entity_to_rentDetailsDto_should_return_success() {
        // GIVEN
        RentEntity rentEntity = getMockedEntity();

        // WHEN
        RentDetailsDto rentDetailsDto = rentConverter.convertToRentDetailsDto(rentEntity);

        // THEN
        assertEquals(getRentDetailsDto(rentEntity), rentDetailsDto);
    }


    @Test
    public void test_convertDtoToEntity_shouldHaveTheSameValues() {

        // GIVEN
        CreateOrUpdateRentDto rentDto = getCreateOrUpdateRentDto();
        RentEntity mockedEntity = getMockedEntity();
        mockedEntity.setId(null);

        //  WHEN
        RentEntity rentEntity = rentConverter.convertToEntity(rentDto);

        // THEN
        assertEquals(mockedEntity.getId(), rentEntity.getId(), "id");
        assertEquals(mockedEntity.getExpectedPickupDate(), rentEntity.getExpectedPickupDate(), "expected pick up date");
        assertEquals(mockedEntity.getExpectedReturnDate(), rentEntity.getExpectedReturnDate(), "expected return date");
        assertEquals(mockedEntity.getDiscount(), rentEntity.getDiscount(), "discount");
    }



    private RentEntity getMockedEntity() {
        return RentEntity.builder()
                .id(2L)
                .userEntity(getMockedUserEntity())
                .spaceShipEntity(getMockedSpaceshipEntity())
                .expectedPickupDate(LocalDate.of(2022,2,2))
                .expectedReturnDate(LocalDate.of(2022,3,2))
                .pricePerDay(150)
                .discount(0.5f)
                .build();
    }


    private CreateOrUpdateRentDto getCreateOrUpdateRentDto() {
        return CreateOrUpdateRentDto.builder()
                .customerId(10L)
                .spaceshipId(2L)
                .expectedPickupDate(LocalDate.of(2022,2,2))
                .expectedReturnDate(LocalDate.of(2022,3,2))
                .discount(0.50f)
                .build();
    }

    private RentDetailsDto getRentDetailsDto(RentEntity rentEntity) {
        return RentDetailsDto.builder()
                .id(rentEntity.getId())
                .customerId(getMockedUserEntity().getId())
                .spaceshipId(getMockedSpaceshipEntity().getId())
                .expectedPickupDate(rentEntity.getExpectedPickupDate())
                .expectedReturnDate(rentEntity.getExpectedReturnDate())
                .pickDate(rentEntity.getPickupDate())
                .returnDate(rentEntity.getReturnDate())
                .pricePerDay(rentEntity.getPricePerDay())
                .discount(rentEntity.getDiscount())
                .build();
    }

    private SpaceshipEntity getMockedSpaceshipEntity() {
        return SpaceshipEntity.builder()
                .id(6L)
                .name("Normandy II")
                .brand("Lambaguini")
                .model("Punto")
                .registerNumber(123456789)
                .priceDay(20)
                .build();
    }

    private UserEntity getMockedUserEntity() {
        return UserEntity.builder()
                .id(5L)
                .name("Rafa")
                .age(20)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("Password123")
                .email("email@email.com")
                .build();
    }

}
