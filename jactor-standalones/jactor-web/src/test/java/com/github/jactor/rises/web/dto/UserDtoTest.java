package com.github.jactor.rises.web.dto;

import com.github.jactor.rises.test.extension.validate.SuppressValidInstanceExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.jactor.rises.model.domain.address.AddressDomain.anAddress;
import static com.github.jactor.rises.model.domain.person.PersonDomain.aPerson;
import static com.github.jactor.rises.model.domain.user.UserDomain.aUser;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SuppressValidInstanceExtension.class)
@DisplayName("A UserDto")
class UserDtoTest {

    @DisplayName("should get the address of the user")
    @Test void shouldGetTheAddress() {
        UserDto testUserDto = new UserDto(
                aUser().with(aPerson().with(anAddress()
                        .withAddressLine1("address line 1")
                        .withAddressLine2("address line 2")
                        .withZipCode(1234)
                        .withCity("somewhere")
                )).build()
        );

        assertThat(testUserDto.getAddressLine1()).isEqualTo("address line 1");
        assertThat(testUserDto.getAddressLine2()).isEqualTo("address line 2");
        assertThat(testUserDto.getCity()).isEqualTo("somewhere");
        assertThat(testUserDto.getZipCode()).isEqualTo(1234);
    }

    @DisplayName("should get the user name of the user")
    @Test void shouldGetTheUser() {
        UserDto testUserDto = new UserDto(aUser().withUsername("user").build());

        assertThat(testUserDto.getUserName()).isEqualTo("user");
    }

    @DisplayName("should get the person for this user")
    @Test void shouldGetThePersonForTheUser() {
        UserDto testUserDto = new UserDto(
                aUser().with(aPerson()
                        .withFirstName("John")
                        .withSurname("Smith")
                        .withDescription("description")
                ).build()
        );

        assertThat(testUserDto.getFirstName()).isEqualTo("John");
        assertThat(testUserDto.getSurname()).isEqualTo("Smith");
        assertThat(testUserDto.getDescription()).isEqualTo("description");
    }
}
