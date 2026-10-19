package ru.university.krasnoperov.exception;

public class UserAlreadyInBlackListException extends BusinessException {
    public UserAlreadyInBlackListException() {
        super("User already in Black List");
    }
}
