package com.gitlab.jactor.rises.facade.consume;

import com.gitlab.jactor.rises.facade.consume.persistence.DefaultBlogConsumerService;
import com.gitlab.jactor.rises.facade.consume.persistence.DefaultGuestBookConsumerService;
import com.gitlab.jactor.rises.facade.consume.persistence.DefaultUserConsumerService;
import com.gitlab.jactor.rises.model.service.BlogConsumerService;
import com.gitlab.jactor.rises.model.service.GuestBookConsumerService;
import com.gitlab.jactor.rises.model.service.UserConsumerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.util.UriTemplateHandler;

@Configuration
@PropertySource("classpath:application.properties")
public class JactorConsumer {

    @Value("${persistence.root.url}")
    private String persistenceRootUrl;

    @Bean public BlogConsumerService blogRestService() {
        return new DefaultBlogConsumerService(uriTemplateHandler(), "/blog");
    }

    @Bean public GuestBookConsumerService guestBookRestService() {
        return new DefaultGuestBookConsumerService(uriTemplateHandler(), "/guestBook");
    }

    @Bean public UserConsumerService userRestService() {
        return new DefaultUserConsumerService(uriTemplateHandler(), "/user");
    }

    private UriTemplateHandler uriTemplateHandler() {
        return new RootUriTemplateHandler(persistenceRootUrl);
    }
}
