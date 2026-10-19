package ru.university.krasnoperov.exception;

import java.util.UUID;

public class CantRateException extends BusinessException {
    public CantRateException(UUID id) {
        super("You can't rate photo of user: " + id);
    }
}
