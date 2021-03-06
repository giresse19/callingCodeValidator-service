package com.rimiTest.CallCodeAndNumber.exceptions;

import com.rimiTest.CallCodeAndNumber.persistence.model.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Objects;
/**
 *Wrapper for all exceptions using @ExceptionHandler annotation for individual exceptions.
 *
 */

@Slf4j
@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    // 400

    @ExceptionHandler({PhoneNumberServiceException.class})
    public ResponseEntity<Object> handlePhoneNumberServiceException(PhoneNumberServiceException exception, WebRequest request) {
        return new ResponseEntity<>(message(HttpStatus.BAD_REQUEST, exception), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CountryCodeAndCountryDAOException.class})
    public ResponseEntity<Object> handleCountryCodeAndCountryDAOException(CountryCodeAndCountryDAOException exception, WebRequest request) {
        return new ResponseEntity<>(message(HttpStatus.BAD_REQUEST, exception), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    //500

    @ExceptionHandler({FetchAndParseAPIDataException.class})
    protected ResponseEntity<Object> handleFetchAndParseAPIDataException(FetchAndParseAPIDataException ex,  WebRequest request) {
        return new ResponseEntity<>(message(HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final String bodyOfResponse = ErrorMessages.INVALID_INPUT.getErrorMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ApiError message(final HttpStatus httpStatus, final Exception ex) {

        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        final Throwable devMessage = explicitDevErrorMsg(ex);
        ErrorMessage errorMessage = new ErrorMessage(new Date(), message, devMessage);

        return new ApiError(errorMessage.getTimestamp(), httpStatus.value(), errorMessage.getMessage(), errorMessage.getDeveloperMessage());
    }

    public static Throwable explicitDevErrorMsg(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

}
