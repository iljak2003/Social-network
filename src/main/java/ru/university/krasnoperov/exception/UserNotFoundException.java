package ru.university.krasnoperov.exception;

import java.util.UUID;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(UUID id) {
        super("User not found with id:" + id);
    }
}
