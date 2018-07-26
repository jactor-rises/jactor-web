package com.gitlab.jactor.rises.web.exception;

import com.gitlab.jactor.rises.commons.dto.ServerErrorDto;
import com.gitlab.jactor.rises.commons.stack.JactorStack;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class JactorResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleInternalServerError(RuntimeException rex, HttpHeaders headers, WebRequest webRequest) {
        ServerErrorDto serverErrorDto = ServerErrorDto.from(rex, new JactorStack().fetchAsDtos());

        return handleExceptionInternal(rex, serverErrorDto, headers, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }
}
