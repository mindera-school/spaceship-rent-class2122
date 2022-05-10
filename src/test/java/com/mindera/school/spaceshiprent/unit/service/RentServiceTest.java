package com.mindera.school.spaceshiprent.unit.service;

import com.mindera.school.spaceshiprent.MockedData;
import com.mindera.school.spaceshiprent.converter.RentConverter;
import com.mindera.school.spaceshiprent.dto.rent.CreateOrUpdateRentDto;
import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.exceptions.RentNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.SpaceshipNotFoundException;
import com.mindera.school.spaceshiprent.exception.exceptions.UnavailableRentDatesException;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.rent.RentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindera.school.spaceshiprent.MockedData.getMockedCreateOrUpdateRent;
import static com.mindera.school.spaceshiprent.MockedData.getMockedRentEntity;
import static com.mindera.school.spaceshiprent.MockedData.getMockedSpaceshipEntity;
import static com.mindera.school.spaceshiprent.MockedData.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedData.getRentDetailsDto;
import static com.mindera.school.spaceshiprent.MockedData.getRentEntityList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Nested
    class CreateRent {

        @Test
        void rentCreation_userSet_spaceshipSet_priceSet() {

            RentEntity entity = getMockedRentEntity();
            UserEntity userEntity = getMockedUserEntity();
            SpaceshipEntity spaceshipEntity = getMockedSpaceshipEntity();

            when(userRepository.findById(getMockedUserEntity().getId())).thenReturn(Optional.of(userEntity));
            when(spaceshipRepository.findById(getMockedSpaceshipEntity().getId())).thenReturn(Optional.of(spaceshipEntity));
            when(rentRepository.save(Mockito.any(RentEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
            when(rentRepository.checkRentAvailability(entity.getExpectedPickupDate(),
                    entity.getExpectedReturnDate(),
                    spaceshipEntity.getId()))
                    .thenReturn(true);

            RentDetailsDto response = rentService.createRent(getMockedCreateOrUpdateRent());
            response.setId(getRentDetailsDto(entity).getId());
            assertEquals(getRentDetailsDto(entity), response);
        }

        @Test
        void rentCreation_UserNotFound() {

            when(userRepository.findById(getMockedUserEntity().getId())).thenReturn(Optional.empty());

            Executable action = () -> rentService.createRent(getMockedCreateOrUpdateRent());
            assertThrows(UserNotFoundException.class, action);
        }

        @Test
        void rentCreation_spaceShipNotFound() {

            UserEntity userEntity = getMockedUserEntity();

            when(userRepository.findById(getMockedUserEntity().getId())).thenReturn(Optional.of(userEntity));
            when(spaceshipRepository.findById(getMockedSpaceshipEntity().getId())).thenReturn(Optional.empty());

            Executable action = () -> rentService.createRent(getMockedCreateOrUpdateRent());

            assertThrows(SpaceshipNotFoundException.class, action);
        }

        @Test
        void rentCreation_spaceShipNotAvailable() {
            CreateOrUpdateRentDto dto = getMockedCreateOrUpdateRent();
            UserEntity userEntity = getMockedUserEntity();
            SpaceshipEntity spaceshipEntity = getMockedSpaceshipEntity();

            when(userRepository.findById(getMockedUserEntity().getId()))
                    .thenReturn(Optional.of(userEntity));

            when(spaceshipRepository.findById(getMockedSpaceshipEntity().getId()))
                    .thenReturn(Optional.of(spaceshipEntity));

            when(rentRepository.checkRentAvailability(dto.getExpectedPickupDate(),
                    dto.getExpectedReturnDate(),
                    spaceshipEntity.getId()))
                    .thenReturn(false);

            Executable action = () -> rentService.createRent(dto);

            assertThrows(UnavailableRentDatesException.class, action);
        }
    }

    @Nested
    class GetRentById {
        @Test
        void getRentById_shouldReturnSuccess() {
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(1L)).thenReturn(Optional.of(entity));

            RentDetailsDto response = rentService.getRentById(1L);

            assertEquals(getRentDetailsDto(entity), response);
        }

        @Test
        void test_getRentById_shouldReturn_notFound() {
            when(rentRepository.findById(5L))
                    .thenReturn(Optional.empty());

            Executable action = () -> rentService.getRentById(5L);

            assertThrows(RentNotFoundException.class, action);
        }
    }

    @Nested
    class UpdateRent {
        @Test
        void test_updateRent_shouldReturnSuccess() {
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(1L)).thenReturn(Optional.of(entity));
            when(rentRepository.save(Mockito.any(RentEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
            when(rentRepository.checkRentAvailability(any(), any(), anyLong()))
                    .thenReturn(true);

            RentDetailsDto response = rentService.updateRent(1L, getMockedCreateOrUpdateRent());

            verify(rentRepository, times(1))
                    .findById(anyLong());
            verify(rentRepository, times(1))
                    .save(Mockito.any(RentEntity.class));
            verify(rentRepository, times(1))
                    .checkRentAvailability(any(), any(), anyLong());

            assertEquals(getRentDetailsDto(entity), response);
        }

        @Test
        void test_updateRentById_shouldReturn_NotFound() {
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.empty());

            Executable action = () -> rentService.updateRent(1L, getMockedCreateOrUpdateRent());

            assertThrows(RentNotFoundException.class, action);
        }

        @Test
        void test_updateRentById_shouldReturn_spaceshipUnavailable() {
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(anyLong()))
                    .thenReturn(Optional.of(entity));

            when(rentRepository.checkRentAvailability(any(), any(), anyLong()))
                    .thenReturn(false);

            Executable action = () -> rentService.updateRent(1L, getMockedCreateOrUpdateRent());

            assertThrows(UnavailableRentDatesException.class, action);

            verify(rentRepository, times(1))
                    .findById(anyLong());
            verify(rentRepository, times(1))
                    .checkRentAvailability(any(), any(), anyLong());
        }
    }

    @Nested
    class GetRentByCostumerId {
        @Test
        void test_getRentByCostumerId_shouldReturn_Rent() {
            UserEntity userEntity = getMockedUserEntity();
            userEntity.setRentEntity(getRentEntityList());

            when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));

            List<RentDetailsDto> response = rentService.getRentByCustomerId(5L);
            List<RentDetailsDto> rentDetails = getRentEntityList().stream().map(MockedData::getRentDetailsDto).collect(Collectors.toList());

            assertEquals(rentDetails, response);
        }

        @Test
        void test_getRentByCustomerId_shouldReturn_Error() {
            when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> rentService.getRentByCustomerId(anyLong()));
        }
    }

    @Nested
    class GetRentBySpaceshipId {
        @Test
        void test_getRentBySpaceShipId_shouldReturn_Success() {
            SpaceshipEntity spaceshipEntity = getMockedSpaceshipEntity();
            spaceshipEntity.setRentEntity(getRentEntityList());

            when(spaceshipRepository.findById(spaceshipEntity.getId())).thenReturn(Optional.of(spaceshipEntity));

            List<RentDetailsDto> response = rentService.getRentBySpaceShipId(1L);
            List<RentDetailsDto> rentDetails = getRentEntityList().stream().map(MockedData::getRentDetailsDto).collect(Collectors.toList());

            assertEquals(rentDetails, response);
        }

        @Test
        void test_getRentBySpaceShipId_ShouldReturn_Error() {
            when(spaceshipRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(SpaceshipNotFoundException.class, () -> rentService.getRentBySpaceShipId(anyLong()));
        }
    }

    @Nested
    class UpdatePickUpDate {
        @Test
        void test_updatePickup_shouldReturn_Success() {
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(1L)).thenReturn(Optional.of(entity));
            RentDetailsDto response = rentService.updatePickUpDate(1L);

            assertEquals(getRentDetailsDto(entity), response);
        }

        @Test
        void test_updatePickup_shouldReturn_Error() {
            when(rentRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RentNotFoundException.class, () -> rentService.updatePickUpDate(1L));
        }
    }

    @Nested
    class UpdateReturnDate {

        @Test
        void test_updateReturn_shouldReturn_Success() {
            RentEntity entity = getMockedRentEntity();

            when(rentRepository.findById(1L)).thenReturn(Optional.of(entity));
            RentDetailsDto response = rentService.updateReturnDate(1L);

            assertEquals(getRentDetailsDto(entity), response);
        }

        @Test
        void test_updateReturn_shouldReturn_Error() {
            when(rentRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(RentNotFoundException.class, () -> rentService.updateReturnDate(1L));
        }
    }

    @Nested
    class GetAllRents {
        @Test
        void getAllRents_shouldReturnSuccess() {

            when(rentRepository.findAll()).thenReturn(getRentEntityList());

            List<RentDetailsDto> rentDetails = getRentEntityList().stream().map(MockedData::getRentDetailsDto).collect(Collectors.toList());

            List<RentDetailsDto> response = rentService.getAllRents();

            assertEquals(rentDetails, response);
        }
    }
}

