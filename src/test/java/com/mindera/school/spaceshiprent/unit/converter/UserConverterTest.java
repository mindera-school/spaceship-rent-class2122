package com.mindera.school.spaceshiprent.unit.converter;

import com.mindera.school.spaceshiprent.converter.UserConverter;
import com.mindera.school.spaceshiprent.dto.user.CreateOrUpdateUserDto;
import com.mindera.school.spaceshiprent.dto.user.UserDetailsDto;
import com.mindera.school.spaceshiprent.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.mindera.school.spaceshiprent.MockedData.getCreateOrUpdateUserDto;
import static com.mindera.school.spaceshiprent.MockedData.getMockedUserEntity;
import static com.mindera.school.spaceshiprent.MockedData.getUserDetailsDto;
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

    @Nested
    class ConvertToUserDetailsDto {
        @Test
        void test_convertEntityToUserDetailsDto_shouldReturnSuccess() {
            // GIVEN
            UserEntity entity = getMockedUserEntity();

            // WHEN
            UserDetailsDto details = userConverter.convertToUserDetailsDto(entity);

            // THEN
            assertEquals(getUserDetailsDto(entity), details);
        }
    }

    @Nested
    class ConvertToUserEntity {
        @Test
        void test_convertDtoToEntity_shouldHaveTheSameValues() {
            // GIVEN
            CreateOrUpdateUserDto userDto = getCreateOrUpdateUserDto();
            UserEntity mockedEntity = getMockedUserEntity();
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
            assertEquals(mockedEntity.getPassword(), userEntity.getPassword(), "password");
            assertEquals(mockedEntity.getRentEntity(), userEntity.getRentEntity(), "rent");
            assertEquals(mockedEntity.getId(), userEntity.getId(), "id");
        }

        @Test
        void convertToEntity_shouldNotHaveId() {
            // GIVEN
            CreateOrUpdateUserDto userDto = getCreateOrUpdateUserDto();

            // WHEN
            UserEntity userEntity = userConverter.convertToEntity(userDto);

            // THEN
            assertNull(userEntity.getId());
        }
    }

}
