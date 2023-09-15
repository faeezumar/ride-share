package org.andela.ryder.exception;

public class BadCustomerStatusException extends RuntimeException{
    public BadCustomerStatusException(String message) {
        super(message);
    }
}
