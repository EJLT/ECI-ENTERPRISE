package com.svalero.ECIwebapp.exception;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException () {
        super("El usuario ya ha sido registrado");
    }
}
