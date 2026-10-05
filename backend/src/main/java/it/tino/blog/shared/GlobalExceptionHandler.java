package it.tino.blog.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidPageRequestException.class)
    public ResponseEntity<Void> handleInvalidPageRequest(
        InvalidPageRequestException e,
        HttpServletRequest request
    ) {
        log.warn(
            "Rejected request to {} with page {} and size {}: the page must not be negative and the size must be between 1 and {}",
            request.getRequestURI(),
            e.page(),
            e.size(),
            PageRequest.MAX_SIZE
        );
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
