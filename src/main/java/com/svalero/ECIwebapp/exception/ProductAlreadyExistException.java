package com.svalero.ECIwebapp.exception;

public class ProductAlreadyExistException extends Exception {

    public ProductAlreadyExistException(String message) {
        super(message);
    }

    public ProductAlreadyExistException () {
        super("Este producto ya ha sido registrado");
    }
}
