package com.github.jactor.rises.web.dto;


import com.github.jactor.rises.client.datatype.Name;
import com.github.jactor.rises.client.domain.Address;
import com.github.jactor.rises.client.domain.Person;
import com.github.jactor.rises.client.domain.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDto {
    private final Address address;
    private final Person person;
    private final User user;
    private String fullDescription;

    public UserDto(User user) {
        this.user = user;
        person = Optional.ofNullable(user).map(User::getPerson).orElse(null);
        address = Optional.ofNullable(person).map(Person::getAddress).orElse(null);
    }

    public List<String> fetchAddress() {
        return Stream.of(fetchAddressLine1(), fetchAddressLine2(), fetchAddressLine3(), fetchZipCode(), fetchCity())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public String fetchFullName() {
        String surname = fetchSurname();

        return fetchFirstname()
                .map(s -> s + " " + surname)
                .orElse(surname);
    }

    private String fetchAddressLine1() {
        return Optional.ofNullable(address)
                .map(Address::getAddressLine1).orElse("");
    }

    private String fetchAddressLine2() {
        return Optional.ofNullable(address)
                .map(Address::getAddressLine2).orElse(null);
    }

    private String fetchAddressLine3() {
        return Optional.ofNullable(address)
                .map(Address::getAddressLine3).orElse(null);
    }

    private String fetchCity() {
        return Optional.ofNullable(address)
                .map(Address::getCity).orElse(null);
    }

    private String fetchZipCode() {
        return String.valueOf(
                Optional.ofNullable(address)
                        .map(Address::getZipCode)
                        .orElse(null)
        );
    }

    private Optional<String> fetchFirstname() {
        return Optional.ofNullable(person)
                .map(Person::getFirstName)
                .map(Name::asString);
    }

    private String fetchSurname() {
        return Optional.ofNullable(person)
                .map(Person::getSurname)
                .map(Name::asString).orElse("");
    }

    public String fetchUsername() {
        return user.getUsername().asString();
    }

    public Optional<String> fetchDescriptionCode() {
        return Optional.ofNullable(person)
                .map(Person::getDescription);
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
}
