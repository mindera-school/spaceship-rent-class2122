package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SpaceshipRepository spaceshipRepository;

    @MockBean
    private RentRepository rentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_getRentById_shouldReturn200(){
        // GIVEN
        RentEntity entity = getMockedRentEntity(2L);
        when(rentRepository.findById(2L))
                .thenReturn(Optional.of(entity));
        String path = "/rents/2";

        // WHEN
        ResponseEntity<RentDetailsDto> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                RentDetailsDto.class
        );

        // THEN
        verify(rentRepository, times(1))
                .findById(anyLong());

        RentDetailsDto expected = getRentDetailsDTO(entity);
        assertEquals(expected, response.getBody());

    }

    @Test
    public void test_getRentById_shouldReturn404 () {
        // GIVEN
        when(rentRepository.findById(1L))
                .thenReturn(Optional.empty());
        String path = "/rents/1";

        // WHEN
        ResponseEntity<SpaceshipRentError> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                SpaceshipRentError.class
        );

        // THEN
        verify(rentRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND,
                response.getStatusCode(),
                "status code");
        assertEquals("RentNotFoundException",
                Objects.requireNonNull(response.getBody()).getException(),
                "exception name");
    }

    @Test
    public void test_getAllRents_shouldReturn200(){

        // GIVEN
        RentEntity entity1 = getMockedRentEntity(1L);
        RentEntity entity2 = getMockedRentEntity(2L);

        when(rentRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));
        String path = "/rents";

        // WHEN
        ResponseEntity<List<RentDetailsDto>> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<RentDetailsDto>>() {
                }

        );

        // THEN
        verify(rentRepository, times(1))
                .findAll();

        List<RentDetailsDto> expected = Arrays.asList(getRentDetailsDTO(entity1), getRentDetailsDTO(entity2));
        assertEquals(expected, response.getBody());
    }


        @Test
        public void test_createRent_shouldReturn200(){
            // GIVEN
            RentEntity entity = getMockedRentEntity(1L);
            when(rentRepository.save(entity))
                    .thenReturn(entity);
            String path = "/rents";

            // WHEN
            ResponseEntity<RentDetailsDto> response = restTemplate.exchange(
                    path,
                    HttpMethod.POST,
                    HttpEntity.EMPTY,
                    RentDetailsDto.class
            );

            // THEN
            verify(rentRepository, times(1))
                    .save(entity);

            RentDetailsDto expected = getRentDetailsDTO(entity);
            assertEquals(expected, response.getBody());

        }



    private UserEntity getMockedUserEntity() {
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

    private SpaceshipEntity getMockedSpaceshipEntity () {
        return SpaceshipEntity.builder()
                .id(1L)
                .name("Corsie")
                .brand("Axeus Mk5")
                .model("Corsair")
                .registerNumber(123234345)
                .priceDay(150)
                .build();
    }

    private RentEntity getMockedRentEntity(Long id) {
        return RentEntity.builder()
                .id(id)
                .userEntity(getMockedUserEntity())
                .spaceShipEntity(getMockedSpaceshipEntity())
                .expectedPickupDate(LocalDate.of(2022,11,12))
                .expectedReturnDate(LocalDate.of(2022,12,13))
                .pricePerDay(150)
                .discount(0)
                .build();
    }

    private CreateOrUpdateRentDto getMockedCreateDto () {
        CreateOrUpdateRentDto dto = new CreateOrUpdateRentDto();
        dto.setCustomerId(1L);
        dto.setSpaceshipId(1L);
        dto.setExpectedPickupDate(LocalDate.of(2022,12,12));
        dto.setExpectedReturnDate(LocalDate.of(2022,12,13));
        dto.setDiscount(0);
        return dto;
    }

    private RentDetailsDto getRentDetailsDTO (RentEntity entity) {
        return RentDetailsDto.builder()
                .id(entity.getId())
                .customerId(entity.getUserEntity().getId())
                .spaceshipId(entity.getSpaceShipEntity().getId())
                .expectedPickupDate(entity.getExpectedPickupDate())
                .expectedReturnDate(entity.getExpectedReturnDate())
                .pickDate(entity.getPickupDate())
                .returnDate(entity.getReturnDate())
                .pricePerDay(entity.getPricePerDay())
                .build();
    }
}
