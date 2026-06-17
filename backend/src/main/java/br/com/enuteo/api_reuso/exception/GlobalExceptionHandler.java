package br.com.enuteo.api_reuso.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Violação de integridade (FK, unique, etc.) — mensagem limpa, sem vazar SQL.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<>(
            "Operação não permitida: o registro está vinculado a outros dados.",
            HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidUser.class)
    public ResponseEntity<Object> handleInvalidUserException(InvalidUser ex, WebRequest request){
        
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }
    
}
