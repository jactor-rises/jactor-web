package com.gitlab.jactor.rises.facade.consume.persistence;

import com.gitlab.jactor.rises.commons.dto.GuestBookDto;
import com.gitlab.jactor.rises.commons.dto.GuestBookEntryDto;

import com.gitlab.jactor.rises.facade.consume.AbstractRestConsumerService;
import com.gitlab.jactor.rises.facade.consume.RestTemplateFactory;
import com.gitlab.jactor.rises.model.service.GuestBookConsumerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.io.Serializable;

public class DefaultGuestBookConsumerService extends AbstractRestConsumerService implements GuestBookConsumerService {

    private final String endpoint;
    private final UriTemplateHandler uriTemplateHandler;

    public DefaultGuestBookConsumerService(UriTemplateHandler uriTemplateHandler, String endpoint) {
        this.uriTemplateHandler = uriTemplateHandler;
        this.endpoint = endpoint;
    }

    @Override public GuestBookDto saveOrUpdate(GuestBookDto guestBookDto) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/persist";

        RethrowHttpClientError<GuestBookDto> rethrowHttpClientError = execution ->
                restTemplate.exchange(
                        endpointMethod, HttpMethod.POST, new HttpEntity<>(guestBookDto), GuestBookDto.class
                );

        ResponseEntity<GuestBookDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                rethrowHttpClientError
        );

        return bodyOf(responseEntity);
    }

    @Override public GuestBookEntryDto saveOrUpdate(GuestBookEntryDto guestBookEntryDto) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/entry/persist";


        RethrowHttpClientError<GuestBookEntryDto> rethrowHttpClientError = execution ->
                restTemplate.exchange(
                        endpointMethod, HttpMethod.POST, new HttpEntity<>(guestBookEntryDto), GuestBookEntryDto.class
                );

        ResponseEntity<GuestBookEntryDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                rethrowHttpClientError
        );

        return bodyOf(responseEntity);
    }

    @Override public GuestBookDto fetch(Serializable id) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/get/" + id;

        ResponseEntity<GuestBookDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                execution -> restTemplate.getForEntity(endpointMethod, GuestBookDto.class)
        );

        return bodyOf(responseEntity);
    }

    @Override public GuestBookEntryDto fetchEntry(Serializable entryId) {
        RestTemplate restTemplate = RestTemplateFactory.initNew(uriTemplateHandler);
        String endpointMethod = endpoint + "/entry/get/" + entryId;

        ResponseEntity<GuestBookEntryDto> responseEntity = RethrowHttpClientError.tryExecution(
                endpointMethod,
                execution -> restTemplate.getForEntity(endpointMethod, GuestBookEntryDto.class)
        );

        return bodyOf(responseEntity);
    }
}
