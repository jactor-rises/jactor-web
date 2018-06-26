package com.github.jactor.rises.web.dto;


import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.client.domain.Address;
import com.github.jactor.rises.client.domain.Person;
import com.github.jactor.rises.client.domain.User;

import java.util.Optional;

public class UserDto {
    private final Address address;
    private final Person person;
    private final User user;

    public UserDto(User user) {
        this.user = user;
        person = Optional.ofNullable(user).map(User::getPerson).orElse(null);
        address = Optional.ofNullable(person).map(Person::getAddress).orElse(null);
    }

    String getAddressLine1() {
        return Optional.ofNullable(address)
                .map(Address::getAddressLine1).orElse("");
    }

    String getAddressLine2() {
        return Optional.ofNullable(address)
                .map(Address::getAddressLine2).orElse("");

    }

    String getCity() {
        return Optional.ofNullable(address)
                .map(Address::getCity).orElse("");
    }

    Integer getZipCode() {
        return Optional.ofNullable(address)
                .map(Address::getZipCode).orElse(null);
    }

    String getFirstName() {
        return Optional.ofNullable(person)
                .map(Person::getFirstName)
                .map(Name::asString).orElse("");
    }

    String getSurname() {
        return Optional.ofNullable(person)
                .map(Person::getSurname)
                .map(Name::asString).orElse("");
    }

    String getUserName() {
        return user.getUsername().asString();
    }

    String getDescription() {
        return Optional.ofNullable(person)
                .map(Person::getDescription).orElse("");
    }
}
