package com.mindera.school.spaceshiprent;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.dto.spaceship.CreateOrUpdateSpaceshipDto;
import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class MockedData {

    public static UserDetailsDto getUserDetailsDto(UserEntity entity) {
        return UserDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .ssn(entity.getSsn())
                .licenseNumber(entity.getLicenseNumber())
                .planet(entity.getPlanet())
                .userType(entity.getUserType())
                .email(entity.getEmail())
                .build();
    }

    public static CreateOrUpdateUserDto getCreateOrUpdateUserDto() {
        return CreateOrUpdateUserDto.builder()
                .name("Rafa")
                .age(20)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("P@ssword123")
                .email("email@email.com")
                .build();
    }

    public static List<UserEntity> getUserList() {
        return Collections.singletonList(getMockedUserEntity());
    }

    public static RentEntity getMockedRentEntity() {
        return RentEntity.builder()
                .id(1L)
                .discount(20)
                .expectedReturnDate(LocalDate.of(2022, 12, 20))
                .expectedPickupDate(LocalDate.of(2022, 10, 20))
                .userEntity(getMockedUserEntity())
                .pricePerDay(12)
                .spaceShipEntity(getMockedSpaceshipEntity())
                .build();
    }

    public static UserEntity getMockedUserEntity() {
        return UserEntity.builder()
                .id(5L)
                .name("Rafa")
                .age(20)
                .ssn(123456789L)
                .licenseNumber("1238127LSC")
                .planet("Terra")
                .userType(UserType.CUSTOMER)
                .password("P@ssword123")
                .email("email@email.com")
                .build();
    }

    public static SpaceshipEntity getMockedSpaceshipEntity() {
        return SpaceshipEntity.builder()
                .id(1L)
                .name("nave")
                .brand("mercedes")
                .model("x5")
                .registerNumber(10)
                .priceDay(12)
                .build();
    }

    public static RentDetailsDto getRentDetailsDto(RentEntity rentEntity) {
        return RentDetailsDto.builder()
                .id(rentEntity.getId())
                .spaceshipId(rentEntity.getSpaceShipEntity().getId())
                .customerId(rentEntity.getUserEntity().getId())
                .expectedPickupDate(rentEntity.getExpectedPickupDate())
                .expectedReturnDate(rentEntity.getExpectedReturnDate())
                .pickDate(rentEntity.getPickupDate())
                .returnDate(rentEntity.getReturnDate())
                .pricePerDay(rentEntity.getPricePerDay())
                .discount(rentEntity.getDiscount())
                .build();
    }

    public static CreateOrUpdateRentDto getMockedCreateOrUpdateRent() {
        CreateOrUpdateRentDto createOrUpdateRentDto = new CreateOrUpdateRentDto();
        createOrUpdateRentDto.setCustomerId(getMockedRentEntity().getUserEntity().getId());
        createOrUpdateRentDto.setSpaceshipId(getMockedRentEntity().getSpaceShipEntity().getId());
        createOrUpdateRentDto.setDiscount(20);
        createOrUpdateRentDto.setExpectedReturnDate(LocalDate.of(2022, 12, 20));
        createOrUpdateRentDto.setExpectedPickupDate(LocalDate.of(2022, 10, 20));
        return createOrUpdateRentDto;

    }

    public static List<RentEntity> getRentEntityList() {
        return Collections.singletonList(getMockedRentEntity());
    }

    public static SpaceShipDetailsDto getSpaceshipDetailsDto(SpaceshipEntity entity) {
        return SpaceShipDetailsDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .registerNumber(entity.getRegisterNumber())
                .priceDay(entity.getPriceDay())
                .build();
    }

    public static CreateOrUpdateSpaceshipDto getCreateOrUpdateSpaceshipDto() {
        return CreateOrUpdateSpaceshipDto.builder()
                .name("nave")
                .brand("mercedes")
                .model("x5")
                .registerNumber(10)
                .priceDay(12)
                .build();
    }

    public static List<SpaceshipEntity> getSpaceshipEntityList() {
        return Collections.singletonList(getMockedSpaceshipEntity());
    }

}
