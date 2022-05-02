package com.mindera.school.spaceshiprent.acceptance;


import com.mindera.school.spaceshiprent.dto.rent.RentDetailsDto;
import com.mindera.school.spaceshiprent.exception.model.SpaceshipRentError;
import com.mindera.school.spaceshiprent.persistence.entity.RentEntity;
import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static com.mindera.school.spaceshiprent.MockedData.getMockedRentEntity;
import static com.mindera.school.spaceshiprent.MockedData.getRentDetailsDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class GetRentById {
        @Test
        public void test_getRentById_shouldReturn200() {
            // GIVEN
            RentEntity entity = getMockedRentEntity();
            when(rentRepository.findById(1L))
                    .thenReturn(Optional.of(entity));
            String path = "/rents/1";

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

            RentDetailsDto expected = getRentDetailsDto(entity);
            assertEquals(expected, response.getBody());

        }

        /*
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
                        List<RentDetailsDto>.class
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
        */
        @Test
        public void test_getRentById_shouldReturn404() {
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
    }

}
