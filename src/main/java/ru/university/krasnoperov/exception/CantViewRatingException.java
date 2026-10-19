package ru.university.krasnoperov.exception;

import java.util.UUID;

public class CantViewRatingException extends BusinessException {
    public CantViewRatingException(UUID id) {
        super("You can't view rating photo of user: " + id);
    }
}
