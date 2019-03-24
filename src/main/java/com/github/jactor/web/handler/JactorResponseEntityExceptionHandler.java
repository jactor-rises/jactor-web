package com.github.jactor.web.handler;

import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class JactorResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(JactorResponseEntityExceptionHandler.class);
  private static final String CAUSED_BY_MSG = "  ...caused by %s: %s ";

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<Object> handleInternalServerError(RuntimeException rex, HttpHeaders headers, WebRequest webRequest) {
    logException(rex, webRequest);
    logCause(rex.getCause());

    return handleExceptionInternal(rex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
  }

  private void logException(Throwable throwable, WebRequest webRequest) {
    LOGGER.error("Exception caught in {} {}", webRequest.getContextPath(), webRequest.getDescription(true));
    LOGGER.error("Failed by {}: {}", throwable.getClass().getName(), throwable.getMessage());

    logCause(throwable.getCause());

    StackWalker.getInstance().walk(
        stackFrameStream -> stackFrameStream
            .filter(stackFrame -> stackFrame.getClassName().startsWith("com.github.jactor"))
            .skip(1) // skip this method
            .collect(Collectors.toList())
    ).forEach(
        stackFrame -> LOGGER.error(" - {}(line:{}) - {}", stackFrame.getClassName(), stackFrame.getLineNumber(), stackFrame.getFileName())
    );
  }

  private void logCause(Throwable cause) {
    Optional<Throwable> possibleCause = Optional.ofNullable(cause);

    while (possibleCause.isPresent()) {
      Throwable theCause = possibleCause.get();
      LOGGER.error(String.format(CAUSED_BY_MSG, theCause.getClass().getName(), theCause.getMessage()), theCause);
      possibleCause = Optional.ofNullable(theCause.getCause());
    }
  }
}
