package ru.university.krasnoperov.exception;

import java.util.UUID;

public class CantGetPhotosException extends BusinessException {
    public CantGetPhotosException(UUID id) {
        super("You can't get photos from user: " + id);
    }
}
