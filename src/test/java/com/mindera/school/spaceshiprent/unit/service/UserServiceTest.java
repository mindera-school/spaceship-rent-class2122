package com.mindera.school.spaceshiprent.unit.service;

import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.NotFoundExceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.userService.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl(userRepository);
    }

    private UserEntity getMockedEntity() {
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

    private UserEntity getMockedUpdatedEntity(Long id) {
        return UserEntity.builder()
                .id(id)
                .age(29)
                .email("email@email.com")
                .licenseNumber("kjdbhf3")
                .userType(UserType.EMPLOYEE)
                .name("Nome")
                .ssn(1245546L)
                .password("password")
                .planet("terra")
                .build();
    }

    // não se usa o converter para não criar dependência com o código que vai ser testado
    private UserDetailsDto getUserDetailsDto(UserEntity entity) {
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

    private CreateOrUpdateUserDto getMockedCreateDto() {
        return CreateOrUpdateUserDto.builder()
                .age(29)
                .email("email@email.com")
                .licenseNumber("kjdbhf3")
                .userType(UserType.EMPLOYEE)
                .name("Nome")
                .ssn(1245546L)
                .password("password")
                .planet("terra")
                .build();
    }

    @Test
    public void test_getUserById_shouldReturnSuccess() {
        // GIVEN
        Long userId = 5L;
        UserEntity entity = getMockedEntity();
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(entity));
        // WHEN
        UserDetailsDto response = userService.getUserById(userId);
        // THEN
        assertEquals(getUserDetailsDto(entity), response);
    }

    @Test
    public void test_getUserByInvalidId_shouldThrowException() {
        // GIVEN
        Long userId = 9L;
        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());
        // WHEN
        Executable response = () -> userService.getUserById(userId);
        // THEN
        assertThrows(UserNotFoundException.class, response);
    }
    /*
    @Test
    public void test_updateUser_shouldReturnSuccess() {
        // GIVEN
        Long userId = 5L;
        UserEntity previousEntity = getMockedEntity();
        UserEntity updatedEntity = getMockedUpdatedEntity(userId);

        // WHEN
        doReturn(Optional.of(previousEntity))
                .when(userRepository.findById(userId));
        when(userRepository.save(Mockito.any(UserEntity.class)))
                .thenReturn(updatedEntity);
        verify(userRepository, atLeast(1)).save(Mockito.any(UserEntity.class));

        // THEN
        assertNotEquals(getUserDetailsDto(previousEntity).getAge(), userService.updateUserById(userId, getMockedCreateDto()).getAge());

}
     */
}
