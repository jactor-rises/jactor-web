package com.github.jactor.web.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.jactor.web.dto.AddressDtoBuilder;
import com.github.jactor.web.dto.PersonDtoBuilder;
import com.github.jactor.web.dto.UserDtoBuilder;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A UserModel")
class UserModelTest {

  @Test
  @DisplayName("should fetch the address of the user as a list of strings")
  void shouldFetchTheAddress() {
    UserModel testUserModel = new UserModel(
        aUser().with(aPerson().with(anAddress()
            .withAddressLine1("address line 1")
            .withAddressLine2("address line 2")
            .withAddressLine3("address line 3")
            .withZipCode("1234")
            .withCity("somewhere")
        )).build()
    );

    List<String> address = testUserModel.fetchAddress();

    assertThat(address).containsExactly(
        "address line 1",
        "address line 2",
        "address line 3",
        "1234",
        "somewhere"
    );
  }

  @Test
  @DisplayName("should not fetch parts of address being null")
  void shouldGetTheAddress() {
    UserModel testUserModel = new UserModel(
        aUser().with(aPerson().with(anAddress()
            .withAddressLine1("address line")
            .withZipCode("4321")
        )).build()
    );

    List<String> address = testUserModel.fetchAddress();

    assertThat(address).containsExactly(
        "address line",
        "4321"
    );
  }

  @Test
  @DisplayName("should fetch the username of the user")
  void shouldFetchTheUsername() {
    UserModel testUserModel = new UserModel(aUser().withUsername("user").build());

    assertThat(testUserModel.fetchUsername()).isEqualTo("user");
  }

  @Test
  @DisplayName("should fetch the person behind the user")
  void shouldFetchThePersonBehindTheUser() {
    UserModel testUserModel = new UserModel(
        aUser().with(aPerson()
            .withFirstName("John")
            .withSurname("Smith")
            .withDescription("description")
        ).build()
    );

    assertAll(
        () -> assertThat(testUserModel.fetchFullName()).isEqualTo("John Smith"),
        () -> assertThat(testUserModel.fetchDescriptionCode()).isEqualTo("description")
    );
  }

  private UserDtoBuilder aUser() {
    return new UserDtoBuilder();
  }

  private PersonDtoBuilder aPerson() {
    return new PersonDtoBuilder();
  }

  private AddressDtoBuilder anAddress() {
    return new AddressDtoBuilder();
  }

}
