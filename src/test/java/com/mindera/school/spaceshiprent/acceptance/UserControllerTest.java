package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.dto.spaceship.SpaceShipDetailsDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.SpaceshipEntity;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_getUserById_shouldReturn200() {
        // GIVEN
        UserEntity entity = getMockedEntity();
        when(userRepository.findById(5L))
                .thenReturn(Optional.of(entity));
        String path = "/users/5";

        // WHEN
        ResponseEntity<UserDetailsDto> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                UserDetailsDto.class);

        // THEN
        verify(userRepository, times(1))
                .findById(anyLong());

        UserDetailsDto expected = getUserDetailsDto(entity);
        assertEquals(expected, response.getBody());
    }

    @Test
    public void test_getUserById_shouldReturn404() {
        // GIVEN
        when(userRepository.findById(5L))
                .thenReturn(Optional.empty());
        String path = "/users/5";

        // WHEN
        ResponseEntity<SpaceshipRentError> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                SpaceshipRentError.class);

        // THEN
        verify(userRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND,
                response.getStatusCode(),
                "status code");
        assertEquals("UserNotFoundException",
                Objects.requireNonNull(response.getBody()).getException(),
                "exception name");
    }

    @Test
    public void test_getAllUsers_shouldReturn200() {
        // GIVEN
        UserEntity entity1 = getMockedEntity();
        UserEntity entity2 = getMockedEntity();

        when(userRepository.findAll())
                .thenReturn(Arrays.asList(entity1, entity2));
        String path = "/users";

        // WHEN
        ResponseEntity<List<UserDetailsDto>> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<UserDetailsDto>>() {
                });

        // THEN
        verify(userRepository, times(1))
                .findAll();

        List<UserDetailsDto> expected = Arrays.asList(getUserDetailsDto(entity1), getUserDetailsDto(entity2));
        assertEquals(expected, response.getBody());
    }

    @Test
    public void test_getAllUsers_shouldReturn404() {
        // GIVEN
        when(userRepository.findAll())
                .thenReturn(List.of());
        String path = "/users";

        // WHEN
        ResponseEntity<List<UserDetailsDto>> response = restTemplate.exchange(
                    path,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<List<UserDetailsDto>>() {
                    });

            // THEN
            verify(userRepository, times(1))
                    .findAll();

            assertEquals(0, Objects.requireNonNull(response.getBody()).size(), "number of space ships");
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
