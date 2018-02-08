package com.github.jactorrises.web.html;

public class WebParameter {

    private final String name;
    private final String value;

    WebParameter(String key, String value) {
        this.name = key;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
