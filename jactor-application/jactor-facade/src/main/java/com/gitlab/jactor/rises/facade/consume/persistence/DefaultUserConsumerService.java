package com.gitlab.jactor.rises.facade.consume.persistence;

import com.gitlab.jactor.rises.commons.datatype.Username;
import com.gitlab.jactor.rises.commons.dto.UserDto;
import com.gitlab.jactor.rises.model.service.UserConsumerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class DefaultUserConsumerService extends AbstractConsumerService implements UserConsumerService {

    public DefaultUserConsumerService(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }

    @Override public UserDto saveOrUpdate(UserDto userDto) {
        ResponseEntity<UserDto> responseEntity = exchangePost(
                fullUrl("/user/persist"), new HttpEntity<>(userDto), UserDto.class
        );

        return bodyOf(responseEntity);
    }

    @Override public Optional<UserDto> fetch(Serializable id) {
        ResponseEntity<UserDto> responseEntity = getForEntity(fullUrl("/user/get/" + id), UserDto.class);

        return Optional.ofNullable(bodyOf(responseEntity));
    }

    @Override public Optional<UserDto> find(Username username) {
        ResponseEntity<UserDto> responseEntity = getForEntity(fullUrl("/user/find/" + username.asString()), UserDto.class);

        return Optional.ofNullable(bodyOf(responseEntity));
    }

    @Override public List<String> findAllUsernames() {
        ResponseEntity<String[]> responseEntity = getForEntity(fullUrl("/user/all/usernames"), String[].class);
        return fetchBodyAsList(responseEntity);
    }
}
