package com.github.jactorrises.model.persistence.entity.user;

import com.github.jactorrises.client.datatype.EmailAddress;
import com.github.jactorrises.client.datatype.UserName;
import com.github.jactorrises.model.Builder;
import com.github.jactorrises.model.persistence.entity.person.PersonEntity;
import com.github.jactorrises.model.persistence.entity.person.PersonEntityBuilder;

public class UserEntityBuilder extends Builder<UserEntity> {
    private EmailAddress emailAddress;
    private PersonEntity person;
    private String password;
    private UserName userName;

    UserEntityBuilder() {
    }

    public UserEntityBuilder with(PersonEntityBuilder personEntityBuilder) {
        person = personEntityBuilder.build();
        return this;
    }

    public UserEntityBuilder withEmailAddress(String emailAddress) {
        this.emailAddress = new EmailAddress(emailAddress);
        return this;
    }

    public UserEntityBuilder withUserName(String userName) {
        this.userName = new UserName(userName);
        return this;
    }

    public UserEntityBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override protected UserEntity buildBean() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmailAddress(emailAddress);
        userEntity.setPersonEntity(person);
        userEntity.setPassword(password);
        userEntity.setUserName(userName);

        return userEntity;
    }
}
