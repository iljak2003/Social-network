package ru.university.krasnoperov.exception;

import java.util.UUID;

public class CantViewPhotosException extends BusinessException {
    public CantViewPhotosException(UUID id) {
        super("You can't view photos: " + id);
    }
}
