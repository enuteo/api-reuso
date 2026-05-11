package br.com.enuteo.api_reuso.exception;

public class InvalidUser extends RuntimeException {
    public InvalidUser(String message) {
        super(message);
    }
    
}
