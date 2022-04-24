package com.baraka.banking.handler;

import com.baraka.banking.dto.ResponseDto;
import com.baraka.banking.exception.BankingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class BankingExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDto> exception(Exception exception) {
        log.error("Error during request.", exception);
        ResponseDto response = ResponseDto.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BankingException.class)
    public ResponseEntity<ResponseDto> bankingException(BankingException exception) {
        log.error("Error during request.", exception);
        ResponseDto response = ResponseDto.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("error in the request MethodArgumentNotValidException ", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String field = fieldError.getField();
        ResponseDto response = new ResponseDto();
        response.setMessage(("Error in " + field + " : " + fieldError.getDefaultMessage()));
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
