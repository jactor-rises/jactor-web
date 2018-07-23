module com.gitlab.jactor.rises.web {
    requires com.gitlab.jactor.rises.commons;
    requires com.gitlab.jactor.rises.model.facade;
    requires org.apache.commons.lang3;
    requires slf4j.api;
    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.webmvc;
    requires thymeleaf;
    requires thymeleaf.spring5;
    requires tomcat.embed.core;
}