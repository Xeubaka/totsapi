package com.tots.api.totsapi.exceptionHandler;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tots.api.totsapi.exception.EntityNotFoundException;
import com.tots.api.totsapi.exception.NoHandlerFoundException;
import com.tots.api.totsapi.exception.ApiException;
import com.tots.api.totsapi.exceptionHandler.Problem.Field;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
    
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<Object> handleParseException(ParseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, getProblem(ex, status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, getProblem(ex, status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(ApiException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, getProblem(ex, status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApi(ApiException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, getProblem(ex, status), new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
        ) {
            return handleExceptionInternal(ex, getProblem(ex, status), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        List<Field> fields = new ArrayList<Problem.Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String field = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new Problem.Field(field, message));
        }

        Problem problem = new Problem();

        problem.setStatus(status.value());
        problem.setTitle("One or more fields are invalid. Please fill it right and try again");
        problem.setDateTime(OffsetDateTime.now());
        problem.setField(fields);

        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        Problem problem = new Problem();
        List<Field> fields = new ArrayList<Problem.Field>();
        
        String[] messageError = ex.getLocalizedMessage().split(";");
        String title = messageError[0].split(":")[0];
        String field = messageError[2].split("\"")[1];

        String message = "Value received: '" +
                            messageError[1].split("\"")[1] +
                            "' Must be filled like: '" + 
                            messageError[1].split("\"")[3] + 
                            "'";
        fields.add(new Problem.Field(field, message));

        problem.setStatus(status.value());
        problem.setTitle(title);
        problem.setField(fields);
        problem.setDateTime(OffsetDateTime.now());

        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem getProblem(Exception ex, HttpStatus status) {
        Problem problem = new Problem();

        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return problem;
    }
}
