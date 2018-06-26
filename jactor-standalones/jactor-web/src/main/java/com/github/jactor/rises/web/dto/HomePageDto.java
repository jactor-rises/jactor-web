package com.github.jactor.rises.web.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class HomePageDto {

    private final List<Paragraph> paragraphs;
    private final List<Technology> technologies;
    private final String techsUsed;
    private final String welcomeMsg;

    public HomePageDto(String welcomeMsg, List<Paragraph> paragraphs, String techsUsed, List<Technology> technologies) {
        this.paragraphs = paragraphs;
        this.technologies = technologies;
        this.techsUsed = techsUsed;
        this.welcomeMsg = welcomeMsg;
    }

    public String getWelcomeMsg() {
        return welcomeMsg;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public String getTechUsed() {
        return techsUsed;
    }

    public static class Paragraph {
        private final String content;

        public Paragraph(String content) {
            this.content = content;
        }

        public @Override String toString() {
            return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                    getContent()).toString();
        }

        public String getContent() {
            return content;
        }
    }

    public static class Technology {
        private final String message;
        private final String tech;
        private final String url;

        public Technology(String tech, String url, String message) {
            this.tech = tech;
            this.url = url;
            this.message = message;
        }

        public @Override String toString() {
            return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(getTech()).append(getUrl()).append(getMessage()).toString();
        }

        public String getMessage() {
            return message;
        }

        public String getTech() {
            return tech;
        }

        public String getUrl() {
            return url;
        }
    }
}
