package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.rent.RentService;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentControllerTest {


    @MockBean
    private RentRepository rentRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SpaceshipRepository spaceshipRepository;



    @Value("${local.server.port}")
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    void test_getRentById_shouldReturn200() {
        //GIVEN
        final var rentEntity = getMockedRentEntity(1L);
        when(rentRepository.findById(1L)).thenReturn(Optional.of(rentEntity));


        //WHEN
        final var response = given()
                .when()
                .get("/rents/{id}","1")
                .then()
                .extract()
                .response();


        //ASSERT
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final var expected = getRentDetailsDTO(rentEntity);
        final var actual = response.getBody().as(RentDetailsDto.class);
        assertEquals(expected, actual);
    }


  @Test
    public void test_getRentById_shouldReturn404() {

        //GIVEN
        when(rentRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN
        final var response = given()
                .when()
                .get("/rents/{id}","1")
                .then()
                .extract()
                .response();

        //ASSERT
        verify(rentRepository, times(1)).findById(1L);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void test_getAllRents_shouldReturn200() {

        // GIVEN
        RentEntity entity1 = getMockedRentEntity(1L);
        RentEntity entity2 = getMockedRentEntity(2L);

        when(rentRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));
        String path = "/rents";

        //WHEN
        final var response = given()
                .when()
                .get(path)
                .then()
                .extract()
                .response();

        //ASSERT
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        final var expected = Arrays.asList(getRentDetailsDTO(entity1), getRentDetailsDTO(entity2));
        final var actual = response.jsonPath().getList("", RentDetailsDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void test_getAllRents_shouldReturnEmptyList() {
        // GIVEN

        when(rentRepository.findAll())
                .thenReturn(List.of());

        String path = "/rents";

        //WHEN
        final var response = given()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
        //ASSERT
        final var expected = 0;
        final var actual = response.jsonPath().getList("", RentDetailsDto.class).size();
        assertEquals(expected, actual);

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

    private SpaceshipEntity getMockedSpaceshipEntity() {
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
                .expectedPickupDate(LocalDate.of(2022, 11, 12))
                .expectedReturnDate(LocalDate.of(2022, 12, 13))
                .pricePerDay(150)
                .discount(0)
                .build();
    }

    private CreateOrUpdateRentDto getMockedCreateDto() {
        CreateOrUpdateRentDto dto = new CreateOrUpdateRentDto();
        dto.setCustomerId(1L);
        dto.setSpaceshipId(1L);
        dto.setExpectedPickupDate(LocalDate.of(2022, 12, 12));
        dto.setExpectedReturnDate(LocalDate.of(2022, 12, 13));
        dto.setDiscount(0);
        return dto;
    }

    private RentDetailsDto getRentDetailsDTO(RentEntity entity) {
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
