package com.acme.shop.config;

import com.acme.shop.exception.RequestHandlingException;
import com.acme.shop.exception.ResourceNotFoundException;
import com.acme.shop.model.ApiError;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    private static String getMessageFromRequest(HttpServletRequest req) {
        if (req != null) {
            return String.format(" Received: %s %s %s", req.getMethod(), req.getRequestURL(), req.getQueryString() != null ? req.getQueryString() : "");
        }
        return "";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ApiError> handleJsonProcessingException(HttpServletRequest req, JsonProcessingException e) {

        String msg = "Error processing json. " + e.getMessage() + getMessageFromRequest(req);
        logger.error(msg, e);

        return error(HttpStatus.INTERNAL_SERVER_ERROR, msg, true);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiError> methodArgumentNotValidException(HttpServletRequest req, Exception e) {

        String exceptionMessage = "";
        if (e != null && e.getMessage() != null) {
            exceptionMessage = " (" + e.getMessage() + ")";
        }

        String msg = "Request not validated due to invalid argument" + exceptionMessage + "." + getMessageFromRequest(req);
        logger.info(msg);

        return error(HttpStatus.BAD_REQUEST, msg, false);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidException(HttpServletRequest req, MethodArgumentTypeMismatchException e) {

        Exception exception = e;
        if (e.getRootCause() instanceof Exception) {
            exception = (Exception) e.getRootCause();
        }

        String msg = "Request not validated due to invalid method. " + exception.getMessage() + getMessageFromRequest(req);
        logger.info(msg);

        return error(HttpStatus.BAD_REQUEST, msg, false);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> methodArgumentNotValidException(HttpServletRequest req, MissingServletRequestParameterException e) {

        final String message = "Mandatory Parameter is missing: " + e.getParameterName() + getMessageFromRequest(req);
        logger.info(message);

        return error(HttpStatus.BAD_REQUEST, message, false);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e) {

        String message = "The specified resource was not found. " + getMessageFromRequest(req);
        logger.info(message);

        return error(HttpStatus.NOT_FOUND, message, false);
    }

    @ExceptionHandler(RequestHandlingException.class)
    public ResponseEntity<ApiError> requestHandlingException(HttpServletRequest req, RequestHandlingException e) {
        final String message = "Error handling request." + getMessageFromRequest(req);
        logger.error(message, e);

        return error(HttpStatus.INTERNAL_SERVER_ERROR, message, true);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> defaultErrorHandler(HttpServletRequest req, Exception e)
            throws Exception {

        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        String msg = "Error handling request." + getMessageFromRequest(req);
        logger.error(msg, e);

        return error(HttpStatus.INTERNAL_SERVER_ERROR, msg, false);
    }

    private ResponseEntity<ApiError> error(HttpStatus httpStatus, String msg, boolean isTransient) {
        ApiError apiError = new ApiError(Integer.toString(httpStatus.value()), msg, isTransient);

        return new ResponseEntity<>(apiError, httpStatus);
    }

}