package ru.university.krasnoperov.exception;

import java.util.UUID;

public class SameIdException extends BusinessException {
    public SameIdException(UUID id) {
        super("Id's equals:" + id);
    }
}
