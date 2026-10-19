package ru.university.krasnoperov;

import org.junit.jupiter.api.Test;
import ru.university.krasnoperov.repository.UserFoto;
import ru.university.krasnoperov.repository.UserFotoInMemmory;

import java.util.UUID;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserFotoInMemmoryTest {

    @Test
    void testAddAndRatePhoto() {
        UserFoto fotoRepo = new UserFotoInMemmory();
        UUID userId = UUID.randomUUID();
        String photoName = "ava.jpg";

        // Добавляем фото
        fotoRepo.addFoto(userId, photoName);

        // Проверяем лайки (должно быть 0)
        Optional<Integer> likes = fotoRepo.getLikes(userId, photoName);
        assertTrue(likes.isPresent());
        assertEquals(0, likes.get());

        // Ставим лайк
        fotoRepo.rateFoto(userId, photoName);
        assertEquals(1, fotoRepo.getLikes(userId, photoName).get());
    }
}