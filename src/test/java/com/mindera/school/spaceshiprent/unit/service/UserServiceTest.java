package com.mindera.school.spaceshiprent.unit.service;

import com.mindera.school.spaceshiprent.MockedData;
import com.mindera.school.spaceshiprent.components.EmailSender;
import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.exception.exceptions.UserAlreadyExists;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateUserDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedData.getUserDetailsDto;
import static com.mindera.school.spaceshiprent.MockedData.getUserList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailSender sender;

    @BeforeEach
    public void setup() {
        this.userService = new UserServiceImpl(new UserConverter(), userRepository, sender);
    }

    @Nested
    class GetUserById {

        @Test
        void test_getUserById_shouldReturnSuccess() {
            // GIVEN
            Long userId = 5L;

            UserEntity entity = getMockedUserEntity();
            when(userRepository.findById(userId))
                    .thenReturn(Optional.of(entity));

            // WHEN
            UserDetailsDto response = userService.getUserById(userId);

            // THEN
            assertEquals(getUserDetailsDto(entity), response);
        }

        @Test
        void test_getUserById_shouldReturnNotFound() {
            // given
            when(userRepository.findById(5L))
                    .thenReturn(Optional.empty());

            // when
            Executable action = () -> userService.getUserById(5L);

            // then
            assertThrows(UserNotFoundException.class, action);
        }
    }

    @Nested
    class CreateUser {
        @Test
        void test_createUser_shouldReturn_success() {
            // GIVEN
            CreateOrUpdateUserDto dto = getCreateOrUpdateUserDto();
            UserEntity entity = getMockedUserEntity();

            // WHEN
            when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

            // shouldn't do anything when email service is called
            doNothing().when(sender).send(isA(String.class), isA(String.class));

            when(userRepository.save(Mockito.any(UserEntity.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

            UserDetailsDto response = userService.createUser(dto);
            response.setId(getMockedUserEntity().getId());

            // THEN
            assertEquals(getUserDetailsDto(entity), response);
        }

        @Test
        void test_createUser_shouldReturn_userAlreadyExists() {
            // GIVEN
            CreateOrUpdateUserDto dto = getCreateOrUpdateUserDto();
            UserEntity entity = getMockedUserEntity();

            // WHEN
            when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.ofNullable(entity));

            Executable action = () -> userService.createUser(dto);

            // THEN
            assertThrows(UserAlreadyExists.class, action);
        }
    }

    @Nested
    class GetAllUsers {
        @Test
        void test_getAllUsers_shouldReturn_success() {
            // given
            List<UserDetailsDto> userDetails = getUserList().stream()
                    .map(MockedData::getUserDetailsDto)
                    .collect(Collectors.toList());

            // when
            when(userRepository.findAll()).thenReturn(getUserList());

            List<UserDetailsDto> response = userService.getAllUsers();

            // then
            assertEquals(userDetails, response);
        }
    }

    @Nested
    class UpdateUserById {
        @Test
        void test_updateUserId_shouldReturn_success() {
            // given
            CreateOrUpdateUserDto dto = getCreateOrUpdateUserDto();
            UserEntity entity = getMockedUserEntity();
            Long id = entity.getId();

            // when
            when(userRepository.findById(id)).thenReturn(Optional.of(entity));
            when(userRepository.save(Mockito.any(UserEntity.class)))
                    .thenAnswer(invocation -> invocation.getArguments()[0]);
            UserDetailsDto response = userService.updateUserById(id, dto);

            // then
            assertEquals(getUserDetailsDto(entity), response);
        }

        @Test
        void test_updateUserId_shouldReturn_notFound() {
            // given
            CreateOrUpdateUserDto dto = getCreateOrUpdateUserDto();
            Long id = getMockedUserEntity().getId();

            // when
            when(userRepository.findById(id)).thenReturn(Optional.empty());
            Executable action = () -> userService.updateUserById(id, dto);

            // then
            assertThrows(UserNotFoundException.class, action);
        }
    }

}
