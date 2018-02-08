package com.github.jactor.rises.web.dto;

import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.client.datatype.UserName;
import com.github.jactor.rises.client.domain.Address;
import com.github.jactor.rises.client.domain.Person;
import com.github.jactor.rises.client.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDtoTest {
    @Mock private Address mockedAddress;
    @Mock private User mockedUser;
    @Mock private Person mockedPerson;

    @Test public void willGetTheAddress() {
        mockUserInstance();
        UserDto testUserDto = new UserDto(mockedUser);

        assertThat(testUserDto.getAddressLine1()).isEqualTo("address line 1");
        assertThat(testUserDto.getAddressLine2()).isEqualTo("address line 2");
        assertThat(testUserDto.getCity()).isEqualTo("somewhere");
        assertThat(testUserDto.getZipCode()).isEqualTo(1234);
    }

    @Test public void willGetTheUser() {
        mockUserInstance();
        UserDto testUserDto = new UserDto(mockedUser);

        assertThat(testUserDto.getUserName()).isEqualTo("user");
    }

    @Test public void willGetThePersonForTheUser() {
        mockUserInstance();
        UserDto testUserDto = new UserDto(mockedUser);

        assertThat(testUserDto.getFirstName()).isEqualTo("John");
        assertThat(testUserDto.getSurname()).isEqualTo("Smith");
        assertThat(testUserDto.getDescription()).isEqualTo("description");
    }

    private void mockUserInstance() {
        when(mockedUser.getUserName()).thenReturn(new UserName("user"));
        when(mockedUser.getPerson()).thenReturn(mockedPerson);
        when(mockedPerson.getAddress()).thenReturn(mockedAddress);
        when(mockedPerson.getDescription()).thenReturn("description");
        when(mockedPerson.getFirstName()).thenReturn(new Name("John"));
        when(mockedPerson.getSurname()).thenReturn(new Name("Smith"));
        when(mockedAddress.getAddressLine1()).thenReturn("address line 1");
        when(mockedAddress.getAddressLine2()).thenReturn("address line 2");
        when(mockedAddress.getCity()).thenReturn("somewhere");
        when(mockedAddress.getZipCode()).thenReturn(1234);
    }
}
