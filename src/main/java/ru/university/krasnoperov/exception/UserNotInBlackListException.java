package ru.university.krasnoperov.exception;

public class UserNotInBlackListException extends BusinessException {
    public UserNotInBlackListException() {
        super("User not in balck list");
    }
}
