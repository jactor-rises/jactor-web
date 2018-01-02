package com.github.jactorrises.persistence.entity.person;

import com.github.jactorrises.commons.builder.AbstractBuilder;
import com.github.jactorrises.persistence.entity.address.AddressEntity;
import com.github.jactorrises.persistence.entity.address.AddressEntityBuilder;
import com.github.jactorrises.persistence.entity.user.UserEntity;
import com.github.jactorrises.persistence.entity.user.UserEntityBuilder;

public class PersonEntityBuilder extends AbstractBuilder<PersonEntity> {
    private AddressEntity addressEntity;
    private String description;
    private String firstName;
    private String surname;
    private String locale;
    private UserEntity userEntity;

    PersonEntityBuilder() {
    }

    public PersonEntityBuilder with(AddressEntity entity) {
        addressEntity = entity;
        return this;
    }

    public PersonEntityBuilder with(AddressEntityBuilder addressEntityBuilder) {
        return with(addressEntityBuilder.build());
    }

    public PersonEntityBuilder with(UserEntityBuilder userEntityBuilder) {
        userEntity = userEntityBuilder.build();
        return this;
    }

    public PersonEntityBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PersonEntityBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonEntityBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public PersonEntityBuilder withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    @Override protected PersonEntity buildBean() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setAddressEntity(addressEntity);
        personEntity.setDescription(description);
        personEntity.setFirstName(firstName);
        personEntity.setSurname(surname);
        personEntity.setLocale(locale);
        personEntity.setUserEntity(userEntity);

        return personEntity;
    }
}
