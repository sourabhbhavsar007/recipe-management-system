package com.restaurant.recipemanagementservice.exception.advice;

import java.util.Date;
import javax.validation.ConstraintViolationException;
import com.restaurant.recipemanagementservice.exception.RecipeCriteriaNotFoundException;
import com.restaurant.recipemanagementservice.exception.RecipeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * This class is rsponsible for handling all un-handled runtime exceptions and log them.
 *
 */
@RestControllerAdvice
public class RecipeExceptionHandlingAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RecipeExceptionHandlingAdvice.class);

    /**
     * Handles RecipeNotFoundException
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<RecipeErrorDetails> recipeNotFoundException(RecipeNotFoundException ex, WebRequest request) {

        logger.error(ex.getMessage(), ex);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    /**
     * Handles RecipeCriteriaNotFoundException
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(RecipeCriteriaNotFoundException.class)
    public ResponseEntity<RecipeErrorDetails> recipeCriteriaNotFoundException(RecipeCriteriaNotFoundException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    /**
     * Handles constraint violation exception.
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RecipeErrorDetails> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {

        logger.error(exception.getMessage(), exception);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handle constraint violation exception related to Hibernate.
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<RecipeErrorDetails> handleConstraintViolationExceptionHibernate(org.hibernate.exception.ConstraintViolationException exception, WebRequest request) {

        logger.error(exception.getMessage(), exception);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles method argument not valid exception.
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RecipeErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {

        logger.error(exception.getMessage(), exception);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles method argument type mismatch exception.
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RecipeErrorDetails> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request) {

        logger.error(exception.getMessage(), exception);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

    /**
     * Global exception handler, for super Exception class
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RecipeErrorDetails> globalExceptionHandler(Exception ex, WebRequest request) {

        logger.error(ex.getMessage(), ex);

        RecipeErrorDetails errorDetails = new RecipeErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
