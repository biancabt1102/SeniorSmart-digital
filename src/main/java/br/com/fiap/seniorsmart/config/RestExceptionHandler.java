package br.com.fiap.seniorsmart.config;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.seniorsmart.exceptions.RestError;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestError> argumentExceptionHandler(){
        log.error("Erro de argumento inválido");
        return ResponseEntity.badRequest().body(
            new RestError(400, "campos inválidos")
        );
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RestError> responseStatusExceptionHandler(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(
            new RestError(e.getStatusCode().value(), e.getBody().getDetail())
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestError> httpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body(
            new RestError(400, "campos inválidos")  
        );
    }

}
