package com.mindera.school.spaceshiprent.unit.service;

import com.mindera.school.spaceshiprent.components.EmailSender;
import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.exceptions.UserNotFoundException;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.service.user.UserServiceImpl;
import com.mindera.school.spaceshiprent.util.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        this.userService = new UserServiceImpl(new UserConverter( new JWTUtils()), userRepository, new EmailSender(new RabbitTemplate()));
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
    public void test_getUserById_shouldReturn_NotFound() {
        // given
        when(userRepository.findById(5L))
                .thenReturn(Optional.empty());

        // when
        Executable action = () -> userService.getUserById(5L);

        // then
        assertThrows(UserNotFoundException.class, action);
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
}
