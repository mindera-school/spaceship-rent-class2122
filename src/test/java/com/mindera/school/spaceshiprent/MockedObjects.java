package com.mindera.school.spaceshiprent;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public abstract class MockedObjects {

    private MockedObjects() {
    }

    public static UserEntity getMockedUserEntity() {
        return UserEntity.builder()
                .id(1L)
                .name("Manel")
                .age(20)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("Password123")
                .email("email@email.com")
                .build();
    }

    public static UserEntity getMockedUserEntityWithEncryptedPassword(String password,
                                                                      PasswordEncoder passwordEncoder) {
        final var userEntity = getMockedUserEntity();
        final var encodedPassword = passwordEncoder.encode(password);
        userEntity.setPassword(encodedPassword);
        return userEntity;
    }

    public static SpaceshipEntity getMockedSpaceshipEntity () {
        return SpaceshipEntity.builder()
                .id(1L)
                .name("Corsie")
                .brand("Axeus Mk5")
                .model("Corsair")
                .registerNumber(123234345)
                .priceDay(150)
                .build();
    }

    public static RentEntity getMockedRentEntity(Long id) {
        return RentEntity.builder()
                .id(id)
                .userEntity(getMockedUserEntity())
                .spaceshipEntity(getMockedSpaceshipEntity())
                .expectedPickupDate(LocalDate.of(2022,11,12))
                .expectedReturnDate(LocalDate.of(2022,12,13))
                .pricePerDay(150)
                .discount(0)
                .build();
    }

    public static CreateOrUpdateRentDto getMockedCreateRentDto() {
        CreateOrUpdateRentDto dto = new CreateOrUpdateRentDto();
        dto.setCustomerId(1L);
        dto.setSpaceshipId(1L);
        dto.setExpectedPickupDate(LocalDate.of(2022,12,12));
        dto.setExpectedReturnDate(LocalDate.of(2022,12,13));
        dto.setDiscount(0);
        return dto;
    }

    public static RentDetailsDto getRentDetailsDTO(RentEntity entity) {
        return RentDetailsDto.builder()
                .id(entity.getId())
                .customerId(entity.getUserEntity().getId())
                .spaceshipId(entity.getSpaceshipEntity().getId())
                .expectedPickupDate(entity.getExpectedPickupDate())
                .expectedReturnDate(entity.getExpectedReturnDate())
                .pickDate(entity.getPickupDate())
                .returnDate(entity.getReturnDate())
                .pricePerDay(entity.getPricePerDay())
                .build();
    }
}
