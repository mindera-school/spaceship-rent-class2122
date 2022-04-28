package com.mindera.school.spaceshiprent.unit.converter;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.enumerator.UserType;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import com.mindera.school.spaceshiprent.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class UserConverterTest {

    private UserConverter userConverter;

    @BeforeEach
    public void setup() {
        this.userConverter = new UserConverter();
    }

    @Test
    public void test_convertEntityToUserDetailsDto_shouldReturnSuccess() {
        // GIVEN
        UserEntity entity = getMockedEntity();

        // WHEN
        UserDetailsDto details = userConverter.convertToUserDetailsDto(entity);

        // THEN
        assertEquals(getUserDetailsDto(entity), details);
    }

    @Test
    public void test_convertDtoToEntity_shouldHaveTheSameValues() {
        // GIVEN
        CreateOrUpdateUserDto userDto = getCreateOrUpdateUserDto();
        UserEntity mockedEntity = getMockedEntity();
        mockedEntity.setId(null);

        // WHEN
        UserEntity userEntity = userConverter.convertToEntity(userDto);

        // THEN
        assertEquals(mockedEntity.getEmail(), userEntity.getEmail(), "email");
        assertEquals(mockedEntity.getName(), userEntity.getName(), "name");
        assertEquals(mockedEntity.getUserType(), userEntity.getUserType(), "user type");
        assertEquals(mockedEntity.getAge(), userEntity.getAge(), "age");
        assertEquals(mockedEntity.getPlanet(), userEntity.getPlanet(), "planet");
        assertEquals(mockedEntity.getLicenseNumber(), userEntity.getLicenseNumber(), "license number");
        assertEquals(mockedEntity.getSsn(), userEntity.getSsn(), "ssn");
        //assertEquals(mockedEntity.getPassword(), userEntity.getPassword(), "password");
        assertEquals(mockedEntity.getRentEntity(), userEntity.getRentEntity(), "rent");
        assertEquals(mockedEntity.getId(), userEntity.getId(), "id");
    }

    @Test
    public void convertToEntity_shouldNotHaveId() {
        // GIVEN
        CreateOrUpdateUserDto userDto = getCreateOrUpdateUserDto();

        // WHEN
        UserEntity userEntity = userConverter.convertToEntity(userDto);

        // THEN
        assertNull(userEntity.getId());
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

    private CreateOrUpdateUserDto getCreateOrUpdateUserDto() {
        return CreateOrUpdateUserDto.builder()
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
