package com.mindera.school.spaceshiprent.converter;

import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import org.springframework.stereotype.Component;

@Component
public final class RentConverter {

    public RentEntity convertToEntity(CreateOrUpdateRentDto dto) {
        return RentEntity.builder()
                .expectedPickupDate(dto.getExpectedPickupDate())
                .expectedReturnDate(dto.getExpectedReturnDate())
                .discount(dto.getDiscount())
                .build();
    }

    public RentDetailsDto convertToRentDetailsDto(RentEntity entity) {
        return RentDetailsDto.builder()
                .id(entity.getId())
                .customerId(entity.getUserEntity().getId())
                .spaceshipId(entity.getSpaceshipEntity().getId())
                .expectedPickupDate(entity.getExpectedPickupDate())
                .expectedReturnDate(entity.getExpectedReturnDate())
                .returnDate(entity.getReturnDate())
                .pickDate(entity.getPickupDate())
                .pricePerDay(entity.getPricePerDay())
                .discount(entity.getDiscount())
                .build();
    }
}
