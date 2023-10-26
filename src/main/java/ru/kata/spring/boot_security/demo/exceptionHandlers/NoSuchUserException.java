package ru.kata.spring.boot_security.demo.exceptionHandlers;

public class NoSuchUserException extends RuntimeException{
    public NoSuchUserException(String msg) {
        super(msg);
    }
}
