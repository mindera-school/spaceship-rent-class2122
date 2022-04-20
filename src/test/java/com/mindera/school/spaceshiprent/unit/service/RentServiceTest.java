package com.mindera.school.spaceshiprent.unit.service;

import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.rent.RentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentServiceTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpaceshipRepository spaceshipRepository;

    @InjectMocks
    private RentServiceImpl rentService;

    @BeforeEach
    public void setup() {
        this.rentService = new RentServiceImpl(new RentConverter(), rentRepository, userRepository, spaceshipRepository);
    }

    @Test
    public void rentCreation_userSet_spaceShipSet_priceSet() {

        RentEntity entity = getMockedEntityRent();
        UserEntity userEntity = getMockedEntityUser();
        SpaceshipEntity spaceshipEntity = getMockedEntitySpaceShip();

        when(userRepository.findById(getMockedEntityUser().getId())).thenReturn(Optional.of(userEntity));
        when(spaceshipRepository.findById(getMockedEntitySpaceShip().getId())).thenReturn(Optional.of(spaceshipEntity));
        when(rentRepository.save(Mockito.any(RentEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        RentDetailsDto response = rentService.createRent(getMockedCreateOrUpdateRent());
        response.setId(getRentDetailsDto(entity).getId());
        assertEquals(getRentDetailsDto(entity), response);
    }

    @Test
    public void rentCreation_UserNotFound() {

        when(userRepository.findById(getMockedEntityUser().getId())).thenReturn(Optional.empty());

        Executable action = () -> rentService.createRent(getMockedCreateOrUpdateRent());
        assertThrows(UserNotFoundException.class, action);
    }

    @Test
    public void rentCreation_spaceShipNotFound() {

        UserEntity userEntity = getMockedEntityUser();

        when(userRepository.findById(getMockedEntityUser().getId())).thenReturn(Optional.of(userEntity));
        when(spaceshipRepository.findById(getMockedEntitySpaceShip().getId())).thenReturn(Optional.empty());

        Executable action = () -> rentService.createRent(getMockedCreateOrUpdateRent());

        assertThrows(SpaceshipNotFoundException.class, action);
    }

    @Test
    public void getAllRents_shouldReturnSuccess() {

        when(rentRepository.findAll()).thenReturn(getRentEntityList());

        List<RentDetailsDto> rentDetails = getRentEntityList().stream().map(this::getRentDetailsDto).collect(Collectors.toList());

        List<RentDetailsDto> response = rentService.getAllRents();

        assertEquals(rentDetails, response);
    }

    @Test
    public void getRentById_shouldReturnSuccess() {
        RentEntity entity = getMockedEntityRent();

        when(rentRepository.findById(1L)).thenReturn(Optional.of(entity));

        RentDetailsDto response = rentService.getRentById(1L);

        assertEquals(getRentDetailsDto(entity), response);
    }

    @Test
    public void test_getRentById_shouldReturn_notFound() {
        when(rentRepository.findById(5L))
                .thenReturn(Optional.empty());

        Executable action = () -> rentService.getRentById(5L);

        assertThrows(RentNotFoundException.class, action);
    }

    @Test
    public void test_updateRent_shouldReturnSuccess() {
        RentEntity entity = getMockedEntityRent();

        when(rentRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(rentRepository.save(Mockito.any(RentEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        RentDetailsDto response = rentService.updateRent(1L, getMockedCreateOrUpdateRent());

        assertEquals(getRentDetailsDto(entity), response);
    }

    @Test
    public void test_updateRentById_shouldReturn_NotFound() {
        when(rentRepository.findById(1L))
                .thenReturn(Optional.empty());

        Executable action = () -> rentService.updateRent(1L, getMockedCreateOrUpdateRent());

        assertThrows(RentNotFoundException.class, action);
    }

    @Test
    public void test_getRentByCostumerId_shouldReturn_Rent() {
        UserEntity userEntity = getMockedEntityUser();
        userEntity.setRentEntity(getRentEntityList());

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));

        List<RentDetailsDto> response = rentService.getRentByCustomerId(5L);
        List<RentDetailsDto> rentDetails = getRentEntityList().stream().map(this::getRentDetailsDto).collect(Collectors.toList());

        assertEquals(rentDetails, response);
    }

    @Test
    public void test_getRentByCustomerId_shouldReturn_Error(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RentNotFoundException.class,() -> rentService.getRentByCustomerId(anyLong()));
    }

    @Test
    public void test_getRentBySpaceShipId_shouldReturn_Success(){
        SpaceshipEntity spaceshipEntity = getMockedEntitySpaceShip();
        spaceshipEntity.setRentEntity(getRentEntityList());

        when(spaceshipRepository.findById(spaceshipEntity.getId())).thenReturn(Optional.of(spaceshipEntity));

        List<RentDetailsDto> response = rentService.getRentBySpaceShipId(1L);
        List<RentDetailsDto> rentDetails = getRentEntityList().stream().map(this::getRentDetailsDto).collect(Collectors.toList());

        assertEquals(rentDetails, response);
    }

    @Test
    public void test_getRentBySpaceShipId_ShouldReturn_Error(){
        when(spaceshipRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RentNotFoundException.class,() -> rentService.getRentBySpaceShipId(anyLong()));
    }

    private RentEntity getMockedEntityRent() {
        return RentEntity.builder()
                .id(1L)
                .discount(20)
                .expectedReturnDate(LocalDate.of(2022, 12, 20))
                .expectedPickupDate(LocalDate.of(2022, 10, 20))
                .userEntity(getMockedEntityUser())
                .pricePerDay(12)
                .spaceShipEntity(getMockedEntitySpaceShip())
                .build();
    }

    private UserEntity getMockedEntityUser() {
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

    private SpaceshipEntity getMockedEntitySpaceShip() {
        return SpaceshipEntity.builder()
                .id(1L)
                .name("nave")
                .brand("mercedes")
                .model("x5")
                .registerNumber(10)
                .priceDay(12)
                .build();
    }

    private RentDetailsDto getRentDetailsDto(RentEntity rentEntity) {
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

    private CreateOrUpdateRentDto getMockedCreateOrUpdateRent() {
        CreateOrUpdateRentDto createOrUpdateRentDto = new CreateOrUpdateRentDto();
        createOrUpdateRentDto.setCustomerId(getMockedEntityRent().getUserEntity().getId());
        createOrUpdateRentDto.setSpaceshipId(getMockedEntityRent().getSpaceShipEntity().getId());
        createOrUpdateRentDto.setDiscount(20);
        createOrUpdateRentDto.setExpectedReturnDate(LocalDate.of(2022, 12, 20));
        createOrUpdateRentDto.setExpectedPickupDate(LocalDate.of(2022, 10, 20));
        return createOrUpdateRentDto;

    }

    private List<RentEntity> getRentEntityList() {
        return Collections.singletonList(getMockedEntityRent());
    }

}

