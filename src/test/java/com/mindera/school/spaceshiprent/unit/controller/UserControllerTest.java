package com.mindera.school.spaceshiprent.unit.controller;

import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.exception.errorModel.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Colocamos esta anotação porque queremos que app levante
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

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

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_getUserById_shouldReturn200() {
        //GIVEN
        UserEntity user = getMockedEntity();
        when(userRepository.findById(5L))
                .thenReturn(Optional.of(user));
        String path = "/users/5";
        //WHEN
        ResponseEntity<UserDetailsDto> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                UserDetailsDto.class); // o que é que o endpoint devolve
        // e como é que vai ser processado. aqui dizemos q é user details dto, para assim o json
        // conseguir converter para um objeto de java

        //THEN
        verify(userRepository, times(1))
                .findById(anyLong());
        UserDetailsDto expected = getUserDetailsDto(user);

        assertEquals(expected, response.getBody());
    }

    @Test
    public void test_getUserById_shouldReturn404() {
        //GIVEN
        when(userRepository.findById(5L))
                .thenReturn(Optional.empty());
        String path = "/users/5";
        //WHEN
        ResponseEntity<SpaceshipRentError> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                SpaceshipRentError.class); // o que é que o endpoint devolve
        // e como é que vai ser processado. aqui dizemos q é user details dto, para assim o json
        // conseguir converter para um objeto de java


        //THEN
        verify(userRepository, times(1))
                .findById(anyLong());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(),
                "status code");
        assertEquals("UserNotFoundException",
                response.getBody().getException(),
                "exception name");
    }
}
