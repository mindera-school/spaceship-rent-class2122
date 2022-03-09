package com.mindera.school.spaceshiprent.converter;


import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;

public class RentConverter {
    public static RentEntity fromCreateOrUpdateRentDto(CreateOrUpdateRentDto createOrUpdateRentDto) {
        return RentEntity.builder()
                .expectedPickupDate(createOrUpdateRentDto.getExpectedPickupDate())
                .expectedReturnDate(createOrUpdateRentDto.getExpectedReturnDate())
                .pricePerDay(createOrUpdateRentDto.getPricePerDay())
                .discount(createOrUpdateRentDto.getDiscount())
                .build();
    }

    public static RentDetailsDto toRentDetailsDto(RentEntity rentEntity) {
        return RentDetailsDto.builder()
                .id(rentEntity.getId())
                .customerId(rentEntity.getUserEntity().getId())
                .spaceshipId(rentEntity.getSpaceShipEntity().getId())
                .expectedPickupDate(rentEntity.getExpectedPickupDate())
                .expectedReturnDate(rentEntity.getExpectedReturnDate())
                .returnDate(rentEntity.getReturnDate())
                .pickDate(rentEntity.getPickupDate())
                .pricePerDay(rentEntity.getPricePerDay())
                .discount(rentEntity.getDiscount())
                .build();
    }
}
