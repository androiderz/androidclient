package com.criminal.menabenyamen.taskr.api;

import java.util.HashMap;
import java.util.Map;

public final class RequestConfiguration {
    private final Map<String, String> headers;
    private final String body;

    private RequestConfiguration(Map<String, String> headers, String body) {
        this.headers = headers;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public static class Builder {
        private final Map<String, String> headers = new HashMap<>();
        private String body;

        public Builder setHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public RequestConfiguration build() {
            return new RequestConfiguration(headers, body);
        }
    }
}
