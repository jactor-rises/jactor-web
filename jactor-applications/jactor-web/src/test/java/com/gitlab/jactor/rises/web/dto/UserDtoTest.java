package com.gitlab.jactor.rises.web.dto;

import com.gitlab.jactor.rises.test.extension.validate.SuppressValidInstanceExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static com.gitlab.jactor.rises.commons.dto.AddressDto.anAddress;
import static com.gitlab.jactor.rises.commons.dto.PersonDto.aPerson;
import static com.gitlab.jactor.rises.commons.dto.UserDto.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SuppressValidInstanceExtension.class)
@DisplayName("A UserDto")
class UserDtoTest {

    @DisplayName("should fetch the address of the user as a list of strings")
    @Test void shouldFetchTheAddress() {
        UserDto testUserDto = new UserDto(
                aUser().with(aPerson().with(anAddress()
                        .withAddressLine1("address line 1")
                        .withAddressLine2("address line 2")
                        .withAddressLine3("address line 3")
                        .withZipCode(1234)
                        .withCity("somewhere")
                )).build()
        );

        List<String> address = testUserDto.fetchAddress();

        assertThat(address).containsExactly(
                "address line 1",
                "address line 2",
                "address line 3",
                "1234",
                "somewhere"
        );
    }

    @DisplayName("should not fetch parts of address being null")
    @Test void shouldGetTheAddress() {
        UserDto testUserDto = new UserDto(
                aUser().with(aPerson().with(anAddress()
                        .withAddressLine1("address line 1")
                        .withZipCode(1234)
                )).build()
        );

        List<String> address = testUserDto.fetchAddress();

        assertThat(address).containsExactly(
                "address line 1",
                "1234"
        );
    }

    @DisplayName("should fetch the username of the user")
    @Test void shouldFetchTheUsername() {
        UserDto testUserDto = new UserDto(aUser().withUserName("user").build());

        assertThat(testUserDto.fetchUsername()).isEqualTo("user");
    }

    @DisplayName("should fetch the person behind the user")
    @Test void shouldFetchThePersonBehindTheUser() {
        UserDto testUserDto = new UserDto(
                aUser().with(aPerson()
                        .withFirstName("John")
                        .withSurname("Smith")
                        .withDescription("description")
                ).build()
        );

        assertAll(
                () -> assertThat(testUserDto.fetchFullName()).isEqualTo("John Smith"),
                () -> assertThat(testUserDto.fetchDescriptionCode()).isEqualTo(Optional.of("description"))
        );
    }
}
