package com.gitlab.jactor.rises.facade.consume.persistence;

import com.gitlab.jactor.rises.commons.dto.BlogDto;
import com.gitlab.jactor.rises.commons.dto.BlogEntryDto;
import com.gitlab.jactor.rises.model.service.BlogConsumerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

public class DefaultBlogConsumerService extends AbstractConsumerService implements BlogConsumerService {

    public DefaultBlogConsumerService(RestTemplate restTemplate, String baseUrl) {
        super(restTemplate, baseUrl);
    }

    @Override public BlogDto saveOrUpdate(BlogDto blogDto) {
        ResponseEntity<BlogDto> responseEntity = exchangePost(
                fullUrl("/blog/persist"), new HttpEntity<>(blogDto), BlogDto.class
        );

        return bodyOf(responseEntity);
    }

    @Override public BlogEntryDto saveOrUpdate(BlogEntryDto blogEntryDto) {
        ResponseEntity<BlogEntryDto> responseEntity = exchangePost(
                fullUrl("/blog/entry/persist"), new HttpEntity<>(blogEntryDto), BlogEntryDto.class
        );

        return bodyOf(responseEntity);
    }

    @Override public BlogDto fetch(Serializable id) {
        ResponseEntity<BlogDto> responseEntity = getForEntity(fullUrl("/blog/get/" + id), BlogDto.class);

        return bodyOf(responseEntity);
    }

    @Override public BlogEntryDto fetchEntry(Serializable entryId) {
        ResponseEntity<BlogEntryDto> responseEntity = getForEntity(fullUrl("/blog/entry/get/" + entryId), BlogEntryDto.class);

        return bodyOf(responseEntity);
    }
}
