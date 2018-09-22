package com.gitlab.jactor.rises.facade;

import com.gitlab.jactor.rises.commons.framework.SpringBeanNames;
import com.gitlab.jactor.rises.model.service.GuestBookDomainService;
import com.gitlab.jactor.rises.model.service.GuestBookConsumerService;
import com.gitlab.jactor.rises.model.service.UserDomainService;
import com.gitlab.jactor.rises.model.service.UserConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import static java.util.Arrays.stream;

@SpringBootApplication
public class JactorFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(JactorFacade.class);

    public @Bean GuestBookDomainService guestBookDomainService(GuestBookConsumerService guestBookConsumerService) {
        return new GuestBookDomainService(guestBookConsumerService);
    }

    public @Bean UserDomainService userDomainService(UserConsumerService userConsumerService) {
        return new UserDomainService(userConsumerService);
    }

    public @Bean CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
        return args -> inspect(applicationContext, args);
    }

    private void inspect(ApplicationContext applicationContext, String[] args) {
        if (LOGGER.isDebugEnabled()) {
            String arguments = (args == null || args.length == 0) ?
                    "without arguments!" :
                    "with arguments: " + String.join(" ", args) + '!';

            LOGGER.debug("Starting {} {}", JactorFacade.class.getSimpleName(), arguments);

            SpringBeanNames springBeanNames = new SpringBeanNames();
            stream(applicationContext.getBeanDefinitionNames()).sorted().forEach(springBeanNames::add);

            LOGGER.debug("Available beans:");
            springBeanNames.getBeanNames().stream().map(name -> "- " + name).forEach(LOGGER::debug);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Available spring beans:");
                springBeanNames.getNamesOfSpringBeans().stream().map(name -> "- " + name).forEach(LOGGER::trace);
            }
        }
    }

    public static void main(String... args) {
        SpringApplication.run(JactorFacade.class, args);
    }
}
