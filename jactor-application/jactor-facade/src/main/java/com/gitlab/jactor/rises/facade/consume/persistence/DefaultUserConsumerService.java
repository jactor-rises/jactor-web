package com.gitlab.jactor.rises.facade.consume.persistence;

import com.gitlab.jactor.rises.commons.datatype.Username;
import com.gitlab.jactor.rises.commons.dto.UserDto;
import com.gitlab.jactor.rises.facade.consume.AbstractRestConsumerService;
import com.gitlab.jactor.rises.facade.consume.RestTemplateFactory;
import com.gitlab.jactor.rises.model.service.UserConsumerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class DefaultUserConsumerService extends AbstractRestConsumerService implements UserConsumerService {

    private final String endpoint;
    private final UriTemplateHandler uriTemplateHandler;

    public DefaultUserConsumerService(UriTemplateHandler uriTemplateHandler, String endpoint) {
        this.uriTemplateHandler = uriTemplateHandler;
        this.endpoint = endpoint;
    }

    @Override public UserDto saveOrUpdate(UserDto userDto) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/persist";

        RethrowHttpClientError<UserDto> rethrowHttpClientError = execution ->
                restTemplate.exchange(
                        endpointMethod, HttpMethod.POST, new HttpEntity<>(userDto), UserDto.class
                );

        ResponseEntity<UserDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                rethrowHttpClientError
        );

        return bodyOf(responseEntity);
    }

    @Override public Optional<UserDto> fetch(Serializable id) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/get/" + id;

        ResponseEntity<UserDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                execution -> restTemplate.getForEntity(endpointMethod, UserDto.class)
        );

        return Optional.ofNullable(bodyOf(responseEntity));
    }

    @Override public Optional<UserDto> find(Username username) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/find/" + username.asString();

        ResponseEntity<UserDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                execution -> restTemplate.getForEntity(endpointMethod, UserDto.class)
        );

        return Optional.ofNullable(bodyOf(responseEntity));
    }

    @Override public List<String> findAllUsernames() {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/all/usernames";

        ResponseEntity<String[]> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                execution -> restTemplate.getForEntity(endpointMethod, String[].class)
        );

        return Optional.ofNullable(responseEntity.getBody())
                .map(Arrays::asList)
                .orElse(emptyList());
    }
}
