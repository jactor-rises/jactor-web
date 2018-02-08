package com.github.jactor.rises.web;

import com.github.jactorrises.client.facade.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class SpringWebClientContext {
    private UserFacade userFacade = mock(UserFacade.class);

    @Bean UserFacade userFacade() {
        return userFacade;
    }
}
