package com.gitlab.jactor.rises.facade.consume;

import com.gitlab.jactor.rises.facade.consume.persistence.DefaultBlogConsumerService;
import com.gitlab.jactor.rises.facade.consume.persistence.DefaultGuestBookConsumerService;
import com.gitlab.jactor.rises.facade.consume.persistence.DefaultUserConsumerService;
import com.gitlab.jactor.rises.model.service.BlogConsumerService;
import com.gitlab.jactor.rises.model.service.GuestBookConsumerService;
import com.gitlab.jactor.rises.model.service.UserConsumerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JactorConsumer {

    private static final String HTTP_LOCALHOST = "http://localhost:1099/jactor-persistence-orm";

    @Bean public BlogConsumerService blogRestService() {
        return new DefaultBlogConsumerService(new RestTemplate(), HTTP_LOCALHOST);
    }

    @Bean public GuestBookConsumerService guestBookRestService() {
        return new DefaultGuestBookConsumerService(new RestTemplate(), HTTP_LOCALHOST);
    }

    @Bean public UserConsumerService userRestService() {
        return new DefaultUserConsumerService(new RestTemplate(), HTTP_LOCALHOST);
    }
}
