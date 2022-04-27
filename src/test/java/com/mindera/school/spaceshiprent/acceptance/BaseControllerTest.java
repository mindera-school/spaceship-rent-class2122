package com.mindera.school.spaceshiprent.acceptance;

import com.mindera.school.spaceshiprent.persistence.repository.RentRepository;
import com.mindera.school.spaceshiprent.persistence.repository.SpaceshipRepository;
import com.mindera.school.spaceshiprent.persistence.repository.UserRepository;
import com.mindera.school.spaceshiprent.security.filter.ApiKeyAuthFilter;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest {

    protected static final String API_KEY = "test_api_key";

    @Autowired
    protected TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected SpaceshipRepository spaceshipRepository;

    @MockBean
    protected RentRepository rentRepository;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
    }

    protected Map<String, String> getApiAuthenticatedHeader() {
        return Map.of(ApiKeyAuthFilter.HEADER_API_KEY, API_KEY);
    }
}
