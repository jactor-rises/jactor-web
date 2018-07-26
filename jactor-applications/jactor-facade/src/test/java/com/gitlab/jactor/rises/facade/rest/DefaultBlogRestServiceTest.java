package com.gitlab.jactor.rises.facade.rest;

import com.gitlab.jactor.rises.commons.dto.BlogDto;
import com.gitlab.jactor.rises.commons.dto.BlogEntryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("A DefaultBlogRestService")
class DefaultBlogRestServiceTest {

    private DefaultBlogRestService defaultBlogRestService;
    private @Mock RestTemplate restTemplateMock;

    @BeforeEach void initAndMock() {
        MockitoAnnotations.initMocks(this);
        defaultBlogRestService = new DefaultBlogRestService(restTemplateMock, "");
    }

    @DisplayName("should use RestTemplate GET to fetch a blog")
    @Test void shouldUseRestTemplateGetToFetchBlog() {
        when(restTemplateMock.getForEntity(anyString(), eq(BlogDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        defaultBlogRestService.fetch(1L);

        verify(restTemplateMock).getForEntity(eq("/blog/get/1"), eq(BlogDto.class));
    }

    @DisplayName("should use RestTemplate GET to fetch a blog entry")
    @Test void shouldUseRestTemplateGetToFetchBlogEntry() {
        when(restTemplateMock.getForEntity(anyString(), eq(BlogEntryDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        defaultBlogRestService.fetchEntry(1L);

        verify(restTemplateMock).getForEntity(eq("/blog/entry/get/1"), eq(BlogEntryDto.class));
    }

    @DisplayName("should exchange blog values with HttpMethod.POST when persisting")
    @Test void shouldExchangeBlogValuesWithHttpPostWhenSaveOrUpdate() {
        when(restTemplateMock.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(BlogDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        BlogDto blogDto = new BlogDto();
        defaultBlogRestService.saveOrUpdate(blogDto);

        verify(restTemplateMock).exchange(
                eq("/blog/persist"), eq(HttpMethod.POST), eq(new HttpEntity<>(blogDto)), eq(BlogDto.class)
        );
    }

    @DisplayName("should exchange blog entry values with HttpMethod.POST when persisting")
    @Test void shouldExchangeBlogEntryValuesWithHttpPostWhenSaveOrUpdate() {
        when(restTemplateMock.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(BlogEntryDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        BlogEntryDto blogEntryDto = new BlogEntryDto();
        defaultBlogRestService.saveOrUpdate(blogEntryDto);

        verify(restTemplateMock).exchange(
                eq("/blog/entry/persist"), eq(HttpMethod.POST), eq(new HttpEntity<>(blogEntryDto)), eq(BlogEntryDto.class)
        );
    }
}