package com.gitlab.jactor.rises.facade.consume;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

public abstract class AbstractRestConsumerService {
    protected AbstractRestConsumerService() {
    }

    protected <T> T bodyOf(ResponseEntity<T> responseEntity) {
        if (responseEntity == null) {
            throw new IllegalStateException("No response from REST service");
        }

        if (isNot2xxSuccessful(responseEntity.getStatusCode())) {
            String badConfiguredResponseMesssage = String.format("Bad configuration of REST service! ResponseCode: %s(%d)",
                    responseEntity.getStatusCode().name(),
                    responseEntity.getStatusCodeValue()
            );

            throw new IllegalStateException(badConfiguredResponseMesssage);
        }

        return responseEntity.getBody();
    }

    private boolean isNot2xxSuccessful(HttpStatus statusCode) {
        return !statusCode.is2xxSuccessful();
    }

    @FunctionalInterface
    protected interface RethrowHttpClientError<T> {
        ResponseEntity<T> execute(RethrowHttpClientError<T> rethrowHttpClientError);

        static <E> ResponseEntity<E> tryExecution(String endpointMethod, RethrowHttpClientError<E> rethrowHttpClientError) {
            try {
                return rethrowHttpClientError.execute(rethrowHttpClientError);
            } catch (RestClientException e) {
                throw new IllegalStateException("Unable to execute: " + endpointMethod, e);
            }
        }
    }
}
