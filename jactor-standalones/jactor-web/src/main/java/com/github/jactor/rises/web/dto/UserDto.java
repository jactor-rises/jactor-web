package com.github.jactor.rises.web.dto;


import com.github.jactor.rises.client.domain.Address;
import com.github.jactor.rises.client.domain.Person;
import com.github.jactor.rises.client.domain.User;

/** A class for displaying a user on this web application */
public class UserDto {
    private final Address address;
    private final Person person;
    private final User user;

    public UserDto(User user) {
        this.user = user;
        person = user.getPerson();
        address = person != null ? person.getAddress() : null;
    }

    String getAddressLine1() {
        return address != null ? address.getAddressLine1() : null;
    }

    String getAddressLine2() {
        return address != null ? address.getAddressLine2() : null;
    }

    String getCity() {
        return address != null ? address.getCity() : null;
    }

    Integer getZipCode() {
        return address != null ? address.getZipCode() : null;
    }

    String getFirstName() {
        return person != null && person.getFirstName() != null ? person.getFirstName().asString() : null;
    }

    String getSurname() {
        return person != null && person.getSurname() != null ? person.getSurname().asString() : null;
    }

    String getUserName() {
        return user.getUsername().asString();
    }

    String getDescription() {
        return person != null ? person.getDescription() : null;
    }
}
