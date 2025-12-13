package com.staffinity.recruiting.common.exception;

import com.staffinity.recruiting.common.util.Trace;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String TRACE = "traceId";
    private static final String INSTANCE = "instance";

    // Error 404: when a requested resource is not found
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setType(URI.create("/errors/not-found"));
        pd.setTitle("Resource not found");
        pd.setDetail(ex.getMessage());
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.warn("Resource not found: {}", ex.getMessage());
        return pd;
    }

    // Error 409: Conflict, e.g., when trying to create a resource that already exists
    @ExceptionHandler(ConflictException.class)
    public ProblemDetail conflict(ConflictException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setType(URI.create("/errors/conflict"));
        pd.setTitle("Data conflict");
        pd.setDetail(ex.getMessage());
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.warn("Data conflict: {}", ex.getMessage());
        return pd;
    }

    // Error 400: Dto validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/validation"));
        pd.setTitle("Validation error");
        pd.setDetail("One or more fields are invalid");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());

        List<Map<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", Optional.ofNullable(error.getDefaultMessage()).orElse("invalid"),
                        "rejectedValue", Optional.ofNullable(error.getRejectedValue()).orElse("")))
                .toList();

        pd.setProperty("errors", errors);
        log.warn("Validation errors: {}", errors);
        return pd;
    }

    // Error 400: When the JSON in the request body is malformed
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail unreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/bad-request"));
        pd.setTitle("Malformed request");
        pd.setDetail("The request body could not be parsed");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.warn("Malformed request body: {}", ex.getMessage());
        return pd;
    }

    // Error 401: Authentication failures
    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setType(URI.create("/errors/unauthorized"));
        pd.setTitle("Authentication Failed");
        pd.setDetail("Invalid credentials provided.");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.warn("Authentication failure: {}", ex.getMessage());
        return pd;
    }

    // Error 400: Illegal arguments passed to methods
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail illegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setType(URI.create("/errors/bad-request"));
        pd.setTitle("Invalid request");
        pd.setDetail(ex.getMessage());
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.warn("Illegal argument: {}", ex.getMessage());
        return pd;
    }

    // Error 500: Generic server error handler
    @ExceptionHandler(Exception.class)
    public ProblemDetail generic(Exception ex, HttpServletRequest req) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setType(URI.create("/errors/internal"));
        pd.setTitle("Internal error");
        pd.setDetail("An unexpected error has occurred");
        pd.setProperty(TRACE, Trace.currentId());
        pd.setProperty(INSTANCE, req.getRequestURI());
        log.error("Unhandled exception", ex);
        return pd;
    }
}