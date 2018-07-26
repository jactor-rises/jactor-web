package com.gitlab.jactor.rises.facade.rest;

import com.gitlab.jactor.rises.commons.dto.GuestBookDto;
import com.gitlab.jactor.rises.commons.dto.GuestBookEntryDto;

import com.gitlab.jactor.rises.model.service.GuestBookRestService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

public class DefaultGuestBookRestService extends AbstractRestService implements GuestBookRestService {

    public DefaultGuestBookRestService(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }

    @Override public GuestBookDto saveOrUpdate(GuestBookDto guestBookDto) {
        ResponseEntity<GuestBookDto> responseEntity = exchangePost(
                fullUrl("/guestBook/persist"), new HttpEntity<>(guestBookDto), GuestBookDto.class
        );

        return bodyOf(responseEntity);
    }

    @Override public GuestBookEntryDto saveOrUpdate(GuestBookEntryDto guestBookEntryDto) {
        ResponseEntity<GuestBookEntryDto> responseEntity = exchangePost(
                fullUrl("/guestBook/entry/persist"), new HttpEntity<>(guestBookEntryDto), GuestBookEntryDto.class
        );

        return bodyOf(responseEntity);
    }

    @Override public GuestBookDto fetch(Serializable id) {
        ResponseEntity<GuestBookDto> responseEntity = getForEntity(fullUrl("/guestBook/get/" + id), GuestBookDto.class);

        return bodyOf(responseEntity);
    }

    @Override public GuestBookEntryDto fetchEntry(Serializable entryId) {
        ResponseEntity<GuestBookEntryDto> responseEntity = getForEntity(fullUrl("/guestBook/entry/get/" + entryId), GuestBookEntryDto.class);

        return bodyOf(responseEntity);
    }
}
